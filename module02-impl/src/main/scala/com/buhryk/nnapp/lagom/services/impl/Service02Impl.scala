package com.buhryk.nnapp.lagom.services.impl

import akka.NotUsed
import com.buhryk.nnapp.lagom.services.api.Service02
import com.lightbend.lagom.scaladsl.api.ServiceCall

import scala.concurrent.Future

class Service02Impl extends Service02{

  def replace_text(test:String): ServiceCall[NotUsed,String] = ServiceCall { _ =>
    Future.successful(test)
  }
  def call_counter(): ServiceCall[NotUsed,Int] = ServiceCall { _ =>
    Future.successful(0)
  }
}
