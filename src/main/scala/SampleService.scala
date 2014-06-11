package com.mlh.sprayswaggersample

import akka.actor.{Actor, ActorSystem, Props, ActorLogging}
import spray.routing._

class SampleActor
  extends Actor
  with ActorLogging {

    def actorRefFactory = context
    def receive = runRoute(swaggerService.route)

  val swaggerService = new SwaggerHttpService {
    override def apiTypes = Seq(typeOf[PetHttpService], typeOf[UserHttpService])
    override def apiVersion = "2.0"
    override def basePath = "http://some.domain.com/api"
    override def docsPath = "docs-are-here"
    override def actorRefFactory = context
    //apiInfo, not used
    //authorizations, not used
  }
}
