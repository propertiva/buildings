package org.propertiva.model

object Building {

  val STREET_ADR = "street_adr"
  val ID = "id"
  val CODE = "code"
  val ZIP_CODE= "zip"
  val LAYOUT = "layout"
  val SIZE = "size"
  val THUMBNAIL_URL = "thumbnail_url"
  val LATITUDE = "latitude"
  val LONGITUDE = "longitude"

  val properties = List(STREET_ADR, ID, CODE, ZIP_CODE, LAYOUT, SIZE, THUMBNAIL_URL, LATITUDE, LONGITUDE )

}
class Building(address: Address,
               val id: Long,
               val code: Int,
               val layout: Int,
               val size: Int,
               val thumbnailUrl: String){

  def getZipCode() : Int ={

    address.zip
  }

  def getCoordinates() : Coordinates ={

    address.coordinates
  }

  def getStreetAddress() : String = {

    address.street1 + address.street2
  }

}
