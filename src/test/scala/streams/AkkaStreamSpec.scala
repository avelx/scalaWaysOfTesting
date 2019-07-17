package streams

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Keep, Sink, Source}
import akka.stream.testkit.scaladsl.TestSink
import akka.testkit.{TestKit, TestProbe}
import org.scalatest.{BeforeAndAfterAll, WordSpecLike}

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps
import com.avel.wayoftesting.streams.Akka._


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

}
