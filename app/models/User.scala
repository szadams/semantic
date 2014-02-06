package models

import scala.util.Random
import java.util.Date
import java.security.SecureRandom
import scala.collection.mutable

case class User(id: Long, name: String, surname: String, friends: mutable.Buffer[User]) //, madeActivities: mutable.Buffer[String])

object User {

  val names = List("Adam", "Maciej", "Katarzyna", "Ildefons", "Joanna", "Piotr", "Włodzimierz")
  val surnames = List("Smith", "Turing", "Jackson", "Bieber", "Freud", "Markov", "Zawinsky")
  val activities = Seq("is_a_friend_of", "liked_post_of", "liked_comment_of", "liked_photo_of", "created_post",
    "commented", "uploaded_photo", "shared_post_of", "poked")

  def createUser: User = {
    val id: Long = math.abs(new SecureRandom().nextLong()) / 1000000000
    val name = names(math.abs(new Random().nextInt(names.size)))
    val surname = surnames(math.abs(new Random().nextInt(surnames.size)))
    
    val user = new User(math.abs(id), name, surname, mutable.Buffer())
    Neo4j.addUser(user.id, user.name, user.surname, mutable.Buffer())
    
    user
  }
  
  def createRelations = {
	  val allUsers = Neo4j.getAllUsers
	  
	  if (allUsers.size > 1) {
      var amountActivities = math.abs(new Random().nextInt(30))
      if (amountActivities == 0) amountActivities = 1
      val act = mutable.Buffer[String]()

      for (i <- 0 to amountActivities) {
        val randUserNumber = math.abs(new Random().nextInt(allUsers.size))
        val randUserNumber2 = math.abs(new Random().nextInt(allUsers.size))
        val randActivity = math.abs(new Random().nextInt(activities.size))
        act += activities(randActivity)
        Neo4j.addRelation(allUsers(randUserNumber).id, allUsers(randUserNumber2).id, activities(randActivity))
        println("--- HERE "+ activities(randActivity))
      }
    }
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