package com.mlh.sprayswaggersample

import akka.actor.{Actor, ActorSystem, Props, ActorLogging}
import akka.actor.ActorDSL._
import akka.io.IO
import spray.can.Http
import spray.routing._
import spray.util._
import akka.io.Tcp._

object SampleApp extends App {
  implicit val system = ActorSystem("spray-swagger-sample-system")

  /* Spray Service */
  val service= system.actorOf(Props[SampleServiceActor], "spray-swagger-sample-service")

  val ioListener = actor("ioListener")(new Act with ActorLogging {
    become {
      case b @ Bound(connection) => log.info(b.toString)
    }
  })

  IO(Http).tell(Http.Bind(service, SampleConfig.HttpConfig.interface, SampleConfig.HttpConfig.port), ioListener)

}
