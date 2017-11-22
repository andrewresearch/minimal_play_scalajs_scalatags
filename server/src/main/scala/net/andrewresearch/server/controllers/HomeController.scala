package net.andrewresearch.server.controllers

import javax.inject._

import net.andrewresearch.server.views.HomePage
import net.andrewresearch.shared.SharedObject
import play.api.mvc._


@Singleton
class HomeController @Inject() (components: ControllerComponents) extends AbstractController(components) {

  println("Message on server side: "+SharedObject.sharedMessage)

  def index = Action {
    Ok(HomePage.render("Minimal play scalajs scalatags project"))
  }


}
