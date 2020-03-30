package controllers

import javax.inject.Inject
import javax.inject.Singleton
import play.api.mvc.Action
import play.api.mvc.AnyContent
import play.api.mvc.BaseController
import play.api.mvc.ControllerComponents
import play.api.mvc.Request

@Singleton
class HomeController @Inject() (val controllerComponents: ControllerComponents)
    extends BaseController {

  def index(maybeName: Option[String]) = Action {
    implicit request: Request[AnyContent] =>
      Ok {
        val message = maybeName
          .map(name => s"Hello, $name!")
          .getOrElse(
            """Please give a name as a query parameter named "name"."""
          )
        views.html.index(message)
      }
  }
}
