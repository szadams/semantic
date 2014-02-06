package models

import scala.collection.mutable

import play.api.Logger

object Neo4j {
  import org.anormcypher._

  Neo4jREST.setServer("localhost", 7474, "/db/data/")

  def executeQuery(query: String) = {
    Cypher(query).execute()
  }

  def create(users: mutable.Buffer[models.User]) = {
    try {
      for (u <- users) {
        addUser(u.id, u.name, u.surname, u.friends)
      }
      //Cypher("""CREATE (anorm {name:"AnormCypher"}), (test {name:"Test"})""").execute

      val req = Cypher("start n=node(*) return n.name").apply()
      val stream = req

      stream.map(row => { row[String]("n.name") }).toList
    } catch {
      case e: Exception =>
        Logger.error(e.toString)
        List()
    }
  }

  def addUser(id: Long, name: String, surname: String, friends: mutable.Buffer[User]) = { //, madeActivities: mutable.Buffer[String]) = {
    var friends_query = ""
    var relations_query = ""

    if (!friends.isEmpty) {
      friends_query = ","

      for (f <- friends) {
        // EXAMPLE: (123456:User { id: 123456, name: Dzidek, surname: Kowalski }),)
        friends_query += "(" + f.id + ":User { id: " + id + ", name: '" + f.name + "', surname: '" + f.surname + "' }),"

        // EXAMPLE: (123456)-[:ISFRIENDOF]->(u),
        relations_query += "(" + f.id + ")-[:ISFRIENDOF]->(u),"
      }
    }

    val query = "CREATE (u:User { id: " + id + ", name: '" + name + "', surname: '" + surname + "' })" + friends_query + relations_query
    executeQuery(query)
  }

  def addFriendsToUser(id: Long, friends: mutable.Buffer[User]) = {
    var friends_query = ""
    var relations_query = ""

    if (!friends.isEmpty) {
      friends_query = ","

      for (f <- friends) {
        // EXAMPLE: (123456:User { id: 123456, name: Dzidek, surname: Kowalski }),)
        friends_query += "(" + f.id + ":User { id: " + id + ", name: '" + f.name + "', surname: '" + f.surname + "' }),"

        // EXAMPLE: (123456)-[:ISFRIENDOF]->(u),
        relations_query += "(" + f.id + ")-[:ISFRIENDOF]->(u),"
      }
    }

    val query = "MATCH (u:User) WHERE u.id = " + id + friends_query + relations_query
    executeQuery(query)
  }

  def addRelation(id1: Long, id2: Long, relation: String) = {
    val query = "START n=node(*), m=node(*) WHERE (n.id = " + id1 + ") and (m.id = " + id2 + ") CREATE (n)-[:" + relation + "]->(m)"
    Logger.warn(query)
    executeQuery(query)
  }

  def removeUser(id: Long) = {
    val query = "MATCH (u { id: " + id + " }) DELETE u"
  }

  def showUser(id: Long): User = {
    val query = "MATCH (u: User) WHERE u.id = " + id + " RETURN u.id as id, u.name as name, u.surname as surname"
    val result = Cypher(query).apply().map(row =>
      User(row[Long]("id"), row[String]("name"), row[String]("surname"), mutable.Buffer(), mutable.Buffer())).toList(0)
    result
  }

  def getAllUsers: List[User] = {
    val query = "MATCH (u: User) RETURN u.id as id, u.name as name, u.surname as surname"
    val result = Cypher(query).apply().map(row =>
      User(row[Long]("id"), row[String]("name"), row[String]("surname"), mutable.Buffer(), mutable.Buffer())).toList
    result
  }

  def cleanDB = Cypher("MATCH (n) OPTIONAL MATCH (n)-[r]-() DELETE n,r").execute()

}