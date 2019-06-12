package controllers

import javax.inject.Inject
import models.OfficeData
import play.api.libs.json._
import play.api.mvc.{AbstractController, ControllerComponents, _}
import services.OfficeService

import scala.concurrent.{ExecutionContext, Future}

case class OfficeDTO(id: Option[Int], name: String, zipCode: Int)

class OfficeController @Inject()(cc: ControllerComponents, officeService: OfficeService)(implicit exec: ExecutionContext) extends AbstractController(cc) {
  implicit val officeWrites = Json.format[OfficeDTO]

  implicit def fromDTOtoData(dto: OfficeDTO): OfficeData = {
    val data = new OfficeData()
    if (dto.id.isDefined) data.id = dto.id.get
    data.name = dto.name
    data.zipCode = dto.zipCode
    data
  }

  private def fromDataToDTO(data: OfficeData): OfficeDTO = {
    OfficeDTO(Some(data.id), data.name, data.zipCode)
  }

  def findAll = Action.async { _ =>
    officeService.findAll()
      .map { offices =>
        Ok(Json.toJson(offices.map(fromDataToDTO)))
      }
  }

  def findOne(id: Long) = Action.async { _ =>
    officeService.findOne(id).map {
      case Some(office) => Ok(Json.toJson(fromDataToDTO(office)))
      case None => Results.NotFound
    }
  }

  def addOne() = Action.async { request =>
    request.body.asJson match {
      case Some(officeJson) => {
        officeJson.validate[OfficeDTO] match {
          case JsSuccess(dto: OfficeDTO, path: JsPath) => {
            officeService.addOne(dto).map {
              case Some(office) => Ok(Json.toJson(fromDataToDTO(office)))
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

  def updateOne() = Action.async { request =>
    request.body.asJson match {
      case Some(officeJson) => {
        officeJson.validate[OfficeDTO] match {
          case JsSuccess(dto: OfficeDTO, path: JsPath) => {
            officeService.updateOne(dto).map {
              case Some(office) => Ok(Json.toJson(fromDataToDTO(office)))
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

  def deleteOne(id: Long) = Action.async { _ =>
    officeService.deleteOne(id).map(_ => Ok("Done"))
  }
}
