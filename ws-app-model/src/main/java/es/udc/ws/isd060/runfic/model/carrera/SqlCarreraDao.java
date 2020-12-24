package es.udc.ws.isd060.runfic.model.carrera;

import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;

public interface SqlCarreraDao {

    // Autor : Yago
    Carrera create(Connection connection, Carrera carrera);

    // Autor : Carlos
    Carrera find(Connection connection, Long idCarrera) throws InstanceNotFoundException;

    // Autor : Yago
    List<Carrera> find(Connection connection, LocalDateTime fechaCelebracion, String ciudad);

    // Autor : Brais
    void update(Connection connection, Long idCarrera) throws InstanceNotFoundException;

    // Autor : Yago
    void remove(Connection connection, Long idCarrera) throws InstanceNotFoundException;


}
