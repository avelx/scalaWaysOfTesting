package com.avel.wayoftesting

import com.typesafe.scalalogging.{LazyLogging}

class User(service: Service) extends LazyLogging {

  def logout() = {
    logger.info("USER: logout")
    if (service.isOnline)
      service.logout(5)
  }

  def login() = {
    logger.info("USER: logout...")
    if (service.isOnline)
      service.login(5)
  }

}
