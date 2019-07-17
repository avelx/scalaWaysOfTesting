package streams

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Keep, Sink, Source}
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

}
