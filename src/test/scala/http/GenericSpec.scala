package http

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.avel.wayoftesting.http.{Book, BookJsonProtocol}
import org.scalatest.{Matchers, WordSpec}
import com.avel.wayoftesting.http.Generic._


class GenericSpec extends WordSpec with Matchers with ScalatestRouteTest with BookJsonProtocol {

  type Books = List[Book]

  "A digital library backend" should {

    "return all the books in the library" in {
      Get("/api/book") ~> libraryRoute ~> check {
        status shouldBe StatusCodes.OK
        entityAs[Books] shouldBe books
      }
    }

    "return a book by hitting the query parameter endpoint" in {
      Get("/api/book?id=2") ~> libraryRoute ~> check {
        status shouldBe StatusCodes.OK
        responseAs[Option[Book]] shouldBe Some(Book(2, "JRR Tolkien", "The Lord of the Rings"))
      }
    }


  }

}
