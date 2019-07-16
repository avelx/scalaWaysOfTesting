package mockito

import java.util

import com.avel.wayoftesting.mockito.{CustomWebService, User}
import org.mockito.Mockito._
import org.mockito.captor._
import org.scalatest.FlatSpec
//import org.mockito.ArgumentMatchersSugar._

class CustomWebServiceSpec extends FlatSpec {

  "User logout method" should "call webService " in {

    val webService = mock( classOf[CustomWebService] )
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

  "User call clean method" should "call webService and raise exception" in {
    val webService = mock( classOf[CustomWebService] )
    when(webService.isOnline).thenReturn(true)
    when(webService.clear()).thenThrow( classOf[Error] )

    val user = new User(webService)
    user.fetch()

    verify(webService, times(1) )logout(-1)

  }

  // Full object mocking
  "LinkedList size" should "return 100" in {
    val spiedList = spy(new util.LinkedList[String])
    when(spiedList.size).thenReturn(100)
    assert(spiedList.size === 100)
  }

}
