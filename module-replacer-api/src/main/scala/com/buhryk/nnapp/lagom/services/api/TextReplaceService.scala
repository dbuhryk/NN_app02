package com.buhryk.nnapp.lagom.services.api

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.Service.namedCall
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}

trait TextReplaceService extends Service{
  def replaceText(): ServiceCall[String,String]
  def callCounter(): ServiceCall[NotUsed,Int]

  override def descriptor:Descriptor = {
    Service.named("replace").withCalls(
      namedCall("replace_text", replaceText _),
      namedCall("call_counter", callCounter _),
    )
  }
}
