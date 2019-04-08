package org.propertiva.service.db

import org.propertiva.model.Building

trait DBInterface {

  def saveBuildings(buildings: List[Building])

}
