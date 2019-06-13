package controllers

import dtos.ShipmentDTOSupport
import javax.inject.Inject
import play.api.libs.json.{JsError, JsPath, JsSuccess, Json}
import play.api.mvc._
import services.ShipmentService

import scala.concurrent.{ExecutionContext, Future}

case class ShipmentDTO(id: Option[Int], office: OfficeDTO, `type`: Int, status: Int, weight: Int)

class ShipmentController @Inject()(cc: ControllerComponents, shipmentService: ShipmentService)(implicit exec: ExecutionContext) extends AbstractController(cc) with ShipmentDTOSupport {
  def findAll: Action[AnyContent] = Action.async { _ =>
    shipmentService.findAll()
      .map { shipments =>
        Ok(Json.toJson(shipments.map(fromDataToDTO)))
      }
  }

  def findOne(id: Long): Action[AnyContent] = Action.async { _ =>
    shipmentService.findOne(id).map {
      case Some(office) => Ok(Json.toJson(fromDataToDTO(office)))
      case None => Results.NotFound
    }
  }

  def addOne(): Action[AnyContent] = Action.async { request =>
    request.body.asJson match {
      case Some(shipmentJson) => {
        shipmentJson.validate[ShipmentDTO] match {
          case JsSuccess(dto: ShipmentDTO, path: JsPath) => {
            shipmentService.addOne(dto).map {
              case Some(shipment) => Ok(Json.toJson(fromDataToDTO(shipment)))
              case None => Results.NotFound
            }
          }
          case e: JsError => Future {
            Results.BadRequest
          }
        }
      }
      case None => Future {
        Results.BadRequest
      }
    }
  }

  def updateOne(): Action[AnyContent] = Action.async { request =>
    request.body.asJson match {
      case Some(shipmentJson) => {
        shipmentJson.validate[ShipmentDTO] match {
          case JsSuccess(dto: ShipmentDTO, path: JsPath) => {
            shipmentService.updateOne(dto).map {
              case Some(shipment) => Ok(Json.toJson(fromDataToDTO(shipment)))
              case None => Results.NotFound
            }
          }
          case e: JsError => Future {
            Results.BadRequest
          }
        }
      }
      case None => Future {
        Results.BadRequest
      }
    }
  }

  def deleteOne(id: Long): Action[AnyContent] = Action.async { _ =>
    shipmentService.deleteOne(id).map(_ => Ok("Done"))
  }
}
