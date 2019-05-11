package org.propertiva.service.db.db_helpers

import org.jooq.{DSLContext, Record}
import org.jooq.impl.DSL.{field, table}
import org.jooq.impl.SQLDataType
import org.jooq.util.postgres.PostgresDataType
import org.propertiva.exceptions.BuildingException
import org.propertiva.model.{Address, Building, Coordinates}
import org.slf4j.{Logger, LoggerFactory}

object BuildingDB {
  private var obj: BuildingDB = null
  private val LOGGER = LoggerFactory.getLogger(classOf[BuildingDB])

  def get(dslContext: DSLContext): BuildingDB = {
    if (obj == null) {
      obj = new BuildingDB(dslContext)
    }
    obj
  }

  val getDBName = "building"
  val getBuildingTableName = "buildings"
}

class BuildingDB private(var context: DSLContext) {

  def saveBuildings(buildings: List[Building]) = {

    buildings.foreach(building => saveBuilding(building))
  }

  def saveBuilding(building: Building) = {

    /*context.createTable(BuildingDB.getBuildingTableName)
      .column(Building.ID, SQLDataType.BIGINT)
      .column(Building.CODE, SQLDataType.INTEGER)
      .column(Building.STREET_ADR, SQLDataType.VARCHAR)
      .column(Building.ZIP_CODE, SQLDataType.INTEGER)
      .column(Building.LAYOUT, SQLDataType.INTEGER)
      .column(Building.SIZE, SQLDataType.INTEGER)
      .column(Building.LATITUDE, SQLDataType.DOUBLE)
      .column(Building.LONGITUDE, SQLDataType.DOUBLE)
      .execute();*/

    context
      .insertInto(table(s"""${BuildingDB.getBuildingTableName}"""),
        field(Building.ID, classOf[Long]), field(Building.CODE, classOf[Int]), field(Building.STREET_ADR, classOf[String]),field(Building.ZIP_CODE, classOf[Int]),field(Building.LAYOUT, classOf[Int]), field(Building.SIZE, classOf[Int]), field(Building.LATITUDE, classOf[Double]), field(Building.LONGITUDE, classOf[Double]))
      .values(building.id, building.code, building.getStreetAddress,building.getZipCode, building.layout, building.size, building.getCoordinates().latitude, building.getCoordinates().longitude)
      .execute
  }

  def getBuilding(id: String) = {

    val record = context.selectFrom(table(s"""${BuildingDB.getDBName}""")).where(s"${Building.ID} = '" + id + "'").fetchOne
    if(record != null){
      val id = record.get(field(Building.ID, PostgresDataType.BIGINT))
      val code = record.get(field(Building.CODE, PostgresDataType.INT))
      val layout = record.get(field(Building.LAYOUT, PostgresDataType.INT))
      val size = record.get(field(Building.SIZE, PostgresDataType.INT))
      val thumbnailUrl = record.get(field(Building.THUMBNAIL_URL, PostgresDataType.TEXT))
      val address =  getAddress(record)
      new Building(address, id, code, layout, size, thumbnailUrl)

    }else{
      throw new BuildingException(s"Did not find building: $id")
    }
  }

  private def getAddress(record: Record) : Address = {

    val longitude = record.get(field(Building.LONGITUDE, PostgresDataType.FLOAT8))
    val latitude = record.get(field(Building.LATITUDE, PostgresDataType.FLOAT8))
    val streetAddr = record.get(field(Building.STREET_ADR, PostgresDataType.TEXT))
    val zip = record.get(field(Building.ZIP_CODE, PostgresDataType.INT))
    val coordinates = Coordinates(longitude,latitude)
    Address(streetAddr, "", coordinates,"","","", zip)
  }
}

