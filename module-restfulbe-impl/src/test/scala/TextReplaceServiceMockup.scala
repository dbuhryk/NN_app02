import akka.NotUsed
import com.buhryk.nnapp.lagom.services.api.TextReplaceService
import com.lightbend.lagom.scaladsl.api.ServiceCall

import scala.concurrent.Future

class TextReplaceServiceMockup extends TextReplaceService{

  def replaceText(): ServiceCall[String,String] = ServiceCall { s =>
    Future.successful(s.replaceAll("Oracle", "Oracle®"))
  }

  def callCounter(): ServiceCall[NotUsed,Int] = ServiceCall { _ =>
    Future.successful(0)
  }
}