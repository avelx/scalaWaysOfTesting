package com.avel.wayoftesting.streams

import akka.stream.scaladsl.{Flow, Sink, Source}

import scala.util.Random

object Akka {

  def simpleSource(n: Int) = Source(1 to n)

  def funSource(n: Int, f: Int => Int) = Source(1 to n).map(f)

  def faultySink(n: Int) = {
    //val failure = Random.nextInt(n)
    Sink.foreach[Int] {
      case 13 =>
        throw new RuntimeException(s"bad luck on")
      case _ =>
    }
  }

  def simpleSink(z: Int) =
    Sink.fold(0)((a: Int, b: Int) => a + b)

  def simpleFlow() =
    Flow[Int].scan[Int](0)(_ + _)

  def multiFlow() = Flow[Int].map(_ * 2)
}
