package org.propertiva.service.db

class DBMgr {

  private var dbMgr : DBMgr = null
  private val POSTGRES_INTERFACE = "postgres"
  private val MONGO_INTERFACE = "mongo"
  private var dbInterface : DBInterface = null

  def getDBMgr: DBMgr = {
    if (dbMgr == null) dbMgr = new DBMgr()
    dbMgr
  }

  def getInterface: DBInterface = getInterface(POSTGRES_INTERFACE)

  def getInterface(interfaceType: String): DBInterface = {
    if (dbInterface == null) {
      dbInterface = new PostgresDBInterface()
      interfaceType match {
        case POSTGRES_INTERFACE =>
          dbInterface
        case MONGO_INTERFACE =>
          dbInterface
        case _ =>
          dbInterface
      }
    }
    else dbInterface
  }

}
