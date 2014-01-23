package models

import scala.util.Random
import java.util.Date
import java.security.SecureRandom
import scala.collection.mutable

case class User(id: Long, name: String, surname: String, friends: mutable.Buffer[User], madeActivities: mutable.Buffer[String])

object User {

  def createUser: User = {
    val id = new SecureRandom().nextLong() 
    new User(math.abs(id), "", "", mutable.Buffer(), mutable.Buffer())
  }
  
  // whether given users are friends
  def areFriends(user1: User, user2: User): Boolean = {
    user1.friends.foreach(f =>
      if (f.id == user2.id) {
        return true
      })

    false
  }
  
  // hardcoded activities, hope that's only for now -.-
  def getPossibleActivities: Seq[String] = {
    Seq("is a friend of ", "liked post of ", "liked comment of ", "liked photo of ", "created post", 
        "commented ", "uploaded photo", "shared post of ", "poked ")
  }
}