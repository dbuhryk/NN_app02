package com.buhryk.nnapp.lagom.services.impl

import akka.NotUsed
import com.buhryk.nnapp.lagom.services.api.{Service02, TextRequest, TextResponse}
import com.lightbend.lagom.scaladsl.api.ServiceCall

import scala.concurrent.Future

class Service02Impl extends Service02{

  def replace_text(): ServiceCall[TextRequest,TextResponse] = ServiceCall { x =>
    Future.successful(TextResponse(x.text.getOrElse("no text")))
  }
  def call_counter(): ServiceCall[NotUsed,Int] = ServiceCall { _ =>
    Future.successful(0)
  }
}
