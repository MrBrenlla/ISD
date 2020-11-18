package es.udc.isd060.runfic.model.inscripcion;


import es.udc.ws.util.exceptions.InstanceNotFoundException;
import java.sql.Connection;
import java.util.List;

public interface SqlInscripcionDao {

    // Autor : Brais
     public Inscripcion create(Connection connection, Inscripcion inscripcion);// fullCapacityException , inscriptionsAlreadyClosed

    // Autor : Carlos
     public Inscripcion find(Connection connection, Long idInscripcion) throws InstanceNotFoundException;

    // Autor : Brais
     public List<Inscripcion> find (Connection connection , String emailUsuario );

    // Autor : Brais
     public List<Inscripcion> find (Connection connection , String emailUsuario, Long IdCarrera ) throws InstanceNotFoundException;

    // Autor : Carlos
     public void update( Connection connection , Inscripcion inscripcion) throws InstanceNotFoundException;

    // Autor : Yago
    public void remove (Connection connection , Long idInscripcion) throws InstanceNotFoundException;

}
