package com.avel.wayoftesting.mockito

import com.typesafe.scalalogging.LazyLogging

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

  def fetch() = {
    logger.info("USER: call clear")
    if (service.isOnline)
      try{
        service.clear()
      } catch {
        case _: Error =>
          service.logout(-1)
      }
  }

}
