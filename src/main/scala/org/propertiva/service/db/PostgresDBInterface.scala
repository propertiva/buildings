package org.propertiva.service.db

import org.jooq.SQLDialect
import org.jooq.impl.DSL
import org.slf4j.LoggerFactory

object PostgresDBInterface {
  private var buildingDBInterface = null
  private val LOGGER = LoggerFactory.getLogger(classOf[PostgresDBInterface])

  private def getDataSource(dbName: String) = {
    val randNum = 0 + (Math.random * ((1000 - 0) + 1)).toInt
    val source = new PGPoolingDataSource()
    source.setDataSourceName(dbName + randNum)
    source.setPortNumber(5432)
    source.setServerName("crypto-land.postgres.database.azure.com")
    source.setDatabaseName(dbName)
    source.setUser("stanwizzy@crypto-land")
    source.setPassword("Ab34567@")
    source.setMaxConnections(10)
    source
  }
}

class PostgresDBInterface() extends DBInterface {

  if (PostgresDBInterface.buildingDBInterface == null) {
    PostgresDBInterface.buildingDBInterface =
      PostgresDBInterface.buildingDBInterface.get(DSL.using(PostgresDBInterface.getDataSource(""), SQLDialect.POSTGRES));
  }


  override def persistTradables(bittrexTradables: List[Tradable], time: String): Unit = {
    PostgresDBInterface.dataInterface.persistTradables(bittrexTradables, time)
  }

}

