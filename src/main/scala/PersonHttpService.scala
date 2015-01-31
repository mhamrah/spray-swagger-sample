package com.gettyimages.spray.swagger

import javax.ws.rs.Path

import com.wordnik.swagger.annotations._
import spray.http.StatusCodes.OK
import spray.httpx.Json4sSupport
import spray.routing.HttpService

@Api(value = "/person", description = "Operations about people.", produces = "application/json", position = 2)
trait PersonHttpService extends HttpService {

  val routes = postPerson

  @ApiOperation(value = "Post a person", notes = "", nickname = "postPerson", httpMethod = "POST")
  @ApiImplicitParams(Array(
    new ApiImplicitParam(name = "body", value = "Person with name", dataType = "Person", required = true, paramType = "body")
  ))
  @ApiResponses(Array(
    new ApiResponse(code = 200, message = "Person got created"),
    new ApiResponse(code = 500, message = "Internal server error")
  ))
  def postPerson =
    path("person") {
      post {
        complete(OK)
      }
    }

  // Workaround to show datatype of post request without using it in another route
  // Related: https://github.com/swagger-api/swagger-core/issues/606
  // Why is this still showing even though it's set to hidden? See https://github.com/martypitt/swagger-springmvc/issues/447
  @ApiOperation(value = "IGNORE", notes = "", hidden = true, httpMethod = "GET", response = classOf[Person])
  protected def showPerson = Unit

}

case class Person(firstname: String, lastname: String)
