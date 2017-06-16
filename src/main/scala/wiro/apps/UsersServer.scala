package wiro.apps

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

import wiro.reflect._
import wiro.models.Config
import wiro.server.akkaHttp.{ RouterDerivationModule, FailSupport, HttpRPCServer }

import io.circe.generic.auto._


object UsersServer extends App with RouterDerivationModule {
  import controllers._
  import models._
  import errors._

  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val ec = system.dispatcher

  val usersRouter = deriveRouter[UsersApi](new UsersApiImpl)

  val rpcServer = new HttpRPCServer(
    config = Config("localhost", 8080),
    routers = List(usersRouter)
  )
}