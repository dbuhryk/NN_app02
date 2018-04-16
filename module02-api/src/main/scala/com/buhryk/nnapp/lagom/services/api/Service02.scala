package com.buhryk.nnapp.lagom.services.api


import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.Service.restCall
import com.lightbend.lagom.scaladsl.api.transport.Method
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}
import play.api.libs.json.{Format, Json}

trait Service02 extends Service{
  def replace_text(): ServiceCall[TextRequest,TextResponse]
  def call_counter(): ServiceCall[NotUsed,Int]

  override def descriptor:Descriptor = {
    Service.named("service02").withCalls(
      restCall(Method.POST, "/api/replace", replace_text _),
      restCall(Method.GET, "/api/callcounter", call_counter _)
    ).withAutoAcl(true)
  }
}

case class TextRequest(text: Option[String])

case class TextResponse(text: String)

object TextResponse {
  implicit val format: Format[TextResponse] = Json.format
}

object TextRequest {
  implicit val format: Format[TextRequest] = Json.format
}