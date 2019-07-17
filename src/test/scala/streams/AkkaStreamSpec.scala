package streams

import akka.Done
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Keep, Sink, Source}
import akka.stream.testkit.scaladsl.{TestSink, TestSource}
import akka.testkit.{TestKit, TestProbe}
import org.scalatest.{BeforeAndAfterAll, WordSpecLike}

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps
import com.avel.wayoftesting.streams.Akka._

import scala.util.{Failure, Success}


class AkkaStreamSpec extends TestKit(ActorSystem("TestingAkkaStreams"))
  with WordSpecLike
  with BeforeAndAfterAll {

  implicit val materializer = ActorMaterializer()

  override def afterAll(): Unit = TestKit.shutdownActorSystem(system)

  "A simple stream" should {
    "satisfy basic assertions" in {
      val source = simpleSource(10)
      val sink = simpleSink(0)

      val sumFuture = source.toMat(sink)(Keep.right).run()
      val sum = Await.result(sumFuture, 2 seconds)
      assert(sum == 55)
    }
  }

  "integrate with test actors via materialized values" in {
    import akka.pattern.pipe
    import system.dispatcher

    val source = simpleSource(10)
    val sink = simpleSink(0)

    val probe = TestProbe()
    source
      .toMat(sink)(Keep.right)
      .run()
      .pipeTo(probe.ref)

    probe
      .expectMsg(55)
  }

  "integrate with a test-actor-based sink" in {
    val source = simpleSource(8)
    val flow = simpleFlow()

    val probe = TestProbe()
    val probeSink = Sink
      .actorRef(probe.ref, "completionMessage")

    val streamUnderTest = source.via(flow)

    streamUnderTest
      .to(probeSink).run()

    probe
      .expectMsgAllOf(0, 1, 3, 6, 10, 15)
  }

  "integrate with Streams TestKit Sink" in {
    val sourceUnderTest = funSource(5, x => x * 3)

    val testSink = TestSink.probe[Int]
    val materializedTestValue = sourceUnderTest
      .runWith(testSink)

    materializedTestValue
      .request(5)
      .expectNext(3, 6, 9, 12, 15)
      .expectComplete()
  }

  "integrate with Streams TestKit Source" in {
    import system.dispatcher

    val sinkUnderTest = faultySink(15)

    val testSource = TestSource.probe[Int]
    val (testPublisher, resultFuture) = testSource
      .toMat(sinkUnderTest)(Keep.both).run()

    testPublisher
      .sendNext(1)
      .sendNext(5)
      .sendNext(13)
      .sendComplete()

    resultFuture.onComplete {
      case Success(_) =>
        fail("the sink under test should have thrown an exception on 13")
      case Failure(_) =>
    }

  }

  "test flows with a test source AND a test sink" in {
    val flowUnderTest = multiFlow()

    val testSource = TestSource.probe[Int]
    val testSink = TestSink.probe[Int]

    val materialized = testSource.via(flowUnderTest).toMat(testSink)(Keep.both).run()
    val (publisher, subscriber) = materialized

    publisher
      .sendNext(1)
      .sendNext(5)
      .sendNext(42)
      .sendNext(99)
      .sendComplete()

    subscriber
      .request(4) // don't forget this!
      .expectNext(2, 10, 84, 198)
      .expectComplete()

  }

}
