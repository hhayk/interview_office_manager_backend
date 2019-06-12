package services

import javax.inject.{Inject, Singleton}
import models.ShipmentData
import repository.ShipmentRepositoryImpl

import scala.concurrent.Future

@Singleton
class ShipmentService @Inject()(shipmentRepository: ShipmentRepositoryImpl) {
  def findAll(): Future[Iterable[ShipmentData]] = {
    shipmentRepository.findAll()
  }

  def findOne(id: Long): Future[Option[ShipmentData]] = {
    shipmentRepository.findOne(id)
  }

  def addOne(data: ShipmentData): Future[Option[ShipmentData]] = {
    shipmentRepository.addOne(data)
  }

  def updateOne(data: ShipmentData): Future[Option[ShipmentData]] = {
    shipmentRepository.updateOne(data)
  }

  def deleteOne(id: Long): Future[Unit] = {
    shipmentRepository.deleteOne(id)
  }
}
