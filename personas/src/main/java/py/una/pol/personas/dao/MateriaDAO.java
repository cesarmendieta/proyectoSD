package py.una.pol.personas.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

import py.una.pol.personas.model.Materia;
import py.una.pol.personas.model.Persona;




@Stateless
public class MateriaDAO {
	@Inject
    private Logger log;
    
	/**
	 * 
	 * @param condiciones 
	 * @return
	 */
	
	
		public List<Persona> desplegarAlumnos(long Id) throws SQLException {
			//String query = "SELECT id, nombre FROM asignatura WHERE id = ?";
			
			String SQL ="SELECT p.cedula, p.nombre, p.apellido FROM persona p " + 
			" JOIN persona_asignatura pa ON p.cedula = pa.id_persona " + 
			" join asignatura a on a.id =pa.id_asignatura  where a.id = ?";
			
			List<Persona> lista = new ArrayList<Persona>();
			
			Connection conn = null; 
	        try 
	        {
	        	conn = Bd.connect();
	        	PreparedStatement pstmt = conn.prepareStatement(SQL);
	        	pstmt.setLong(1, Id);
	        	
	        	ResultSet rs = pstmt.executeQuery();

	        	while(rs.next()) {
	        		Persona p = new Persona();
	        		p.setCedula(rs.getLong(1));
	        		p.setNombre(rs.getString(2));
	        		p.setApellido(rs.getString(3));
	        		lista.add(p);
	        	}
	        	
	        } catch (SQLException ex) {
	            log.severe("Error en la seleccion: " + ex.getMessage());
	        }
	        finally  {
	        	try{
	        		conn.close();
	        	}catch(Exception ef){
	        		log.severe("No se pudo cerrar la conexion a BD: "+ ef.getMessage());
	        	}
	        }
			return lista;

		}
	
	
	
	public List<Materia> desplegarMaterias(long Id) throws SQLException {
		//String query = "SELECT id, nombre FROM asignatura WHERE id = ?";
		
		String SQL ="SELECT a.id, a.nombre FROM persona p" + 
		" JOIN persona_asignatura pa ON p.cedula = pa.id_persona " + 
		" join asignatura a on a.id =pa.id_asignatura  where p.cedula = ?";
		
		List<Materia> lista = new ArrayList<Materia>();
		
		
		Connection conn = null; 
        try 
        {
        	conn = Bd.connect();
        	PreparedStatement pstmt = conn.prepareStatement(SQL);
        	pstmt.setLong(1, Id);
        	
        	ResultSet rs = pstmt.executeQuery();

        	while(rs.next()) {
        		Materia p = new Materia();
        		p.setId(rs.getLong(1));
        		p.setNombre(rs.getString(2));
        		lista.add(p);
        	}
        	
        } catch (SQLException ex) {
            log.severe("Error en la seleccion: " + ex.getMessage());
        }
        finally  {
        	try{
        		conn.close();
        	}catch(Exception ef){
        		log.severe("No se pudo cerrar la conexion a BD: "+ ef.getMessage());
        	}
        }
		return lista;

	}
	
	
	public long crear(Materia p) throws SQLException {

        String SQL = "INSERT INTO asignatura(id, nombre) "
                + "VALUES(?,?)";
 
        long id = 0;
        Connection conn = null;
        
        try 
        {
        	conn = Bd.connect();
        	PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setLong(1, p.getId());
            pstmt.setString(2, p.getNombre());
            
 
            int affectedRows = pstmt.executeUpdate();
            // check the affected rows 
            if (affectedRows > 0) {
                // get the ID back
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                    }
                } catch (SQLException ex) {
                	throw ex;
                }
            }
        } catch (SQLException ex) {
        	throw ex;
        }
        finally  {
        	try{
        		conn.close();
        	}catch(Exception ef){
        		log.severe("No se pudo cerrar la conexion a BD: "+ ef.getMessage());
        	}
        }
        log.severe("valor retornado de id = "+id );	
        return id;
    }
	
	
	
	public long actualizar(Materia p) throws SQLException {

        String SQL = "UPDATE asignatura SET nombre = ?  WHERE id = ? ";
 
        long id = 0;
        Connection conn = null;
        
        try 
        {
        	conn = Bd.connect();
        	PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, p.getNombre());
            pstmt.setLong(2, p.getId());
 
            int affectedRows = pstmt.executeUpdate();
            // check the affected rows 
            if (affectedRows > 0) {
                // get the ID back
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
        	log.severe("Error en la actualizacion: " + ex.getMessage());
        }
        finally  {
        	try{
        		conn.close();
        	}catch(Exception ef){
        		log.severe("No se pudo cerrar la conexion a BD: "+ ef.getMessage());
        	}
        }
        return id;
    }
	
	public long borrar(long numeroId) throws SQLException {

        String SQL = "DELETE FROM asignatura WHERE id = ? ";
 
        long id = 0;
        Connection conn = null;
        
        try 
        {
        	conn = Bd.connect();
        	PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setLong(1, numeroId);
 
            int affectedRows = pstmt.executeUpdate();
            // check the affected rows 
            if (affectedRows > 0) {
                // get the ID back
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                    }
                } catch (SQLException ex) {
                	log.severe("Error en la eliminación: " + ex.getMessage());
                	throw ex;
                }
            }
        } catch (SQLException ex) {
        	log.severe("Error en la eliminación: " + ex.getMessage());
        	throw ex;
        }
        finally  {
        	try{
        		conn.close();
        	}catch(Exception ef){
        		log.severe("No se pudo cerrar la conexion a BD: "+ ef.getMessage());
        		throw ef;
        	}
        }
        return id;
    }
	
	public Materia seleccionarPorId(long numeroId) {
		String SQL = "SELECT id, nombre FROM asignatura WHERE id = ? ";
		
		Materia p = null;
		
		Connection conn = null; 
        try 
        {
        	conn = Bd.connect();
        	PreparedStatement pstmt = conn.prepareStatement(SQL);
        	pstmt.setLong(1, numeroId);
        	
        	ResultSet rs = pstmt.executeQuery();

        	while(rs.next()) {
        		p = new Materia();
        		p.setId(rs.getLong(1));
        		p.setNombre(rs.getString(2));
        		
        	}
        	
        } catch (SQLException ex) {
        	log.severe("Error en la seleccion: " + ex.getMessage());
        }
        finally  {
        	try{
        		conn.close();
        	}catch(Exception ef){
        		log.severe("No se pudo cerrar la conexion a BD: "+ ef.getMessage());
        	}
        }
		return p;

	}
	
	
}

