import com.buhryk.nnapp.lagom.services.api.TextReplaceRestfulService
import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.api.{LagomConfigComponent, ServiceAcl, ServiceInfo}
import com.lightbend.lagom.scaladsl.client.LagomServiceClientComponents
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import com.softwaremill.macwire._
import controllers.Main
import play.api.ApplicationLoader.Context
import play.api.libs.ws.ahc.AhcWSComponents
import play.api.{ApplicationLoader, BuiltInComponentsFromContext, Mode, _}
import play.filters.HttpFiltersComponents
import router.Routes

import scala.collection.immutable
import scala.concurrent.ExecutionContext

abstract class WebGatewayModule(context: Context) extends BuiltInComponentsFromContext(context)
  //with AssetsComponents
  with HttpFiltersComponents
  with AhcWSComponents
  with LagomConfigComponent
  with LagomServiceClientComponents {

  override lazy val serviceInfo: ServiceInfo = ServiceInfo(
    "web-gateway",
    Map(
      "web-gateway" -> immutable.Seq(ServiceAcl.forPathRegex("(?!/api/).*"))
    )
  )
  override implicit lazy val executionContext: ExecutionContext = actorSystem.dispatcher

  override lazy val router = {
    val prefix = "/"
    wire[Routes]
  }

  lazy val main = wire[Main]
  lazy val replaceRestfulService = serviceClient.implement[TextReplaceRestfulService]
}

class WebGatewayLoader extends ApplicationLoader {
  override def load(context: Context): Application = context.environment.mode match {
    case Mode.Dev =>
      new WebGatewayModule(context) with LagomDevModeComponents {}.application
    case _ =>
      new WebGatewayModule(context) {
        override def serviceLocator = NoServiceLocator
      }.application
  }
}