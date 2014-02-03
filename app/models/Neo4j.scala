package models

object Neo4j {
	import org.anormcypher._
	
	// without auth
	//Neo4jREST.setServer("localhost", 1337, "/db/data/")
	Neo4jREST.setServer("localhost", 1337, "")
	
	// or with basic auth
	// Neo4jREST.setServer("localhost", 1337, "/db/data/", "username", "password")
	
	def create {
		// create some test nodes
		Cypher("""create (anorm {name:"AnormCypher"}), (test {name:"Test"})""").execute()
		
		// a simple query
		val req = Cypher("start n=node(*) return n.name")
		
		// get a stream of results back
		try {
			val stream = req()
			
			// get the results and put them into a list
			stream.map(row => {row[String]("n.name")}).toList
		} catch {
			case e: Exception => println(e)
		}
	}
	
	// Zwracają True jeżeli się udało
	val isAdded = Cypher("""
	  create (germany {name:"Germany", population:81726000, type:"Country", code:"DEU"}),
	         (france {name:"France", population:65436552, type:"Country", code:"FRA", indepYear:1790}),
	         (monaco {name:"Monaco", population:32000, type:"Country", code:"MCO"});
	  """).execute()
	
	val isSucces: Boolean = Cypher("START n=node(0) RETURN n").execute
	
}