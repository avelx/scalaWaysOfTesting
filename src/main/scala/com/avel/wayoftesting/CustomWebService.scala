package com.avel.wayoftesting

import com.typesafe.scalalogging.{LazyLogging}

class CustomWebService extends Service with LazyLogging {

  override def init(): Unit = {
    logger.info("Call init")
  }

  override def run(i: Int): Unit = {
    logger.info(s"Call run: $i")
  }
}
