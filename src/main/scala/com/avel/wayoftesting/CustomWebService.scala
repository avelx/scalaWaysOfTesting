package com.avel.wayoftesting

import com.typesafe.scalalogging.{LazyLogging}

class CustomWebService extends Service with LazyLogging {

  override def init(): Unit = {
    logger.info("Call init")
  }

  override def login(i: Int): Unit = {
    logger.info(s"LogIn: $i")
  }

  override def isOnline: Boolean = false

  override def clear(): Unit =
    throw new Error("Can't change internal state")

  override def logout(i: Int): Unit = {
    logger.info(s"LogOut: $i")
  }

}
