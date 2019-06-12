package repository

import akka.actor.ActorSystem
import javax.inject.{Inject, Singleton}
import models.{OfficeData, ShipmentData}
import play.api.libs.concurrent.CustomExecutionContext
import play.api.{Logger, MarkerContext}

import scala.collection.JavaConverters._
import scala.concurrent.Future

class ShipmentExecutionContext @Inject()(actorSystem: ActorSystem) extends CustomExecutionContext(actorSystem, "repository.dispatcher")

trait ShipmentRepository {
  def findAll()(implicit mc: MarkerContext): Future[Iterable[ShipmentData]]

  def findOne(id: Long)(implicit mc: MarkerContext): Future[Option[ShipmentData]]

  def addOne(data: ShipmentData)(implicit mc: MarkerContext): Future[Option[ShipmentData]]

  def updateOne(data: ShipmentData)(implicit mc: MarkerContext): Future[Option[ShipmentData]]

  def deleteOne(id: Long)(implicit mc: MarkerContext): Future[Unit]
}

@Singleton
class ShipmentRepositoryImpl @Inject()()(implicit ec: ShipmentExecutionContext) extends ShipmentRepository {
  private val logger = Logger(this.getClass)

  override def findAll()(implicit mc: MarkerContext): Future[Iterable[ShipmentData]] = {
    Future {
      logger.trace(s"Fetching Shipment list")
      ShipmentData.find.all().asScala
    }
  }

  override def findOne(id: Long)(implicit mc: MarkerContext): Future[Option[ShipmentData]] = {
    Future {
      logger.trace(s"Fetching Shipment By Id : $id")
      Option(ShipmentData.find.byId(id))
    }
  }

  override def addOne(data: ShipmentData)(implicit mc: MarkerContext): Future[Option[ShipmentData]] = {
    Future {
      logger.trace(s"Inserting New Shipment: $data")
      ShipmentData.find.db().save(data)
      Option(data)
    }
  }

  override def updateOne(data: ShipmentData)(implicit mc: MarkerContext): Future[Option[ShipmentData]] = {
    Future {
      logger.trace(s"Updating Shipment: $data")
      ShipmentData.find.db().update(data)
      Option(data)
    }
  }

  override def deleteOne(id: Long)(implicit mc: MarkerContext): Future[Unit] = {
    Future {
      logger.trace(s"Removing Shipment By Id : $id")
      ShipmentData.find.deleteById(id)
    }
  }
}
