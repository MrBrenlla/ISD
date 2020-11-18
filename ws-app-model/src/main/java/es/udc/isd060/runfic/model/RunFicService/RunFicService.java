package es.udc.isd060.runfic.model.RunFicService;

import es.udc.isd060.runfic.model.RunFicService.exceptions.CarreraYaCelebradaException;
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

    Inscripcion addInscripcion(String email, String tarjeta, long carrera) throws InputValidationException;

    List<Inscripcion> findInscripcion(String email);

    //**************************************************************************************************
    //****************************************** Yago *************************************************
    //**************************************************************************************************
    Carrera addCarrera(Carrera carrera) throws InputValidationException;

    List<Carrera> findCarrera(LocalDateTime fechaCelebracion);

    List<Carrera> findCarrera(LocalDateTime fechaCelebracion, String ciudad);

    void removeCarrera(Long idCarrera) throws InstanceNotFoundException;

    //**************************************************************************************************
    //****************************************** Carlos *************************************************
    //**************************************************************************************************

    Carrera findCarrera(Long idCarrera) throws InstanceNotFoundException;

    Inscripcion recogerDorsal(Long idInscripcion, String numTarjeta) throws InstanceNotFoundException, DorsalHaSidoRecogidoException, NumTarjetaIncorrectoException, CarreraYaCelebradaException, InputValidationException;

}
