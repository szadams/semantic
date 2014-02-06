package models

import scala.util.Random
import java.util.Date
import java.security.SecureRandom
import scala.collection.mutable

case class User(id: Long, name: String, surname: String, friends: mutable.Buffer[User], madeActivities: mutable.Buffer[String])

object User {

  val names = List("Adam", "Maciej", "Katarzyna", "Ildefons", "Joanna", "Piotr", "Włodzimierz")
  val surnames = List("Smith", "Turing", "Jackson", "Bieber", "Freud", "Markov", "Zawinsky")
  val activities = Seq("is_a_friend_of", "liked_post_of", "liked_comment_of", "liked_photo_of", "created_post",
      "commented", "uploaded_photo", "shared_post_of", "poked")
  
  def createUser: User = {
    val id = new SecureRandom().nextLong()
    val name = names(math.abs(new Random().nextInt(names.size-1)))
    val surname = surnames(math.abs(new Random().nextInt(surnames.size-1)))
    
    new User(math.abs(id), name, surname, mutable.Buffer(), mutable.Buffer())
  }

  // whether given users are friends
  def areFriends(user1: User, user2: User): Boolean = {
    user1.friends.foreach(f =>
      if (f.id == user2.id) {
        return true
      })

    false
  }

  def getPossibleActivities: Seq[String] = {
    activities
  }
}