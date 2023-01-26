package com.markov.controllers;

import com.markov.entities.dto.WorkerDTO;
import com.markov.exceptions.BadRequestException;
import com.markov.exceptions.NoSuchWorkerException;
import com.markov.resources.WorkerService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/workers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WorkerResource {

    @Inject
    private WorkerService workerService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllWorkers(@QueryParam(value = "size") String size,
                                  @QueryParam(value = "page") String page,
                                  @QueryParam(value = "filter") String filter,
                                  @QueryParam(value = "sort") String sort) throws BadRequestException {
        return Response.ok(workerService.getAllWorkers(size, page, filter, sort)).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response addNewWorker(@Valid WorkerDTO workerDTO) throws NoSuchWorkerException {
        return Response.ok(workerService.addNewWorker(workerDTO)).build();
    }

    @GET
    @Path("/{workerId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getWorkerById(@PathParam("workerId") Integer workerId) throws NoSuchWorkerException {
        return Response.ok(workerService.getWorkerById(workerId)).build();
    }

    @GET
    @Path("/{workerId}/hire/{organizationId}/{position}/{startDate}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response hireWorker(@PathParam("workerId") Integer workerId, @PathParam("organizationId") Integer organizationId,
                               @PathParam("position") String position, @PathParam("startDate") String startDate) throws NoSuchWorkerException {
        return Response.ok(workerService.hireWorker(workerId, organizationId, position, startDate)).build();
    }

    @PUT
    @Path("/{workerId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateWorker(@PathParam("workerId") Integer workerId,
                                 @Valid WorkerDTO workerDTO) throws NoSuchWorkerException {
        System.out.println(workerDTO.getName());
        return Response.ok(workerService.updateWorker(workerId, workerDTO)).build();
    }

    @DELETE
    @Path("/{workerId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteWorkerById(@PathParam("workerId") Integer workerId) throws NoSuchWorkerException {
        workerService.deleteWorkerById(workerId);
        return Response.ok().build();
    }

    @GET
    @Path("/end-date/{endDateValue}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response countWorkersWithBiggerEndDate(@PathParam("endDateValue") String endDateValue) {
        return Response.ok(workerService.getNumberOfWorkersWithBiggerEndDate(endDateValue)).build();
    }

    @GET
    @Path("/position/{positionValue}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response countWorkersWithSmallerPosition(@PathParam("positionValue") String positionValue){
        return Response.ok(workerService.getNumberOfWorkersWithSmallerPosition(positionValue)).build();
    }

    @GET
    @Path("/name/{nameValue}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getWorkersWithName(@PathParam("nameValue") String nameValue) throws BadRequestException {
        return Response.ok(workerService.getWorkersWithNameContains(nameValue)).build();
    }
}
