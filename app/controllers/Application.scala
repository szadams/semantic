package controllers

import play.api._
import play.api.mvc._

import models._

object Application extends Controller {

	def index = Action {
		
	  val users = Booting.createUsers(3)
	  Ok(views.html.index(users))
	}

}