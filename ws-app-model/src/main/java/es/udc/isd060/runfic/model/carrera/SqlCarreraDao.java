package es.udc.isd060.runfic.model.carrera;

import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

public interface SqlCarreraDao {

    // Autor : Yago
     Carrera create ( Connection connection , Carrera carrera);

    // Autor : Carlos
     Carrera find ( Connection connection , Long idCarrera);

    // Autor : Yago
     List<Carrera> find (Connection connection , LocalDateTime fechaCelebracion , String ciudad );// Non lanza InvalidDateException, comprobase en modelo

    // Autor : Brais
     void update ( Connection connection , Carrera carrera );

    // Autor : Yago
     void remove ( Connection connection , Carrera carrera ) ;


}
