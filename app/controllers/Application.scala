package controllers

import play.api._
import play.api.mvc._
import models._
import scala.util.Random
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object Application extends Controller {

  def index = Action {
    val r = new Random().nextInt(20)
    val users = Booting.createUsers(r)

    val results_from_DB = Neo4j.create
    Logger.info("RESULT FROM DB: " + results_from_DB.toString)
    
    Logger.info("New user: " + Neo4j.addUser(123456, "Dzidek", "Kowalski", ArrayBuffer()))

    Logger.info("Get all users: " + Neo4j.getAllUsers)
    
    Logger.info("Clean DB: " + Neo4j.cleanDB)

    Ok(views.html.index(users))
  }

}