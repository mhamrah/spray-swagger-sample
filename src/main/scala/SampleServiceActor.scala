package com.mlh.sprayswaggersample

import akka.actor.{Actor, ActorSystem, Props, ActorLogging}
import spray.routing._
import com.gettyimages.spray.swagger._
import scala.reflect.runtime.universe._
import com.wordnik.swagger.model.ApiInfo

class SampleServiceActor
  extends HttpServiceActor
  with ActorLogging {

    override def actorRefFactory = context

    val pets = new PetHttpService {
      def actorRefFactory = context
    }

    val users = new UserHttpService {
      def actorRefFactory = context
    }

    def receive = runRoute(pets.routes ~ users.routes ~ swaggerService.routes ~
      get {
        pathPrefix("") { pathEndOrSingleSlash {
            getFromResource("swagger-ui/index.html")
          }
        } ~
        getFromResourceDirectory("swagger-ui")
      })

  val swaggerService = new SwaggerHttpService {
    override def apiTypes = Seq(typeOf[PetHttpService], typeOf[UserHttpService])
    override def apiVersion = "2.0"
    override def baseUrl = "http://localhost:8080"
    override def docsPath = "api-docs"
    override def actorRefFactory = context
    override def apiInfo = Some(new ApiInfo("Spray-Swagger Sample", "A sample petstore service using spray and spray-swagger.", "TOC Url", "Michael Hamrah @mhamrah", "Apache V2", "http://www.apache.org/licenses/LICENSE-2.0"))

    //authorizations, not used
  }
}
