package wiro.apps

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import wiro.models.Config
import wiro.server.akkaHttp.{FailSupport, HttpRPCServer, RouterDerivationModule}
import wiro.server.akkaHttp.ToHttpResponse
import wiro.reflect._
import FailSupport._
import akka.http.scaladsl.model.{ContentType, HttpEntity, HttpResponse, StatusCodes}
import akka.http.scaladsl.model.MediaTypes
import io.circe.generic.auto._
import wiro.apps.models.User

// Models definition
object models {
  case class User(name: String)
}

  import wiro.annotation._

  // Error messages
  case class Error(msg: String)
  case class UserNotFoundError(msg: String)


object errors {

  import io.circe.syntax._
  implicit def notFoundToResponse = new ToHttpResponse[UserNotFoundError] {
    def response(error: UserNotFoundError) = HttpResponse(
      status = StatusCodes.NotFound,
      entity = HttpEntity(ContentType(MediaTypes.`application/json`), error.asJson.noSpaces)
    )
  }

  implicit def errorToResponse = new ToHttpResponse[Error] {
    def response(error: Error) = HttpResponse(
      status = StatusCodes.InternalServerError,
      entity = HttpEntity(ContentType(MediaTypes.`application/json`), error.asJson.noSpaces)
    )
  }
}

object UsersServer extends App with RouterDerivationModule {

  import models._
  import errors._

  val usersRouter = deriveRouter[UsersApi](new UsersApiImpl)

  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()

  val rpcServer = new HttpRPCServer(
    config = Config("localhost", 8080),
    routers = List(usersRouter)
  )
}
