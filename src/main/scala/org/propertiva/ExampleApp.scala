package org.propertiva

import com.fasterxml.jackson.databind.{ObjectMapper, SerializationFeature}
import com.fasterxml.jackson.datatype.joda.JodaModule
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import org.propertiva.api.filter.DiagnosticContextFilter
import org.propertiva.api.health.DefaultHealthCheck
import org.propertiva.api.resource.{ExampleResource, RootResource}
import io.dropwizard.setup.Environment

class ExampleApp extends io.dropwizard.Application[ExampleAppConfig] {
  override def getName: String = "dropwizard-scala-example"

  override def run(t: ExampleAppConfig, env: Environment): Unit = {
    env.jersey().register(new RootResource)
    env.jersey().register(new ExampleResource)
    env.jersey.register(jacksonJaxbJsonProvider)
    env.jersey.register(new DiagnosticContextFilter)
    env.healthChecks().register("default", new DefaultHealthCheck)
  }

  private def jacksonJaxbJsonProvider: JacksonJaxbJsonProvider = {
    val provider = new JacksonJaxbJsonProvider()
    val objectMapper = new ObjectMapper()
    objectMapper.registerModule(DefaultScalaModule)
    objectMapper.registerModule(new JodaModule)
    objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false)
    objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
    provider.setMapper(objectMapper)
    provider
  }
}


object ExampleApp {
  def main(args: Array[String]): Unit = new ExampleApp().run(args: _*)
}
