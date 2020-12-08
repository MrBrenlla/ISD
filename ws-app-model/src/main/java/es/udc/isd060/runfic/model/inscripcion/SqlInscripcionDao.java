package es.udc.isd060.runfic.model.inscripcion;


import es.udc.isd060.runfic.model.carrera.Carrera;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import java.sql.Connection;
import java.util.List;

public interface SqlInscripcionDao {

    // Autor : Brais
    Inscripcion create(Connection connection, Inscripcion inscripcion);// fullCapacityException , inscriptionsAlreadyClosed

    // Autor : Carlos
    Inscripcion find(Connection connection, Long idInscripcion) throws InstanceNotFoundException;

    // Autor : Brais
    List<Inscripcion> find(Connection connection, String emailUsuario) throws InstanceNotFoundException;

    // Autor : Brais
    List<Inscripcion> find(Connection connection, String emailUsuario, Long IdCarrera) throws InstanceNotFoundException;

    // Autor : Carlos
    void update(Connection connection, Inscripcion inscripcion) throws InstanceNotFoundException;

    // Autor : Yago
    void remove(Connection connection, Long idInscripcion) throws InstanceNotFoundException;

    // Autor : Carlos
    // FIND alternativo para email y Carrera
    public Inscripcion findAlt(Connection connection, String email, Carrera carrera) throws InstanceNotFoundException;

    }
