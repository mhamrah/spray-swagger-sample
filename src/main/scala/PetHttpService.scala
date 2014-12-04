package com.mlh.sprayswaggersample

import com.wordnik.swagger.annotations._
import javax.ws.rs.Path
import spray.routing.HttpService

@Api(value = "/pet", description = "Operations about pets.", position = 0)
trait PetHttpService extends HttpService {

  import Json4sSupport._

  val routes = readRoute ~ updateRoute ~ deleteRoute ~ addRoute ~ searchRoute ~ readRouteForNestedResource


  @ApiOperation(value = "Find a pet by ID", notes = "Returns a pet based on ID", httpMethod = "GET", response = classOf[Pet])
  @ApiImplicitParams(Array(
    new ApiImplicitParam(name = "petId", value = "ID of pet that needs to be fetched", required = true, dataType = "integer", paramType = "path")
  ))
  @ApiResponses(Array(
    new ApiResponse(code = 404, message = "Pet not found"),
    new ApiResponse(code = 400, message = "Invalid ID supplied")
  ))
  def readRoute = get {
    path("pet" / IntNumber) { id =>
      complete(Pet(id, "Sparky", new java.util.Date()))
    }
  }

  @ApiOperation(value = "Updates a pet in the store with form data.", notes = "", nickname = "updatePetWithForm", httpMethod = "POST")
  @ApiImplicitParams(Array(
    new ApiImplicitParam(name = "petId", value = "ID of pet that needs to be updated", required = true, dataType = "string", paramType = "path"),
    new ApiImplicitParam(name = "name", value = "Updated name of the pet.", required = false, dataType = "string", paramType = "form"),
    new ApiImplicitParam(name = "status", value = "Updated status of the pet.", required = false, dataType = "string", paramType = "form")
  ))
  @ApiResponses(Array(
    new ApiResponse(code = 404, message = "Dictionary does not exist.")
  ))
  def updateRoute = post {
    path("pet" / Segment) { id => {
      formFields('name, 'status) { (name, status) =>
        complete("ok")
      }
    }
    }
  }

  @ApiOperation(value = "Deletes a pet", nickname = "deletePet", httpMethod = "DELETE")
  @ApiImplicitParams(Array(
    new ApiImplicitParam(name = "petId", value = "Pet id to delete", required = true, dataType = "string", paramType = "path")
  ))
  @ApiResponses(Array(
    new ApiResponse(code = 400, message = "Invalid pet value")
  ))
  def deleteRoute = delete {
    path("pet" / Segment) { id => complete(s"Deleted $id")}
  }

  @ApiOperation(value = "Add a new pet to the store", nickname = "addPet", httpMethod = "POST", consumes = "application/json, application/vnd.custom.pet")
  @ApiImplicitParams(Array(
    new ApiImplicitParam(name = "body", value = "Pet object that needs to be added to the store", dataType = "Pet", required = true, paramType = "body")
  ))
  @ApiResponses(Array(
    new ApiResponse(code = 405, message = "Invalid input")
  ))
  def addRoute = post {
    path("/pet" / Segment) { id => complete(id)}
  }

  @ApiOperation(value = "Searches for a pet", nickname = "searchPet", httpMethod = "GET", produces = "application/json, application/xml")
  def searchRoute = get {
    path("pet") {
      complete(new Pet(1, "sparky", new java.util.Date()))
    }
  }

  @Path("/findByTags")
  @ApiOperation(value = "Find Pets by Tags", httpMethod = "GET", nickname = "findPetsByTags")
  @ApiImplicitParams(Array(
    new ApiImplicitParam(name = "petId", value = "Tags to filter by", required = true, dataType = "string", paramType = "query", allowMultiple = true)
  ))
  def findByTags = get {
    path("findByTags") {
      complete(List(new Pet(1, "sparky", new java.util.Date())))
    }
  }

  @Path("/{petId}/friends/{friendId}")
  @ApiOperation(value = "Find Pet's friend by friendId", httpMethod = "GET", nickname = "findPetsFriendById")
  @ApiImplicitParams(Array(
    new ApiImplicitParam(name = "petId", value = "Pet id of the pet whose friend needs to be fetched", required = true, dataType = "string", paramType = "path"),
    new ApiImplicitParam(name = "friendId", value = "Id of the friend that needs to be fetched", required = true, dataType = "string", paramType = "path")
  ))
  def readRouteForNestedResource = get {
    path("pet" / Segment / "friends" / Segment) {
      (petId, friendId) => {
        complete(new Pet(2, "scooby (sparky's friend)", new java.util.Date()))
      }
    }
  }
}

case class Pet(id: Int, name: String, birthDate: java.util.Date)
