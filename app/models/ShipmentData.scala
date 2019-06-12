package models

import io.ebean.Finder
import javax.persistence._

object ShipmentData {
  val find = new Finder[Long, ShipmentData](classOf[ShipmentData])
}

@Entity
@Table(name = "SHIPMENT")
class ShipmentData {
  @Id
  var id = 0
  @ManyToOne
  var office: OfficeData = _
  @Column(name = "type")
  var `type` = 0
  var status = 0
  var weight = 0
}