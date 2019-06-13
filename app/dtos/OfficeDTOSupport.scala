package dtos

import controllers.OfficeDTO
import models.OfficeData
import play.api.libs.json._

trait OfficeDTOSupport {
  implicit val officeWrites = Json.format[OfficeDTO]

  implicit def fromDTOtoData(dto: OfficeDTO): OfficeData = {
    val data = new OfficeData()
    if (dto.id.isDefined) data.id = dto.id.get
    data.name = dto.name
    data.zipCode = dto.zipCode
    data
  }

  implicit def fromDataToDTO(data: OfficeData): OfficeDTO = {
    OfficeDTO(
      Some(data.id),
      data.name,
      data.zipCode
    )
  }
}

