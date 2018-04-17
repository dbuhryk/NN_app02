package com.buhryk.nnapp.lagom.services.impl

import java.util.concurrent.atomic.AtomicInteger

import akka.NotUsed
import com.buhryk.nnapp.lagom.services.api.TextReplaceService
import com.lightbend.lagom.scaladsl.api.ServiceCall

import scala.concurrent.Future
import scala.io.Source

class TextReplaceServiceImpl extends TextReplaceService{

  val counter = new AtomicInteger(0)
  val lines = Source.fromResource("Keyword.txt").getLines().toList

  def replaceText(): ServiceCall[String,String] = ServiceCall { s =>
    val result:String = lines.foldLeft(s)( (s, rs) => s.replaceAll(rs, rs + "®") )
    counter.incrementAndGet()
    Future.successful(result)
  }

  def callCounter(): ServiceCall[NotUsed,Int] = ServiceCall { _ =>
    Future.successful(counter.get())
  }
}