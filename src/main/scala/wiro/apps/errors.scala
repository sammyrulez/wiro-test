package wiro.apps

import wiro.server.akkaHttp.ToHttpResponse
import wiro.server.akkaHttp.FailSupport._

import akka.http.scaladsl.model.{ HttpResponse, StatusCodes, ContentType, HttpEntity}
import akka.http.scaladsl.model.MediaTypes

import io.circe.generic.auto._

object errors {
  import controllers.UserNotFoundError

  import io.circe.syntax._
  implicit def notFoundToResponse = new ToHttpResponse[UserNotFoundError] {
    def response(error: UserNotFoundError) = HttpResponse(
      status = StatusCodes.NotFound,
      entity = HttpEntity(ContentType(MediaTypes.`application/json`), error.asJson.noSpaces)
    )
  }

  import controllers.Error
  implicit def errorToResponse = new ToHttpResponse[Error] {
    def response(error: Error) = HttpResponse(
      status = StatusCodes.InternalServerError,
      entity = HttpEntity(ContentType(MediaTypes.`application/json`), error.asJson.noSpaces)
    )
  }
}
