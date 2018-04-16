package com.buhryk.nnapp.lagom.services.impl

import akka.NotUsed
import com.buhryk.nnapp.lagom.services.api._
import com.lightbend.lagom.scaladsl.api.ServiceCall

import scala.concurrent.{ExecutionContext, Future}

class TextReplaceRestfulServiceImpl(replaceService: TextReplaceService)
                                   (implicit ec: ExecutionContext) extends TextReplaceRestfulService {

  def replaceText(): ServiceCall[TextRequest, TextResponse] = ServiceCall { x =>
    val result: Future[String] = replaceService.replaceText.invoke(x.text.getOrElse("No text"))
    result.map {
      r => TextResponse(r, result = Some(0))
    }
  }

  def callCounter(): ServiceCall[NotUsed, CounterResponse] = ServiceCall { _ =>
    val result: Future[Int] = replaceService.callCounter.invoke()
    result.map {
      r => CounterResponse(r, result = Some(0))
    }
  }
}
