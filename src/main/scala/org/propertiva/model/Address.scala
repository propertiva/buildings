package org.propertiva.model

case class Address(street1: String,
                   street2: String,
                   coordinates: Coordinates,
                   state: String,
                   city: String,
                   county: String = "",
                   zip: Int)
