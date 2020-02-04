package py.una.pol.personas.service;


import javax.ejb.Stateless;
import javax.inject.Inject;

import py.una.pol.personas.dao.MateriaDAO;
import py.una.pol.personas.dao.MateriaDAO;
import py.una.pol.personas.model.Materia;
import py.una.pol.personas.model.Persona;

import java.util.List;
import java.util.logging.Logger;


//The @Stateless annotation eliminates the need for manual transaction demarcation
@Stateless
public class MateriaService {

 @Inject
 private Logger log;

 @Inject
 private MateriaDAO dao;

 public void crear(Materia p) throws Exception {
     log.info("Creando Asignatura: " + p.getNombre());
     try {
     	dao.crear(p);
     }catch(Exception e) {
     	log.severe("ERROR al crear asignatura: " + e.getLocalizedMessage() );
     	throw e;
     }
     log.info("Asignatura creada con éxito: " + p.getNombre() );
 }
 
 
 
 public long actualizar(Materia p) throws Exception{
	 return dao.actualizar(p);
 }
 
 public long borrar(long numeroId) throws Exception {
 	return dao.borrar(numeroId);
 }
 
 public Materia seleccionarPorId(long numeroId) {
 	return dao.seleccionarPorId(numeroId);
 }
 
 public List<Materia> listarAsignaturas(long Id)throws Exception{
	 return dao.desplegarMaterias(Id);
 }
 
 public List<Persona> listarPersonas(long Id)throws Exception{
	 return dao.desplegarAlumnos(Id);
 }

  
}