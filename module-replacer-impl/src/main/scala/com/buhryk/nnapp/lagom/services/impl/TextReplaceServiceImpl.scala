package com.buhryk.nnapp.lagom.services.impl

import akka.NotUsed
import com.buhryk.nnapp.lagom.services.api.TextReplaceService
import com.lightbend.lagom.scaladsl.api.ServiceCall

import scala.concurrent.Future

class TextReplaceServiceImpl extends TextReplaceService{

  def replaceText(): ServiceCall[String,String] = ServiceCall { _ =>
    Future.successful("No text yet")
  }

  def callCounter(): ServiceCall[NotUsed,Int] = ServiceCall { _ =>
    Future.successful(0)
  }
}