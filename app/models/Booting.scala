package models

import scala.collection.mutable.ListBuffer
import akka.actor.ActorLogging
import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props

case class UserActivity(what: String)

class UserActor extends Actor with ActorLogging {
  def receive = {
    case UserActivity(what) => log.info(what)
  }
}

// mainly to create new agents/users when application is starting
object Booting {

  def createUsers(amount: Int) = {
    val users = new ListBuffer[User]

    for (a <- 1 to amount) {
      users += User.createUser
      val system = ActorSystem(users.last.id.toString) // maybe it'll help identify concrete actor
      val actor = system.actorOf(Props[UserActor], name = users.last.id.toString + "User")
      actor ! UserActivity("I'm person with id "+users.last.id)
    }

    users.toBuffer
  }
}