import org.scalatest.FlatSpec
import org.mockito.Mockito._
import org.mockito.captor._
//import org.mockito.ArgumentMatchersSugar._

class CustomWebServiceSpec extends FlatSpec {
  import com.avel.wayoftesting._

  val webService = mock( classOf[CustomWebService] )

  "User logout method" should "call webService " in {
    when(webService.isOnline).thenReturn(true)

    val userId : Int = 5
    val user = new User(webService)
    user.logout()
    user.login()

    val captor = ArgCaptor[Int]

    // verify order
    val strictOrder = inOrder(webService)
    strictOrder.verify(webService, times(1) )logout(captor.capture)
    assert(captor.value == 5)

    strictOrder.verify(webService, times(1) )login (userId)
  }


//  it should "produce NoSuchElementException when head is invoked" in {
//    assertThrows[NoSuchElementException] {
//      Set.empty.head
//    }
//  }

}
