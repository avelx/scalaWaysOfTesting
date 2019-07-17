package com.avel.wayoftesting.http

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.{StatusCodes}
import akka.http.scaladsl.server.Directives._
import spray.json._
case class Book(id: Int, author: String, title: String)


trait BookJsonProtocol extends DefaultJsonProtocol {
  implicit val bookFormat = jsonFormat3(Book)
}

object Generic extends BookJsonProtocol with SprayJsonSupport {

  // code under test
  var books = List(
    Book(1, "Harper Lee", "To Kill a Mockingbird"),
    Book(2, "JRR Tolkien", "The Lord of the Rings"),
    Book(3, "GRR Marting", "A Song of Ice and Fire"),
    Book(4, "Tony Robbins", "Awaken the Giant Within")
  )

  /*
    GET /api/book - returns all the books in the library
    GET /api/book/X - return a single book with id X
    GET /api/book?id=X - same
    POST /api/book - adds a new book to the library
    GET /api/book/author/X - returns all the books from the actor X
   */
  val libraryRoute =
    pathPrefix("api" / "book") {
      (path("author" / Segment) & get) { author =>
        complete(books.filter(_.author == author))
      } ~
        get {
          (path(IntNumber) | parameter('id.as[Int])) { id =>
            complete(books.find(_.id == id))
          } ~
            pathEndOrSingleSlash {
              complete(books)
            }
        } ~
        post {
          entity(as[Book]) { book =>
            books = books :+ book
            complete(StatusCodes.OK)
          } ~
            complete(StatusCodes.BadRequest)
        }
    }

}
