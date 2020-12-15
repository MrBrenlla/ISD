package es.udc.isd060.runfic.model.RunFicServiceService;

import es.udc.isd060.runfic.model.carrera.Carrera;
import es.udc.isd060.runfic.model.inscripcion.Inscripcion;

import java.time.LocalDateTime;
import java.util.List;

public interface RunFicServiceService {

    public Carrera addCarrera ( Carrera carrera  );
    public Carrera findCarrera ( Integer idCarrera );
    public Inscripcion addInscripcion ( String email , String numTarjeta , Carrera carrera );
    public List<Inscripcion > findInscripcion ( String email );
    public Inscripcion recogerDorsal ( Integer codReserva , String numTarjeta );
    public List<Carrera> findCarrera (LocalDateTime fechaCelebracion , String nombreCiudad );

}
