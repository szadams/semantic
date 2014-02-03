package controllers

import play.api._
import play.api.mvc._
import models._
import scala.util.Random

object Application extends Controller {

  def index = Action {
    val r = new Random().nextInt(20)
    val users = Booting.createUsers(r)
    
    val results_from_DB = Neo4j.create
    println(results_from_DB)
    
    println("isAdded:  " + Neo4j.isAdded)
    println("isSucces: " + Neo4j.isSucces)
    
    Ok(views.html.index(users))
  }

}