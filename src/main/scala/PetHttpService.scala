package com.mlh.sprayswaggersample

import com.wordnik.swagger.annotations._
import javax.ws.rs.Path
import spray.routing.HttpService
import spray.httpx.Json4sSupport

@Api(value = "/pet", description = "Operations about pets.")
trait PetHttpService extends HttpService with Json4sSupport {


  @ApiOperation(value = "Find a pet by ID", notes = "Returns a pet based on ID", httpMethod = "GET", response = classOf[Pet])
  @ApiImplicitParams(Array(
    new ApiImplicitParam(name = "petId", value = "ID of pet that needs to be fetched", required = true, dataType = "integer", paramType = "path", allowableValues="[1,100000]")
  ))
  @ApiResponses(Array(
    new ApiResponse(code = 404, message = "Pet not found"),
    new ApiResponse(code = 400, message = "Invalid ID supplied")
  ))
  def readRoute = get { path("/pet" / Segment) { id =>
    complete(id)
  }}

  @ApiOperation(value = "Updates a pet in the store with form data.", notes = "", nickname = "updatePetWithForm", httpMethod = "POST", consumes="application/json")
  @ApiImplicitParams(Array(
    new ApiImplicitParam(name = "petId", value = "ID of pet that needs to be updated", required = true, dataType = "string", paramType = "path"),
    new ApiImplicitParam(name = "name", value = "Updated name of the pet.", required = false, dataType = "string", paramType = "form"),
    new ApiImplicitParam(name = "status", value = "Updated status of the pet.", required = false, dataType = "string", paramType = "form")
  ))
  @ApiResponses(Array(
    new ApiResponse(code = 404, message = "Dictionary does not exist.")
  ))
  def updateRoute = post { path("/pet" / Segment) { id => { formFields('name, 'status) { (name, status) =>
    complete("ok")
  }}}}

  @ApiOperation(value = "Deletes a pet", nickname="deletePet", httpMethod = "DELETE")
  @ApiImplicitParams(Array(
    new ApiImplicitParam(name = "petId", value = "Pet id to delete", required = true, paramType="path")
  ))
  @ApiResponses(Array(
    new ApiResponse(code = 400, message = "Invalid pet value")
  ))
  def deleteRoute = delete { path("/user" / Segment) { id => complete(id) } }

  @ApiOperation(value = "Add a new pet to the store", nickname="addPet", httpMethod="POST", consumes="application/json, application/xml")
  @ApiImplicitParams(Array(
    new ApiImplicitParam(name = "body", value = "Pet object that needs to be added to the store", required = true, paramType="body")
  ))
  @ApiResponses(Array(
    new ApiResponse(code = 405, message = "Invalid input")
  ))
  def addRoute = post { path("/user" / Segment) { id => complete(id) } }

  @ApiOperation(value = "Searches for a pet", nickname="searchPet", httpMethod="GET", produces="application/json, application/xml")
  def searchRoute = get { complete("") }
}

case class Pet(id: Int, name: String, birthDate: java.util.Date)
