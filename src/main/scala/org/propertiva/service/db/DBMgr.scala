package org.propertiva.service.db

object DBMgr {

  private var dbMgr: DBMgr = null
  private val POSTGRES_INTERFACE = "postgres"
  private val MONGO_INTERFACE = "mongo"


  def get: DBMgr = {
    if (dbMgr == null) dbMgr = new DBMgr()
    dbMgr
  }
}

class DBMgr private (){

  private var dbInterface: DBInterface = null

  def getInterface: DBInterface = getInterface(DBMgr.POSTGRES_INTERFACE)

  def getInterface(interfaceType: String): DBInterface = {
    if (dbInterface == null) {
      dbInterface = new PostgresDBInterface()
      interfaceType match {
        case DBMgr.POSTGRES_INTERFACE =>
          dbInterface
        case DBMgr.MONGO_INTERFACE =>
          dbInterface
        case _ =>
          dbInterface
      }
    }
    else dbInterface
  }

}
