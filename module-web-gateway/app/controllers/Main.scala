package controllers

import java.io.{BufferedReader, BufferedWriter, FileReader, FileWriter}
import java.nio.file.Paths

import com.buhryk.nnapp.lagom.services.api.{TextReplaceRestfulService, TextRequest}
import play.api.libs.Files.TemporaryFile
import play.api.mvc.{Action, AnyContent, ControllerComponents, MultipartFormData}

import scala.concurrent.{ExecutionContext, Future}

class Main(replaceRestfulService: TextReplaceRestfulService, controllerComponents: ControllerComponents)
          (implicit ec: ExecutionContext)
  extends AbstractMainController(controllerComponents) {

  def index: Action[AnyContent] = Action.async { implicit request =>
    val counterResponse = replaceRestfulService.callCounter.invoke()
    counterResponse.map { counter =>
      Ok(views.html.index(counter.counter))
    }
  }

  def submit: Action[MultipartFormData[TemporaryFile]] = Action.async(parse.multipartFormData) { request =>
    request.body.file("file").map { f =>
      if (f.ref.length() > 0) {
        val filename = Paths.get(f.filename).getFileName
        val br = new BufferedReader(new FileReader(f.ref))
        val str = Stream.continually(br.readLine()).takeWhile(_ != null).mkString("\n")
        //f.ref.moveTo(Paths.get(s"$filename"), replace = true)
        val textResponseFuture = replaceRestfulService.replaceText.invoke(TextRequest(Some(str)))

        textResponseFuture.map {
          textResponse => {
            val new_f = f.ref.temporaryFileCreator.create()
            new_f.deleteOnExit()
            val bw = new BufferedWriter(new FileWriter(new_f));
            bw.write(textResponse.text)
            bw.close()
            Ok.sendFile(new_f, inline=false, _=>f.filename, onClose = () => { f.ref.deleteOnExit() })
          }
        }
      }
      else {
        Future(Redirect(routes.Main.index).flashing(
        "error" -> "No files selected or file is empty"))
      }
    }.getOrElse {
      Future(Redirect(routes.Main.index).flashing(
        "error" -> "Missing file"))
    }
  }
}

//  val fileForm = Form(mapping(
//
//  )
//case class FileSubmitForm(file: File)