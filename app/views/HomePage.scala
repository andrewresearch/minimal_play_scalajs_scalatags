package views


import controllers.routes
import net.andrewresearch.server.ExampleService
import scalatags.Text.all._
import scalatags.Text.{tags, tags2}

/**
  * Created by andrew@andrewresearch.net on 20/11/17.
  */
object HomePage extends GenericPage {

  override def page(titleStr:String) = tags.html(
    tags.head(
      tags2.title(titleStr),
      link(rel:="stylesheet",href:=routes.Assets.versioned("stylesheets/bootstrap.min.css").url)
    ),
    body(
      div(`class`:="container-fluid",
        div(`class`:="row",raw("&nbsp;")),
        div(`class`:="row",
          div(`class`:="col-sm-10 offset-1",
            h1(id:="title", titleStr),
            p(id :="sjs","If you can see this, the JS client has NOT run properly, check the console."),
            p("This text is in a view made by scalatags"),
            p("From server: "+ExampleService.serviceText)
          )
        )
      ),
      script(src:=routes.Assets.versioned("clientjs-fastopt-bundle.js").url)
    )
  )
}
