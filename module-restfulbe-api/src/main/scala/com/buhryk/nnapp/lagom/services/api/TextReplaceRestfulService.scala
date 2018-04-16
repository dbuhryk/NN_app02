package com.buhryk.nnapp.lagom.services.api

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.Service.restCall
import com.lightbend.lagom.scaladsl.api.transport.Method
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}
import play.api.libs.json.{Format, Json}

trait TextReplaceRestfulService extends Service{
  def replaceText(): ServiceCall[TextRequest, TextResponse]
  def callCounter(): ServiceCall[NotUsed, CounterResponse]

  override def descriptor:Descriptor = {
    Service.named("replacerestful").withCalls(
      restCall(Method.POST, "/api/replace", replaceText _),
      restCall(Method.GET, "/api/callcounter", callCounter _)
    ).withAutoAcl(true)
  }
}

case class TextRequest(text: Option[String])

case class TextResponse(text: String, result: Option[Int] = None)

case class CounterResponse(counter: Int, result: Option[Int] = None)

object TextResponse {
  implicit val format: Format[TextResponse] = Json.format
}

object CounterResponse {
  implicit val format: Format[CounterResponse] = Json.format
}

object TextRequest {
  implicit val format: Format[TextRequest] = Json.format
}