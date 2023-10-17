package org.example.controller;

import org.example.controller.application.OperacionService;
import org.example.controller.application.OperacionServiceImpl;
import org.example.controller.dto.OperacionInputDto;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.FileNotFoundException;


@Path("/operacion")
public class OperacionController {
    private static OperacionService operacionService;

    public static OperacionService getInstance() throws FileNotFoundException {
        if (operacionService == null){
            return new OperacionServiceImpl();
        }
        return operacionService;
    }
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String addOperacion(){
        try {
            getInstance().addOperacion();
            return "Se han añadido las operaciones";
        } catch (Exception e) {
            System.out.println("No se ha podido añadir la operación");
             return "No se han añadido las operaciones";
        }
    }
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Iterable<String> getAllOperacion (@PathParam("pageNumber") @DefaultValue("1") int pageNumber, @PathParam("pageSize") @DefaultValue("4") int pageSize) {
        try {
            return getInstance().findAllOperacion(pageNumber, pageSize);
        } catch (FileNotFoundException e) {
            System.out.println("No se encontró el archivo\\" + e);
            return null;
        }
    }
    @GET
    @Path("/byId/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getOperacionById (@PathParam("id") String id){
        try {
            return getInstance().findOperacionById(id);
        } catch (FileNotFoundException e) {
            System.out.println("No se encontró el archivo\\" + e);
            return null;
        }
    }
    @PUT
    @Path("/update/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String updateOperacion (@PathParam("id") String id, OperacionInputDto operacionInputDto){
        try {
            getInstance().updateOperacion(id, operacionInputDto);
            return "Se ha actualizado el archivo de operaciones, la operación con id: " + id;
        } catch (FileNotFoundException e) {
            System.out.println("No se encontró el archivo\\" + e);
            return "No se ha podido actualizar la operación con id: " + id;
        }
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
