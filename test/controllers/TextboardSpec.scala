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

  override def fakeApplication() =
    new GuiceApplicationBuilder()
      .configure(
        "db.default.driver" -> "org.h2.Driver",
        "db.default.url" -> "jdbc:h2:mem:play"
      )
      .build()

  "GET /" should {
    "not print any messages if there are no posts" in {
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
    "not post an empty message" in {
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

    "not post a too long message" in {
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
