package org.propertiva.service.db.db_helpers

import org.jooq.{DSLContext, Record}
import org.jooq.impl.DSL.{field, table}
import org.propertiva.model.Building
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

  def getBuilding(building: Building) = {
    val record = context.selectFrom(table("\"market_cap\"")).where("time = '" + time + "'").fetchOne
  }
}

