package views

import scalatags.Text.all._
import scalatags.Text.{tags,tags2}

import play.twirl.api.Html

trait GenericPage {

  def render(title:String) = Html("<!DOCTYPE html>" +page(title).render)

  def page(titleStr:String) = tags.html(tags.head(tags2.title(titleStr)))

}
