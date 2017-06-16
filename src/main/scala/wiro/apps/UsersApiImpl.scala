package wiro.apps

import wiro.apps.Models.User

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
/**
  * Created by sam on 16/06/17.
  */


object UsersDb {
  val users = collection.mutable.Map.empty[Int, User]
}

class UsersApiImpl() extends UsersApi {



  override def getUser(
                        id: Int
                      ): Future[Either[UserNotFoundError, User]] = {
    UsersDb.users.get(id) match {
      case Some(user) => Future(Right(user))
      case None => Future(Left(UserNotFoundError("User not found")))
    }
  }

  override def insertUser(
                           id: Int,
                           name: String
                         ): Future[Either[Error, User]] = {
    val newUser = User(name)
    UsersDb.users(id) = newUser
    Future(Right(newUser))
  }
}
