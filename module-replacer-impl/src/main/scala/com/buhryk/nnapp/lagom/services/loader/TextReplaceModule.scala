package com.buhryk.nnapp.lagom.services.loader

import com.buhryk.nnapp.lagom.services.api.TextReplaceService
import com.buhryk.nnapp.lagom.services.impl.TextReplaceServiceImpl
import com.lightbend.lagom.scaladsl.client.ConfigurationServiceLocatorComponents
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import com.lightbend.lagom.scaladsl.server.{LagomApplication, LagomApplicationContext, LagomApplicationLoader}
import com.softwaremill.macwire.wire
import play.api.libs.ws.ahc.AhcWSComponents

abstract class TextReplaceModule (context: LagomApplicationContext) extends LagomApplication(context)
  with AhcWSComponents {
  override lazy val lagomServer = serverFor[TextReplaceService](wire[TextReplaceServiceImpl])
}

class TextReplaceLoader extends LagomApplicationLoader {
  override def load(context: LagomApplicationContext) =
    new TextReplaceModule(context) with ConfigurationServiceLocatorComponents

  override def loadDevMode(context: LagomApplicationContext) =
    new TextReplaceModule(context) with LagomDevModeComponents

  override def describeService = Some(readDescriptor[TextReplaceService])
}
