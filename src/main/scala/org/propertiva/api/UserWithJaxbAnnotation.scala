package org.propertiva.api

import javax.xml.bind.annotation.{XmlAccessType, XmlAccessorType, XmlElement, XmlRootElement}

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
class UserWithJaxbAnnotation(@XmlElement() val username: String, @XmlElement() val email: String) {
  def this() = this("me", "me@example.com")
}

