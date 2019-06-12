package routers

import controllers.ShipmentController
import javax.inject.Inject
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

class ShipmentRouter @Inject()(controller: ShipmentController) extends SimpleRouter {
  override def routes: Routes = {
    case GET(p"/") =>
      controller.findAll()

    case GET(p"/$id") =>
      controller.findOne(id.toLong)

    case POST(p"/") =>
      controller.addOne()

    case PUT(p"/") =>
      controller.updateOne()

    case DELETE(p"/$id") =>
      controller.deleteOne(id.toLong)
  }
}
