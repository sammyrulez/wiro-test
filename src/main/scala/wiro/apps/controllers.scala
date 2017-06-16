package wiro.apps

import scala.concurrent.Future

object controllers extends ControllersInterfaces {
  import models.User
  import scala.concurrent.ExecutionContext.Implicits.global

  val users = collection.mutable.Map.empty[Int, User] // Users DB

  // API implementation
  class UsersApiImpl() extends UsersApi {
    override def getUser(
                          id: Int
                        ): Future[Either[UserNotFoundError, User]] = {
      users.get(id) match {
        case Some(user) => Future(Right(user))
        case None => Future(Left(UserNotFoundError("User not found")))
      }
    }

    override def insertUser(
                             id: Int,
                             name: String
                           ): Future[Either[Error, User]] = {
      val newUser = User(name)
      users(id) = newUser
      Future(Right(newUser))
    }
  }
}
