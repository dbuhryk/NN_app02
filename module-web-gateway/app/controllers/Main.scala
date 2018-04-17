package controllers

import java.io.{BufferedReader, BufferedWriter, FileReader, FileWriter}
import java.nio.file.Paths

import com.buhryk.nnapp.lagom.services.api.{TextReplaceRestfulService, TextRequest}
import play.api.libs.Files.TemporaryFile
import play.api.mvc.{Action, AnyContent, ControllerComponents, MultipartFormData}

import scala.concurrent.duration.FiniteDuration
import scala.concurrent.{Await, ExecutionContext, Future, duration}

class Main(replaceRestfulService: TextReplaceRestfulService, controllerComponents: ControllerComponents)
          (implicit ec: ExecutionContext)
  extends AbstractMainController(controllerComponents) {

  def index: Action[AnyContent] = Action.async { implicit request =>
    val counterResponse = replaceRestfulService.callCounter.invoke()
    counterResponse.map { counter =>
      Ok(views.html.index(counter.counter))
    }
    //Future(Ok(views.html.index(0)))
  }

  def submit: Action[MultipartFormData[TemporaryFile]] = Action.async(parse.multipartFormData) { request =>
    //Request[MultipartFormData[TemporaryFile]]
    request.body.file("file").map { f =>
      if (f.ref.length() > 0) {
        val filename = Paths.get(f.filename).getFileName
        val br = new BufferedReader(new FileReader(f.ref))
        val str = Stream.continually(br.readLine()).takeWhile(_ != null).mkString("\n")
        //f.ref.moveTo(Paths.get(s"$filename"), replace = true)
        val textResponseFuture = replaceRestfulService.replaceText.invoke(TextRequest(Some(str)))
        val textResponse = Await.result(textResponseFuture, FiniteDuration(5000,duration.MILLISECONDS))
        val new_f = f.ref.temporaryFileCreator.create()
        new_f.deleteOnExit()
        val bw = new BufferedWriter(new FileWriter(new_f));
        bw.write(textResponse.text)
        bw.close()
        textResponseFuture.map {
          textResponse => {
            val new_f = f.ref.temporaryFileCreator.create()
            new_f.deleteOnExit()
          }
        }
        Future(Ok.sendFile(new_f, inline=false, _=>f.filename, onClose = () => { f.ref.deleteOnExit() })) }
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