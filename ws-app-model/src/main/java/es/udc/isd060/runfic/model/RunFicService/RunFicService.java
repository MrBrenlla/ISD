package es.udc.isd060.runfic.model.RunFicService;

import es.udc.isd060.runfic.model.RunFicService.exceptions.DorsalHaSidoRecogidoException;
import es.udc.isd060.runfic.model.RunFicService.exceptions.NumTarjetaIncorrectoException;
import es.udc.isd060.runfic.model.carrera.*;
import es.udc.isd060.runfic.model.inscripcion.Inscripcion;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import java.util.List;

import java.time.LocalDateTime;

public interface RunFicService {

    //**************************************************************************************************
    //****************************************** Brais *************************************************
    //**************************************************************************************************

    public Inscripcion addInscripcion(String email,String tarjeta, long carrera) throws InputValidationException;

    public List<Inscripcion> findInscripcion(String email);

    //**************************************************************************************************
    //****************************************** Yago *************************************************
    //**************************************************************************************************
    public Carrera addCarrera(Carrera carrera) throws InputValidationException;

    public List<Carrera> findCarrera (LocalDateTime fechaCelebracion);

    public List<Carrera> findCarrera (LocalDateTime fechaCelebracion, String ciudad);

    public void removeCarrera(Long idCarrera) throws InstanceNotFoundException;

    //**************************************************************************************************
    //****************************************** Carlos *************************************************
    //**************************************************************************************************

    public Carrera findCarrera ( Long idCarrera ) throws InstanceNotFoundException;

    public Inscripcion recogerDorsal (Long idInscripcion,String numTarjeta) throws InstanceNotFoundException, DorsalHaSidoRecogidoException, NumTarjetaIncorrectoException;

}
