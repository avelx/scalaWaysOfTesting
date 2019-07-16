package com.avel.wayoftesting

import com.typesafe.scalalogging.{LazyLogging}

class User(service: Service) extends LazyLogging {

  def logout() = {
    logger.info("USER: logout...")
    service.run(6)
  }

}
