package controllers

import javax.inject.Inject
import models.{OfficeData, ShipmentData}
import play.api.libs.json.{JsError, JsPath, JsSuccess, Json}
import play.api.mvc._
import services.ShipmentService

import scala.concurrent.{ExecutionContext, Future}

case class ShipmentDTO(id: Option[Int], office: OfficeDTO, `type`: Int, status: Int, weight: Int)

class ShipmentController @Inject()(cc: ControllerComponents, shipmentService: ShipmentService)(implicit exec: ExecutionContext) extends AbstractController(cc) {
  implicit val officeWrites = Json.format[OfficeDTO]
  implicit val shipmentWrites = Json.format[ShipmentDTO]

  implicit def fromDTOtoData(dto: OfficeDTO): OfficeData = {
    val data = new OfficeData()
    if (dto.id.isDefined) data.id = dto.id.get
    data.name = dto.name
    data.zipCode = dto.zipCode
    data
  }

  implicit def fromDTOtoData(dto: ShipmentDTO): ShipmentData = {
    val data = new ShipmentData()
    if (dto.id.isDefined) data.id = dto.id.get
    data.office = fromDTOtoData(dto.office)
    data.`type` = dto.`type`
    data.status = dto.status
    data.weight = dto.weight
    data
  }

  private def fromDataToDTO(data: ShipmentData): ShipmentDTO = {
    val officeDTO = OfficeDTO(Some(data.office.id), data.office.name, data.office.zipCode)
    ShipmentDTO(Some(data.id), officeDTO, data.`type`, data.status, data.weight)
  }

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
