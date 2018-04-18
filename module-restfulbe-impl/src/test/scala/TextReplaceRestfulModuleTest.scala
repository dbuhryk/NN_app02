import com.buhryk.nnapp.lagom.services.api.TextReplaceRestfulService
import com.buhryk.nnapp.lagom.services.impl.TextReplaceRestfulServiceImpl
import com.lightbend.lagom.scaladsl.server.{LagomApplication, LagomApplicationContext}
import com.softwaremill.macwire.wire
import play.api.libs.ws.ahc.AhcWSComponents

abstract class TextReplaceRestfulModuleTest (replaceService: TextReplaceServiceMockup, context: LagomApplicationContext) extends LagomApplication(context)
  with AhcWSComponents {
  override lazy val lagomServer = serverFor[TextReplaceRestfulService](wire[TextReplaceRestfulServiceImpl])
  //lazy val replaceService = serviceClient.implement[TextReplaceService]
}
