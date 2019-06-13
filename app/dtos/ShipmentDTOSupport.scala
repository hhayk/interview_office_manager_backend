package dtos

import controllers.{OfficeDTO, ShipmentDTO}
import models.ShipmentData
import play.api.libs.json.Json

trait ShipmentDTOSupport extends OfficeDTOSupport {
  implicit val shipmentWrites = Json.format[ShipmentDTO]

  implicit def fromDTOtoData(dto: ShipmentDTO): ShipmentData = {
    val data = new ShipmentData()
    if (dto.id.isDefined) data.id = dto.id.get
    data.office = fromDTOtoData(dto.office)
    data.`type` = dto.`type`
    data.status = dto.status
    data.weight = dto.weight
    data
  }

  implicit def fromDataToDTO(data: ShipmentData): ShipmentDTO = {
    ShipmentDTO(
      Some(data.id),
      OfficeDTO(Some(data.office.id), data.office.name, data.office.zipCode),
      data.`type`,
      data.status,
      data.weight
    )
  }
}
