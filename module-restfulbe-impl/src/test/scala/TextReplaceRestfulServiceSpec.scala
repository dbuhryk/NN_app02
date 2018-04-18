import com.buhryk.nnapp.lagom.services.api.{TextReplaceRestfulService, TextRequest}
import com.lightbend.lagom.scaladsl.server.LocalServiceLocator
import com.lightbend.lagom.scaladsl.testkit.ServiceTest
import com.softwaremill.macwire.wire
import org.scalatest.{AsyncWordSpec, BeforeAndAfterAll, Matchers}

class TextReplaceRestfulServiceSpec extends AsyncWordSpec with Matchers with BeforeAndAfterAll {

  lazy val replaceService = wire[TextReplaceServiceMockup]

  lazy val server = ServiceTest.startServer(ServiceTest.defaultSetup) { ctx =>
    new TextReplaceRestfulModuleTest(replaceService, ctx) with LocalServiceLocator
  }
  lazy val client = server.serviceClient.implement[TextReplaceRestfulService]

  override protected def beforeAll() = server

  override protected def afterAll() = server.stop()

  "Replace Restful Service" should {
    "return counter" in {
      client.callCounter.invoke().map { response =>
        response.counter should === (0)
        response.result.get should === (0)
      }
    }

    "do basic replacement" in {
      client.replaceText.invoke(TextRequest(text=Some("Oracle"))).map { response =>
        response.text should === ("Oracle®")
        response.result.get should === (0)
      }
    }
  }
}
