package com.avel.wayoftesting


trait Service {
  def isOnline: Boolean
  def init()
  def login(userId: Int)
  def logout(userId: Int)
  def clear()
}