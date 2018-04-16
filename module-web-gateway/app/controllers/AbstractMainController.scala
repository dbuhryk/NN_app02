
package controllers

import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.ExecutionContext

abstract class AbstractMainController(controllerComponents: ControllerComponents)
                                     (implicit ec: ExecutionContext)
  extends AbstractController(controllerComponents) {
}







