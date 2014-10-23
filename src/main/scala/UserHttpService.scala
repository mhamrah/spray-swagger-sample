package com.gettyimages.spray.swagger

import com.wordnik.swagger.annotations._
import javax.ws.rs.Path
import spray.routing.HttpService
import spray.httpx.Json4sSupport

@Api(value = "/user", description = "Operations about users.", produces="application/json", position=1)
trait UserHttpService extends HttpService {

  val routes = readRoute ~ getUser

  @ApiOperation(value = "Updated user", notes = "This can only be done by the logged in user.", nickname = "updateUser", httpMethod = "PUT")
  @ApiImplicitParams(Array(
    new ApiImplicitParam(name = "username", value = "ID of pet that needs to be updated", required = true, dataType = "string", paramType = "path"),
    new ApiImplicitParam(name = "body", value = "Updated user object.", required = false, dataType = "string", paramType = "form")
  ))
  @ApiResponses(Array(
    new ApiResponse(code = 404, message = "User not found"),
    new ApiResponse(code = 400, message = "Invalid username supplied")
  ))
  def readRoute = put { path("user" / Segment) { id =>
    complete(s"Put ${id}")
  }}

  @ApiOperation(value = "Get user by name", notes = "", response=classOf[User], nickname = "getUserByName", httpMethod = "GET")
  @ApiImplicitParams(Array(
    new ApiImplicitParam(name = "petId", value = "ID of pet that needs to be updated", required = true, dataType = "string", paramType = "path"),
    new ApiImplicitParam(name = "name", value = "Updated name of the pet.", required = false, dataType = "string", paramType = "form"),
    new ApiImplicitParam(name = "status", value = "Updated status of the pet.", required = false, dataType = "string", paramType = "form")
  ))
  @ApiResponses(Array(
    new ApiResponse(code = 404, message = "User does not exist.")
  ))
  def getUser = post { path("user" / Segment) { id => { formFields('name, 'status) { (name, status) =>
    complete(s"Posted $name, $status")
  }}}}

}

case class User(username: String, status: String)
