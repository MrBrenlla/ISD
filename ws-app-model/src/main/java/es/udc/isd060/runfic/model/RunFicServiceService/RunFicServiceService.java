package es.udc.isd060.runfic.model.RunFicServiceService;

import es.udc.isd060.runfic.model.carrera.Carrera;
import es.udc.isd060.runfic.model.inscripcion.Inscripcion;

import java.time.LocalDateTime;
import java.util.List;

// TODO PREGUNTAR A PROFESOR
// En Realidad  CREO  QUE Es RunFicService ( Es EL problema de copiar las cosas)
// Solo ha que hacer REFACTOR > RENAME
// VER APUNTES TEORÍA TEMA 6
public interface RunFicServiceService {

    //**************************************************************************************************
    //****************************************** Brais *************************************************
    //**************************************************************************************************

    Inscripcion addInscripcion ( String email , String numTarjeta , Carrera carrera );

    List<Inscripcion > findInscripcion ( String email );

    //**************************************************************************************************
    //****************************************** Yago *************************************************
    //**************************************************************************************************

    public Carrera addCarrera ( Carrera carrera  );

    public List<Carrera> findCarrera (LocalDateTime fechaCelebracion , String nombreCiudad );

    //**************************************************************************************************
    //****************************************** Carlos *************************************************
    //**************************************************************************************************

    public Carrera findCarrera ( Integer idCarrera );

    public Inscripcion recogerDorsal ( Integer codReserva , String numTarjeta );


}
