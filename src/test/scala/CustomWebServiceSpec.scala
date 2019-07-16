import org.scalatest.FlatSpec
import org.mockito.Mockito._
import org.mockito.ArgumentMatchersSugar._

class CustomWebServiceSpec extends FlatSpec {
  import com.avel.wayoftesting._

  val webService = mock( classOf[CustomWebService] )

  "User logout method" should "call webService run" in {
    val user = new User(webService)
    user.logout()

    val userId : Int = 5
    verify(webService, times(1) ).run( userId )
  }


//
//  it should "produce NoSuchElementException when head is invoked" in {
//    assertThrows[NoSuchElementException] {
//      Set.empty.head
//    }
//  }

}
