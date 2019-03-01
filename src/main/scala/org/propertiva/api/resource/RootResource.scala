package org.propertiva.api.resource

import java.lang.System.getProperty
import javax.ws.rs.core.MediaType.APPLICATION_JSON
import javax.ws.rs.core.Response
import javax.ws.rs.{GET, Path, Produces}

@Path("/")
class RootResource {

  @Produces(Array(APPLICATION_JSON))
  @GET
  def default: Response = {
    Response.ok.entity(Map("name" -> "propertiva-to-bad", "message" -> "MVP lets go!",
      "scala.version" -> getProperty("java.version"), "java.vm.version" -> getProperty("java.vm.version"))).build()
  }

}
