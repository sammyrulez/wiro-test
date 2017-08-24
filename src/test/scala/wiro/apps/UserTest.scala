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
import wiro.apps.errors._
import scala.concurrent.{ExecutionContext, Future}




class UserTest extends FlatSpec with Matchers with ScalatestRouteTest with RouterDerivationModule {



  val route = deriveRouter[UsersApi](new UsersApiImpl).buildRoute

  it should "get a user" in {
    Get("/users/getUser?id=0") ~> route ~> check {
      responseAs[User].name shouldBe "Pluto"
    }
  }
}
