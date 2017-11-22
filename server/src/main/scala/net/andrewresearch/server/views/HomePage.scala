package net.andrewresearch.server.views

import scalatags.Text.all._

/**
  * Created by andrew@andrewresearch.net on 20/11/17.
  */
object HomePage extends GenericPage {

  override def page(titleStr:String) = html(
    head(
      scalatags.Text.tags2.title(titleStr) //,
    ),
    body(
      div(
        h1(id:="title", titleStr),
        p(id :="sjs","..."),
        p("This text is in a view made by scalatags")
      ),
      script(src:="./assets/client-fastopt.js")
    )
  )
}
