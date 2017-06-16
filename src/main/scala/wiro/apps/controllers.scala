package wiro.apps


import scala.concurrent.Future
import wiro.server.akkaHttp.{FailSupport, HttpRPCServer, RouterDerivationModule, ToHttpResponse}
import wiro.models.Config

import scala.concurrent.ExecutionContext.Implicits.global
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.http.scaladsl.model.{ContentType, HttpEntity, HttpResponse, StatusCodes}
import akka.http.scaladsl.model.MediaTypes
import io.circe.generic.auto._
import wiro.server.akkaHttp.AuthenticationType.Token

object controllers {
  import models._
  import wiro.annotation._
  import FailSupport._

  case class Nope(msg: String)
  case class Wa(lol: String, bah: Int, dah: Int)

  @path("woff")
  trait DoghouseApi {
    @query(name = Some("puppy"))
    def getPuppy(
                ): Future[Either[Nope, Dog]]

    @command(name = Some("pallino"))
    @auth(authenticationType = Token)
    def getPallino(
                    something: String
                  ): Future[Either[Nope, Dog]]


  }

  class DoghouseApiImpl() extends DoghouseApi {
    override def getPallino(
                             something: String
                           ): Future[Either[Nope, Dog]] = Future(Right(Dog("pallino")))

    override def getPuppy(

                         ): Future[Either[Nope, Dog]] = Future(Right(Dog("puppy")))

  }
}

object errors {
  import FailSupport._
  import controllers.Nope

  import io.circe.syntax._
  implicit def nopeToResponse = new ToHttpResponse[Nope] {
    def response(error: Nope) = HttpResponse(
      status = StatusCodes.UnprocessableEntity,
      entity = HttpEntity(ContentType(MediaTypes.`application/json`), error.asJson.noSpaces)
    )
  }
}


object Server extends App with RouterDerivationModule {
  import controllers._
  import wiro.reflect._
  import models._
  import errors._
  import FailSupport._

  val doghouseRouter = deriveRouter[DoghouseApi](new DoghouseApiImpl)

  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()

  val rpcServer = new HttpRPCServer(
    config = Config("localhost", 8080),
    routers = List(doghouseRouter)
  )
}

object models {
  case class Dog(name: String)
  case class Kitten(name: String)
}

