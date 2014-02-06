package controllers

import play.api._
import play.api.mvc._
import models._
import scala.util.Random
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object Application extends Controller {

  def index = Action {
    Ok(views.html.index(List()))
  }

  def start = Action {
    val r = new Random().nextInt(100)
    val users = Booting.createUsers(if (r == 0) r+1 else r)

    val results_from_DB = Neo4j.create(users)
    //    Logger.info("RESULT FROM DB: " + results_from_DB.toString)

    //    Logger.info("New user query status: " + Neo4j.addUser(123456, "Dzidek", "Kowalski", ArrayBuffer()))

    //    Logger.info("Get all users query status: " + Neo4j.getAllUsers)
//    
//    Neo4j.addUser(1, "A", "A", mutable.Buffer())
//    Neo4j.addUser(2, "B", "B", mutable.Buffer())
//    
//    Neo4j.addRelation(1, 2, "XOXOX")
    
    User.createRelations

    //Logger.info("Clean DB query status: " + Neo4j.cleanDB)

    Ok(views.html.index(users))
  }

  def clean = Action {
    Neo4j.cleanDB

    Ok(views.html.index(List()))
  }

}