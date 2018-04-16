package controllers

import play.api.mvc.{Action, AnyContent, ControllerComponents}

import scala.concurrent.{ExecutionContext, Future}

class Main(controllerComponents: ControllerComponents)
          (implicit ec: ExecutionContext)
  extends AbstractMainController(controllerComponents) {

  def index: Action[AnyContent] = Action.async { implicit request =>
    Future(Ok(views.html.index("Name")))
  }
}
