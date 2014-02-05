package models

import scala.collection.mutable

import play.api.Logger

object Neo4j {
	import org.anormcypher._

	Neo4jREST.setServer("localhost", 7474, "/db/data/")

	def executeQuery(query: String) = {
		Logger.info("QUERY => " + query)
		Cypher("" + query + "").execute
	}
	
	// TODO dodać wszystkich wygenerowanych userów
	def create = {
		try {
			Cypher("""CREATE (anorm {name:"AnormCypher"}), (test {name:"Test"})""").execute

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
				friends_query += "(" + f.id + ":User { id: " + id + ", name: " + f.name + ", surname: " + f.surname + "}),"
				
				// EXAMPLE: (123456)-[:ISFRIENDOF]->(u),
				relations_query += "(" + f.id + ")-[:ISFRIENDOF]->(u),"
			}
		}
		
		val query = "CREATE(u:User { id: " + id + ", name: " + name + ", surname: " + surname + "})" + friends_query + relations_query
		executeQuery(query)
	}
	
	def addFriendsToUser(id: Long, friends: mutable.Buffer[User]) = {
		var friends_query = ""
		var relations_query = ""
		
		if (!friends.isEmpty) {
			friends_query = ","
				
			for (f <- friends) {
				// EXAMPLE: (123456:User { id: 123456, name: Dzidek, surname: Kowalski }),)
				friends_query += "(" + f.id + ":User { id: " + id + ", name: " + f.name + ", surname: " + f.surname + "}),"
				
				// EXAMPLE: (123456)-[:ISFRIENDOF]->(u),
				relations_query += "(" + f.id + ")-[:ISFRIENDOF]->(u),"
			}
		}
		
		val query = "MATCH (u:User) WHERE u.id = " + id + friends_query + relations_query
		executeQuery(query)
	}
	
	def addActivitiesToUser() = {
		???
	}
	
	def removeUser(id: Long) = {
		val query = "MATCH (u { id: " + id + " }) DELETE u"
	}

	def updateUser(id: Long, name: String, surname: String, friends: mutable.Buffer[User], madeActivities: mutable.Buffer[String]) = {
		???
	}

	def showUser(id: Long) = {
		val query = "MATCH (u: User) WHERE u.id = " + id + " RETURN u"
		executeQuery(query)
	}
	
	def getAllUsers = executeQuery("MATCH u: User RETURN u")
	
	def cleanDB = {
		val query = "START n = node(*) MATCH n-[r?]-() WHERE (ID(n)>0 AND ID(n)<10000) DELETE n, r;"
		executeQuery(query)
	}

}