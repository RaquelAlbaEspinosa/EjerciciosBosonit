package org.example.controller;

import org.example.controller.application.OperacionService;
import org.example.controller.application.OperacionServiceImpl;
import org.example.controller.dto.OperacionInputDto;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("/operacion")
public class OperacionController {
    private static OperacionService operacionService;

    public static OperacionService getInstance(){
        if (operacionService == null){
            return new OperacionServiceImpl();
        }
        return operacionService;
    }
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String addOperacion(OperacionInputDto operacionInputDto){
        try {
            return getInstance().addOperacion(operacionInputDto);
        } catch (Exception e) {
            System.out.println("No se ha podido añadir la operación");
             return null;
        }
    }
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Iterable<String> getAllOperacion (@PathParam("pageNumber") @DefaultValue("1") int pageNumber, @PathParam("pageSize") @DefaultValue("4") int pageSize){
        return getInstance().findAllOperacion(pageNumber,pageSize);
    }
    @GET
    @Path("/byId/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getOperacionById (@PathParam("id") String id){
        return getInstance().findOperacionById(id);
    }
    @PUT
    @Path("/update/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String updateOperacion (@PathParam("id") String id, OperacionInputDto operacionInputDto){
        return getInstance().updateOperacion(id, operacionInputDto);
    }
    @DELETE
    @Path("/delete/{id}")
    public String deleteOperacion (@PathParam("id") String id){
        try{
            getInstance().deleteOperacion(id);
            return "Se ha borrado la operacion con id: " + id;
        } catch (Exception e){
            return "No se ha encontrado la operación con id: " + id;
        }
    }
}
