import com.buhryk.nnapp.lagom.services.api.TextReplaceService
import com.buhryk.nnapp.lagom.services.loader.TextReplaceModule
import com.lightbend.lagom.scaladsl.server.LocalServiceLocator
import com.lightbend.lagom.scaladsl.testkit.ServiceTest
import org.scalatest.{AsyncWordSpec, BeforeAndAfterAll, Matchers}

class TextReplaceServiceSpec extends AsyncWordSpec with Matchers with BeforeAndAfterAll {

  lazy val server = ServiceTest.startServer(ServiceTest.defaultSetup) { ctx =>
    new TextReplaceModule(ctx) with LocalServiceLocator
  }
  lazy val client = server.serviceClient.implement[TextReplaceService]

  override protected def beforeAll() = server

  override protected def afterAll() = server.stop()

  "Replace Service" should {
    "return counter" in {
      client.callCounter.invoke().map { response =>
        response should === (0)
      }
    }

    "do basic replacement" in {
      client.replaceText.invoke("Oracle").map { response =>
        response should === ("Oracle®")
      }
    }
  }
}
