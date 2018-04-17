package controllers

import java.nio.file.Paths

import play.api.mvc.{Action, AnyContent, ControllerComponents}

import scala.concurrent.{ExecutionContext, Future}

class Main(controllerComponents: ControllerComponents)
          (implicit ec: ExecutionContext)
  extends AbstractMainController(controllerComponents) {

  def index: Action[AnyContent] = Action.async { implicit request =>
    Future(Ok(views.html.index("Name")))
  }

  def submit = Action(parse.multipartFormData) { request =>
    //Request[MultipartFormData[TemporaryFile]]
    request.body.file("file").map { f =>
      val filename = Paths.get(f.filename).getFileName
      //f.ref.moveTo(Paths.get(s"$filename"), replace = true)
      Ok.sendFile(f.ref)
    }.getOrElse {
      Redirect(routes.Main.index)
    }
  }
}

//  val fileForm = Form(mapping(
//
//  )
//case class FileSubmitForm(file: File)