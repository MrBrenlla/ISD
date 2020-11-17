package es.udc.isd060.runfic.model.RunFicService;

import es.udc.isd060.runfic.model.RunFicService.exceptions.DorsalHaSidoRecogidoException;
import es.udc.isd060.runfic.model.RunFicService.exceptions.NumTarjetaIncorrectoException;
import es.udc.isd060.runfic.model.carrera.*;
import es.udc.isd060.runfic.model.inscripcion.Inscripcion;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.time.LocalDateTime;

public interface RunFicService {

    //**************************************************************************************************
    //****************************************** Brais *************************************************
    //**************************************************************************************************

    //**************************************************************************************************
    //****************************************** Yago *************************************************
    //**************************************************************************************************
    public Carrera addCarrera(Carrera carrera);

    public <List>Carrera findCarrera (LocalDateTime fechaCelebracion);

    public <List>Carrera findCarrera (LocalDateTime fechaCelebracion, String ciudad);

    //**************************************************************************************************
    //****************************************** Carlos *************************************************
    //**************************************************************************************************

    public Carrera findCarrera ( Long idCarrera ) throws InstanceNotFoundException;

    public Inscripcion recogerDorsal (Long idInscripcion,String numTarjeta) throws InstanceNotFoundException, DorsalHaSidoRecogidoException, NumTarjetaIncorrectoException;

}
