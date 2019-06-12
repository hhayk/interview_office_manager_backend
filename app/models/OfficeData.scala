package models

import io.ebean.Finder
import javax.persistence.{Column, Entity, Id, Table}

object OfficeData {
  val find = new Finder[Long, OfficeData](classOf[OfficeData])
}

@Entity
@Table(name = "OFFICE")
class OfficeData {
  @Id
  var id = 0
  var name = ""
  @Column(name = "zipcode")
  var zipCode = 0
}