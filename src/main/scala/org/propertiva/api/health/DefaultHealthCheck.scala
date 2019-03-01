package org.propertiva.api.health

import com.codahale.metrics.health.HealthCheck
import com.codahale.metrics.health.HealthCheck.Result

class DefaultHealthCheck extends HealthCheck {
  override def check(): Result = Result.healthy()
}
