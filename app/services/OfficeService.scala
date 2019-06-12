package services

import javax.inject.{Inject, Singleton}
import models.OfficeData
import repository.OfficeRepositoryImpl

import scala.concurrent.Future

@Singleton
class OfficeService @Inject()(officeRepository: OfficeRepositoryImpl) {
  def findAll(): Future[Iterable[OfficeData]] = {
    officeRepository.findAll()
  }

  def findOne(id: Long): Future[Option[OfficeData]] = {
    officeRepository.findOne(id)
  }

  def addOne(data: OfficeData): Future[Option[OfficeData]] = {
    officeRepository.addOne(data)
  }

  def updateOne(data: OfficeData): Future[Option[OfficeData]] = {
    officeRepository.updateOne(data)
  }

  def deleteOne(id: Long): Future[Unit] = {
    officeRepository.deleteOne(id)
  }
}
