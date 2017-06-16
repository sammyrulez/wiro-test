package wiro.apps

/**
  * Created by sam on 16/06/17.
  */
import scala.concurrent.Future

// Models definition
object models {
  case class User(name: String)
}

trait ControllersInterfaces {
  import models._
  import wiro.annotation._

  // Error messages
  case class Error(msg: String)
  case class UserNotFoundError(msg: String)

  // API interface
  @path("users")
  trait UsersApi {

    @query
    def getUser(
                 id: Int
               ): Future[Either[UserNotFoundError, User]]

    @command
    def insertUser(
                    id: Int,
                    name: String
                  ): Future[Either[Error, User]]
  }
}
