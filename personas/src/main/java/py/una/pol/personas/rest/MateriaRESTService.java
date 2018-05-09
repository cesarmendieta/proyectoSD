package py.una.pol.personas.rest;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import py.una.pol.personas.model.Materia;
import py.una.pol.personas.model.Persona;
import py.una.pol.personas.service.MateriaService;
import py.una.pol.personas.service.PersonaService;

@Path("/asignaturas")
@RequestScoped
public class MateriaRESTService {
	@Inject
    private Logger log;

    @Inject
    MateriaService asignaturaService;

    

    @GET
    @Path("/PorCedula/{personaId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Materia> listarAsignaturas(@PathParam("personaId") long personaId) throws Exception{
        return asignaturaService.listarAsignaturas(personaId);
    }
    
    @GET
    @Path("/PorAsignatura/{asignaturaId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Persona> listarPersonas(@PathParam("asignaturaId") long asignaturaId) throws Exception{
        return asignaturaService.listarPersonas(asignaturaId);
    }
    
    /**
     * Creates a new member from the values provided. Performs validation, and will return a JAX-RS response with either 200 ok,
     * or with a map of fields, and related errors.
     */
    @POST
    @Path("/crear")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crear(Materia p) {

        Response.ResponseBuilder builder = null;

        try {
        	asignaturaService.crear(p);
            // Create an "ok" response
            
            //builder = Response.ok();
            builder = Response.status(201).entity("Asignatura creada exitosamente");
            
        } catch (SQLException e) {
            // Handle the unique constrain violation
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("bd-error", e.getLocalizedMessage());
            builder = Response.status(Response.Status.CONFLICT).entity(responseObj);
        } catch (Exception e) {
            // Handle generic exceptions
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }

        return builder.build();
    }
    
   
    @POST
    @Path("/actualizar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response modificar(Materia p) {

        Response.ResponseBuilder builder = null;

        try {
        	asignaturaService.actualizar(p);
            // Create an "ok" response
            
            //builder = Response.ok();
            builder = Response.status(201).entity("Asignatura actualizada exitosamente");
            
        } catch (SQLException e) {
            // Handle the unique constrain violation
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("bd-error", e.getLocalizedMessage());
            builder = Response.status(Response.Status.CONFLICT).entity(responseObj);
        } catch (Exception e) {
            // Handle generic exceptions
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }

        return builder.build();
    }
    

   @DELETE
   @Path("/borrar/{id}")
   public Response borrar(@PathParam("id") long id)
   {      
	   Response.ResponseBuilder builder = null;
	   try {
		   
		   if(asignaturaService.seleccionarPorId(id) == null) {
			   
			   builder = Response.status(Response.Status.NOT_ACCEPTABLE).entity("Asignatura no existe.");
		   }else {
			   asignaturaService.borrar(id);
			   builder = Response.status(202).entity("Asignatura borrada exitosamente.");			   
		   }
		   

		   
	   } catch (SQLException e) {
           // Handle the unique constrain violation
           Map<String, String> responseObj = new HashMap<>();
           responseObj.put("bd-error", e.getLocalizedMessage());
           builder = Response.status(Response.Status.CONFLICT).entity(responseObj);
       } catch (Exception e) {
           // Handle generic exceptions
           Map<String, String> responseObj = new HashMap<>();
           responseObj.put("error", e.getMessage());
           builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
       }
       return builder.build();
   }
	
	 
	
	
}
