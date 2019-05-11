package org.propertiva.service.db

import org.jooq.SQLDialect
import org.jooq.impl.DSL
import org.slf4j.LoggerFactory
import org.postgresql.ds.PGPoolingDataSource
import org.propertiva.model.Building
import org.propertiva.service.db.db_helpers.BuildingDB

object PostgresDBInterface {
  private var buildingDBInterface = BuildingDB.get(DSL.using(getDataSource(BuildingDB.getDBName), SQLDialect.POSTGRES))
  private val LOGGER = LoggerFactory.getLogger(classOf[PostgresDBInterface])

  private def getDataSource(dbName: String) = {
    val randNum = 0 + (Math.random * ((1000 - 0) + 1)).toInt
    val source = new PGPoolingDataSource()
    source.setDataSourceName(dbName + randNum)
    source.setPortNumber(5432)
    source.setServerName("localhost")
    source.setDatabaseName(dbName)
    source.setUser("stanleyopara")
    source.setPassword("")
    source.setMaxConnections(10)
    source
  }
}

class PostgresDBInterface() extends DBInterface {


  override def saveBuildings(buildings: List[Building]): Unit = {
    PostgresDBInterface.buildingDBInterface.saveBuildings(buildings)
  }

}

