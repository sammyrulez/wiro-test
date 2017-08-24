package wiro.apps

import wiro.annotation.{command, path, query}
import wiro.apps.Models.User

import scala.concurrent.Future

/**
  * Created by sam on 16/06/17.
  */

object Models {

  case class User(name: String)

}


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
