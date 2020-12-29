package es.udc.ws.isd060.runfic.model.RunFicService;

import es.udc.ws.isd060.runfic.model.RunFicService.exceptions.*;
import es.udc.ws.isd060.runfic.model.carrera.*;
import es.udc.ws.isd060.runfic.model.inscripcion.Inscripcion;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import java.util.List;

import java.time.LocalDateTime;

public interface RunFicService {

    //**************************************************************************************************
    //****************************************** Brais *************************************************
    //**************************************************************************************************

    Inscripcion addInscripcion(String email, String tarjeta, long carrera) throws InputValidationException, CarreraInexistente,UsuarioInscrito,FueraDePlazo,SinPlazas;

    List<Inscripcion> findInscripcion(String email);

    //**************************************************************************************************
    //****************************************** Yago *************************************************
    //**************************************************************************************************
    Carrera addCarrera(Carrera carrera) throws InputValidationException;

    List<Carrera> findCarrera(LocalDateTime fechaCelebracion);

    List<Carrera> findCarrera(LocalDateTime fechaCelebracion, String ciudad);

    void removeInscripcion(Long idInscripcion)  throws InstanceNotFoundException;

    //**************************************************************************************************
    //****************************************** Carlos *************************************************
    //**************************************************************************************************

    Carrera findCarrera(Long idCarrera) throws InstanceNotFoundException;

    Inscripcion recogerDorsal(Long idInscripcion, String numTarjeta) throws InstanceNotFoundException, DorsalHaSidoRecogidoException, NumTarjetaIncorrectoException, CarreraYaCelebradaException, InputValidationException;
}
