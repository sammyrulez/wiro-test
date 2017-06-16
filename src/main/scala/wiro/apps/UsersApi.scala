package wiro.apps

import wiro.annotation.{command, path, query}
import wiro.apps.Models.User

import scala.concurrent.Future

/**
  * Created by sam on 16/06/17.
  */
@path("users")
trait UsersApi {

  @query(name = Some("getUser"))
  def getUser(
               id: Int
             ): Future[Either[UserNotFoundError, User]]

  @command(name = Some("insertUser"))
  def insertUser(
                  id: Int,
                  name: String
                ): Future[Either[Error, User]]
}
