package controllers

import model.CustomerService
import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import play.api.test.{ FakeRequest, WithApplication }

import play.api.test.Helpers._


@RunWith(classOf[JUnitRunner])
class CustomerControllerSpec extends Specification {

  val customerController = new CustomerController

  "Customer" should {

    "be able to see the registration page" in new WithApplication {

      val result = route(FakeRequest(GET,"/")).get
      status(result) must equalTo(OK)
      contentType(result) must beSome.which(_ == "text/html")
    }

    "be able to see the login page" in new WithApplication {

      val result = route(FakeRequest(GET,"/login")).get
      status(result) must equalTo(OK)
    }

    "be not able to register if username is already there" in new WithApplication() {

      val result = route(FakeRequest(GET,"/processRegistration").withFormUrlEncodedBody("username"->"akankshac",
        "password"->"akanksha","name"->"Akanksha Chabbra","email"->"akanksha@gmail.com",
        "mobile"->"9834773722")).get
      status(result) must equalTo(303)
    }

    "be not able to login with wrong details" in new WithApplication() {

      val result = route(FakeRequest(GET,"/processLogin").withFormUrlEncodedBody("username"->"akankshacd",
      "password"->"akanksha")).get
      status(result) must equalTo(303)
    }

    "be able to see the flash message" in new WithApplication() {

      val result = route(FakeRequest(GET,"/login").withFormUrlEncodedBody("username"->"akankshacd",
        "password"->"akanksha").withFlash("error"->"Username or password is incorrect")).get
      status(result) must equalTo(303)

    }
  }

}
