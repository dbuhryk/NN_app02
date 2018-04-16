package com.buhryk.nnapp.lagom.services.loader

import com.buhryk.nnapp.lagom.services.api.Service02
import com.buhryk.nnapp.lagom.services.impl.Service02Impl
import com.lightbend.lagom.scaladsl.api.ServiceLocator
import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import com.lightbend.lagom.scaladsl.server.{LagomApplication, LagomApplicationContext, LagomApplicationLoader}
import com.softwaremill.macwire.wire
import play.api.libs.ws.ahc.AhcWSComponents

abstract class Service02Application(context: LagomApplicationContext) extends LagomApplication(context)
  with AhcWSComponents {

  override lazy val lagomServer = serverFor[Service02](wire[Service02Impl])
}

class Service02Loader extends LagomApplicationLoader {
  override def load(context: LagomApplicationContext) =
    new Service02Application(context) {
      override def serviceLocator: ServiceLocator = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext) =
    new Service02Application(context) with LagomDevModeComponents

  override def describeService = Some(readDescriptor[Service02])
}
