package models

import play.api.Logger

object Neo4j {
  import org.anormcypher._

  Neo4jREST.setServer("localhost", 7474, "/db/data/")

  def create = {
    // create some test nodes
    try {
      Cypher("""create (anorm {name:"AnormCypher"}), (test {name:"Test"})""").execute

      // a simple query
      val req = Cypher("start n=node(*) return n.name").apply()

      // get a stream of results back
      val stream = req

      // get the results and put them into a list
      stream.map(row => { row[String]("n.name") }).toList
    } catch {
      case e: Exception => Logger.error(e.toString)
      List()
    }
  }

  // Zwracają True jeżeli się udało
  val isAdded = Cypher("""
	  create (germany {name:"Germany", population:81726000, type:"Country", code:"DEU"}),
	         (france {name:"France", population:65436552, type:"Country", code:"FRA", indepYear:1790}),
	         (monaco {name:"Monaco", population:32000, type:"Country", code:"MCO"});
	  """).execute

  val isSuccess: Boolean = Cypher("START n=node(*) RETURN n").execute

}