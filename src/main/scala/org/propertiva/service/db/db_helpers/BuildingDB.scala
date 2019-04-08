package org.propertiva.service.db.db_helpers

import org.jooq.{DSLContext, Record}
import org.jooq.impl.DSL.{field, table}
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
}

class BuildingDB private(var context: DSLContext) {

  def saveBuildings(buildings: List[Building]) = {

    buildings.foreach(building => saveBuilding(building))
  }

  def saveBuilding(building: Building) = {

    context
      .insertInto(table(s"\"${BuildingDB.getDBName}\""),
        field(Building.ID), field(Building.CODE), field(Building.STREET_ADR),field(Building.ZIP_CODE),field(Building.LAYOUT), field(Building.SIZE), field(Building.LATITUDE), field(Building.LONGITUDE))
      .values(building.id, building.code, building.getStreetAddress,building.getZipCode, building.layout, building.size, building.getCoordinates().latitude, building.getCoordinates().longitude)
      .execute
  }

  def getBuilding(id: String) = {

    val record = context.selectFrom(table(s"\"${BuildingDB.getDBName}\"")).where(s"${Building.ID} = '" + id + "'").fetchOne
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

