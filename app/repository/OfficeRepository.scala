package repository

import akka.actor.ActorSystem
import javax.inject.{Inject, Singleton}
import models.OfficeData
import play.api.libs.concurrent.CustomExecutionContext
import play.api.{Logger, MarkerContext}
import play.db.ebean.EbeanConfig

import scala.collection.JavaConverters._
import scala.concurrent.Future

class OfficeExecutionContext @Inject()(actorSystem: ActorSystem) extends CustomExecutionContext(actorSystem, "repository.dispatcher")

trait OfficeRepository {
  def findAll()(implicit mc: MarkerContext): Future[Iterable[OfficeData]]

  def findOne(id: Long)(implicit mc: MarkerContext): Future[Option[OfficeData]]

  def addOne(data: OfficeData)(implicit mc: MarkerContext): Future[Option[OfficeData]]

  def updateOne(data: OfficeData)(implicit mc: MarkerContext): Future[Option[OfficeData]]

  def deleteOne(id: Long)(implicit mc: MarkerContext): Future[Unit]
}

@Singleton
class OfficeRepositoryImpl @Inject()(ebeanConfig: EbeanConfig)(implicit ec: OfficeExecutionContext) extends OfficeRepository {
  private val logger = Logger(this.getClass)

  override def findAll()(implicit mc: MarkerContext): Future[Iterable[OfficeData]] = {
    Future {
      logger.trace(s"Fetching Offices List")
      OfficeData.find.all().asScala
    }
  }

  override def findOne(id: Long)(implicit mc: MarkerContext): Future[Option[OfficeData]] = {
    Future {
      logger.trace(s"Fetching Office By Id : $id")
      Option(OfficeData.find.byId(id))
    }
  }

  override def addOne(data: OfficeData)(implicit mc: MarkerContext): Future[Option[OfficeData]] = {
    Future {
      logger.trace(s"Inserting New Office: $data")
      OfficeData.find.db().save(data)
      Option(data)
    }
  }

  override def updateOne(data: OfficeData)(implicit mc: MarkerContext): Future[Option[OfficeData]] = {
    Future {
      logger.trace(s"Updating Office: $data")
      OfficeData.find.db().update(data)
      Option(data)
    }
  }

  override def deleteOne(id: Long)(implicit mc: MarkerContext): Future[Unit] = {
    Future {
      logger.trace(s"Removing Office By Id : $id")
      OfficeData.find.deleteById(id)
    }
  }
}
