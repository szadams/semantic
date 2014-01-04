package models

import scala.collection.mutable.ListBuffer

// mainly to create new agents/users when application is starting
object Booting {
  
  def createUsers(amount: Int) = {
    val users = new ListBuffer[User]
    for (a <- 1 to amount) {
      users += User.createUser
      Thread.sleep(200)
    }
    
    users.toSeq
  }
}