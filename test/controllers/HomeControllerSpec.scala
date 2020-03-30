package controllers

import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test._
import play.api.test.Helpers._

class HomeControllerSpec
    extends PlaySpec
    with GuiceOneAppPerTest
    with Injecting {

  "HomeController GET" should {

    "render the index page" in {
      val controller = new HomeController(stubControllerComponents())
      val name = "hoge"
      val home = controller.index(Some(name)).apply(FakeRequest())

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include("Hello, hoge!")
    }
  }
}
