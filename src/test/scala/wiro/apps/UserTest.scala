package wiro.apps

import akka.http.scaladsl.model.HttpResponse
import org.scalatest.{FlatSpec, Matchers}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.http.scaladsl.unmarshalling.FromResponseUnmarshaller
import akka.stream.Materializer
import de.heikoseeberger.akkahttpcirce.ErrorAccumulatingCirceSupport._
import io.circe.Json
import wiro.apps.Models._
import wiro.server.akkaHttp._
import FailSupport._
import io.circe.generic.auto._

import scala.concurrent.{ExecutionContext, Future}




class UserTest extends FlatSpec with Matchers with ScalatestRouteTest with RouterDerivationModule {

  /*
  implicit def userNFEncoder = new WiroEncoder[Either[wiro.apps.UserNotFoundError,wiro.apps.Models.User]](){

    override def encode(a: Either[UserNotFoundError, User]) = Json.Null
  }

    implicit def userEncoder = new WiroEncoder[Either[wiro.apps.Error,wiro.apps.Models.User]](){

      override def encode(a: Either[Error, User]) = Json.Null
    }

    implicit def fromResponseUnmarshaller = new FromResponseUnmarshaller[wiro.apps.Models.User]() {
      override def apply(value: HttpResponse)(implicit ec: ExecutionContext, materializer: Materializer) = {
        Future(new User("Pluto"))
      }
    }
    */

  val route = deriveRouter[UsersApi](new UsersApiImpl).buildRoute

  it should "get a user" in {
    Get("/users/getUser?id=0") ~> route ~> check {
      responseAs[User].name shouldBe "Pluto"
    }
  }
}
