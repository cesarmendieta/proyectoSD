package py.una.pol.personas.model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;


@SuppressWarnings("serial")
@XmlRootElement

public class Materia implements Serializable{
	
	Long id;
	String nombre;
	
	public Materia( ){
			
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}
