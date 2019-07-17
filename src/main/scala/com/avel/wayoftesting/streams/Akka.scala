package com.avel.wayoftesting.streams

import akka.stream.scaladsl.{Sink, Source}

object Akka {

  def simpleSource(n: Int) = Source(1 to n)

  def simpleSink(z: Int) = Sink.fold(0)((a: Int, b: Int) => a + b)


}
