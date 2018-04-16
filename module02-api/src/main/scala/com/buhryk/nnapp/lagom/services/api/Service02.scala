package com.buhryk.nnapp.lagom.services.api


import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.Service.restCall
import com.lightbend.lagom.scaladsl.api.transport.Method
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}

trait Service02 extends Service{
  def replace_text(test:String): ServiceCall[NotUsed,String]
  def call_counter(): ServiceCall[NotUsed,Int]

  override def descriptor:Descriptor = {
    Service.named("service02").withCalls(
      restCall(Method.POST, "/api/service02/replace", replace_text _),
      restCall(Method.GET, "/api/service02/callcounter", call_counter _)
    )
  }
}