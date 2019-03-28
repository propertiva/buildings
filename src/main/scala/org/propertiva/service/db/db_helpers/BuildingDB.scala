package org.propertiva.service.db.db_helpers

import org.jooq.{DSLContext, Record}
import org.jooq.impl.DSL.{field, table}
import org.jooq.util.postgres.PostgresDataType
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

  def getMarketCap(time: String) = {
    val record = context.selectFrom(table("\"market_cap\"")).where("time = '" + time + "'").fetchOne

  }

  def putMarketCap(): Unit = {
    context.insertInto(table("\"market_cap\""), field("time"), field("cap"), field("volume")).values("", "", "").execute
  }
}

