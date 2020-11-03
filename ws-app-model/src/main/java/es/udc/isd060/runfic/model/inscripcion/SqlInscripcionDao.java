package es.udc.isd060.runfic.model.inscripcion;

import com.sun.jdi.connect.spi.Connection;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.util.List;

public interface SqlInscripcionDao {

    // Autor : Brais
     Inscripcion create(Connection connection, Inscripcion inscripcion);// fullCapacityException , inscriptionsAlreadyClosed

    // Autor : Carlos
     Inscripcion find(Connection connection, Long idInscripcion);

    // Autor : Brais
     List<Inscripcion> find (Connection connection , String emailUsuario ) ;

    // Autor : Brais
     List<Inscripcion> find (Connection connection , String emailUsuario, Long IdCarrera ) ;

    // Autor : Carlos
      void update( Connection connection , Inscripcion inscripcion);

    // Autor : Yago
     void remove ( Connection connection , Inscripcion inscripcion);

}
