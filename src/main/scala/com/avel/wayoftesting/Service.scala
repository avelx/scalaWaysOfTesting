package com.avel.wayoftesting


trait Service {
  def init()
  def login(userId: Int)
  def logout(userId: Int)
}