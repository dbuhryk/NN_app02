package com.buhryk.nnapp.lagom.services.loader

import com.buhryk.nnapp.lagom.services.api.{TextReplaceRestfulService, TextReplaceService}
import com.buhryk.nnapp.lagom.services.impl.TextReplaceRestfulServiceImpl
import com.lightbend.lagom.scaladsl.client.ConfigurationServiceLocatorComponents
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import com.lightbend.lagom.scaladsl.server.{LagomApplication, LagomApplicationContext, LagomApplicationLoader}
import com.softwaremill.macwire.wire
import play.api.libs.ws.ahc.AhcWSComponents

abstract class TextReplaceRestfulModule (context: LagomApplicationContext) extends LagomApplication(context)
  with AhcWSComponents {
  override lazy val lagomServer = serverFor[TextReplaceRestfulService](wire[TextReplaceRestfulServiceImpl])
  lazy val replaceService = serviceClient.implement[TextReplaceService]
}

class TextReplaceRestfulLoader extends LagomApplicationLoader {
  override def load(context: LagomApplicationContext) =
    new TextReplaceRestfulModule(context) with ConfigurationServiceLocatorComponents

  override def loadDevMode(context: LagomApplicationContext) =
    new TextReplaceRestfulModule(context) with LagomDevModeComponents

  override def describeService = Some(readDescriptor[TextReplaceRestfulService])
}
