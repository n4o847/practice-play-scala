import org.scalatestplus.play._
import org.scalatestplus.play.guice.GuiceOneServerPerTest
import play.api.mvc._
import play.api.inject.guice._
import play.api.routing._
import play.api.routing.sird._

class TextboardSpec
    extends PlaySpec
    with GuiceOneServerPerTest
    with OneBrowserPerSuite
    with HtmlUnitFactory {

  import org.openqa.selenium.htmlunit.HtmlUnitDriver

  override def createWebDriver() = {
    val driver = new HtmlUnitDriver {
      def setAcceptLanguage(lang: String) =
        this.getWebClient().addRequestHeader("Accept-Language", lang)
    }
    driver.setAcceptLanguage("en")
    driver
  }

  "GET /" should {
    "does not print any messages if there are no posts" in {
      go to s"http://localhost:$port/"
      assert(pageTitle === "Scala Text Textboard")
      assert(findAll(className("post-body")).length === 0)
    }
  }

  "POST /" should {
    "print messages that was posted" in {
      val body = "test post"

      go to s"http://localhost:$port/"
      textField(cssSelector("input#post")).value = body
      submit()

      eventually {
        val posts = findAll(className("post-body")).toSeq
        assert(posts.length === 1)
        assert(posts(0).text === body)
        assert(findAll(cssSelector("p.error")).length === 0)
      }
    }
  }

  "POST /" should {
    "print messages that was posted by others" in {
      go to s"http://localhost:$port/"

      eventually {
        val posts = findAll(className("post-body")).toSeq
        assert(posts.length === 1)
      }
    }

    "cannot post an empty message" in {
      val body = ""

      go to s"http://localhost:$port/"
      textField(cssSelector("input#post")).value = body
      submit()

      eventually {
        val error = findAll(cssSelector("p.error")).toSeq
        assert(error.length === 1)
        assert(error(0).text === "Please enter a message.")
      }
    }

    "cannot post a too long message" in {
      val body = "too long messages"

      go to s"http://localhost:$port/"
      textField(cssSelector("input#post")).value = body
      submit()

      eventually {
        val error = findAll(cssSelector("p.error")).toSeq
        assert(error.length === 1)
        assert(error(0).text === "The message is too long.")
      }
    }
  }
}
