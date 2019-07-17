package com.avel.wayoftesting.streams

import akka.stream.scaladsl.{Flow, Sink, Source}

object Akka {

  def simpleSource(n: Int) = Source(1 to n)

  def funSource(n: Int, f: Int => Int) = Source(1 to n).map(f)

  def simpleSink(z: Int) =
    Sink.fold(0)((a: Int, b: Int) => a + b)

  def simpleFlow() =
    Flow[Int].scan[Int](0)(_ + _)
}
