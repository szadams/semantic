package models

import scala.collection.mutable

import play.api.Logger

object Neo4j {
	import org.anormcypher._

	Neo4jREST.setServer("localhost", 7474, "/db/data/")

	def executeQuery(query: String) = Cypher("" + query + "").execute
	
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
	
//	MATCH (ee:Person) WHERE ee.name = "Emil"
//	CREATE (js:Person { name: "Johan", from: "Sweden", learn: "surfing" }),
//	(ir:Person { name: "Ian", from: "England", title: "author" }),
//	(rvb:Person { name: "Rik", from: "Belgium", pet: "Orval" }),
//	(ally:Person { name: "Allison", from: "California", hobby: "surfing" }),
//	(ee)-[:KNOWS {since: 2001}]->(js),(ee)-[:KNOWS {rating: 5}]->(ir),
//	(js)-[:KNOWS]->(ir),(js)-[:KNOWS]->(rvb),
//	(ir)-[:KNOWS]->(js),(ir)-[:KNOWS]->(ally),
//	(rvb)-[:KNOWS]->(ally)
	
//	MATCH (ee:Person)-[:KNOWS]-(friends)
//	WHERE ee.name = "Emil" RETURN ee, friends

	def addUser(id: Long, name: String, surname: String, friends: mutable.Buffer[User]) = { //, madeActivities: mutable.Buffer[String]) = {
		val query = "CREATE(u:User { id: " + id + " name: " + name + ", surname: " + surname + "})";
		executeQuery(query)
		???
	}
	
	def addFriendsToUser() = {
		???
	}
	
	def addActivitiesToUser() = {
		???
	}

	def removeUser(id: Long) = {
		???
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
		???
	}

}