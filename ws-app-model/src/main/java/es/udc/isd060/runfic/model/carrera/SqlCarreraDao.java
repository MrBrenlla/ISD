package es.udc.isd060.runfic.model.carrera;

import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

public interface SqlCarreraDao {

    // Autor : Yago
    public Carrera create ( Connection connection , Carrera carrera);

    // Autor : Carlos
    public Carrera find ( Connection connection , Long idCarrera) throws InstanceNotFoundException;

    // Autor : Yago
    public List<Carrera> find (Connection connection , LocalDateTime fechaCelebracion , String ciudad );// Non lanza InvalidDateException, comprobase en modelo

    // Autor : Brais
    public void update ( Connection connection , Carrera carrera ) throws InstanceNotFoundException;

    // Autor : Yago
    public void remove ( Connection connection , Carrera carrera ) throws InstanceNotFoundException;


}
