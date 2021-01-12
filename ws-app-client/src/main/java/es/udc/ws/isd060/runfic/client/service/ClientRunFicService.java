package es.udc.ws.isd060.runfic.client.service;

import es.udc.ws.isd060.runfic.client.service.dto.ClientCarreraDto;
import es.udc.ws.isd060.runfic.client.service.dto.ClientInscripcionDto;
import es.udc.ws.isd060.runfic.model.RunFicService.exceptions.CarreraYaCelebradaException;
import es.udc.ws.isd060.runfic.model.RunFicService.exceptions.DorsalHaSidoRecogidoException;
import es.udc.ws.isd060.runfic.model.RunFicService.exceptions.NumTarjetaIncorrectoException;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

public interface ClientRunFicService {
    //**************************************************************************************************
    //****************************************** Brais *************************************************
    //**************************************************************************************************

    // NOTA : Los Comentarios están suponiendo una implementación REST en HTML usando JSON (La que se nos pide para la it-2)
    // En el caso de existir nuevas implementaciones de la interfaz estás también estarán indicadas en los comentarios

    // CT : POST http://XXX/ws-runfic-service/Inscripcion
    public Long addInscripcion(ClientInscripcionDto inscripcion) throws InputValidationException;

    // CT : GET http://XXX/ws-runfic-service/Inscripcion?email=[email]
    public List<ClientInscripcionDto> findInscripcion(String email);

    //**************************************************************************************************
    //****************************************** Yago *************************************************
    //**************************************************************************************************

    // CT : POST http://XXX/ws-runfic-service/Carrera
    public Long addCarrera(ClientCarreraDto carrera) throws InputValidationException;

    // CT : GET http://XXX/ws-runfic-service/Carrera?fechaCelebracion=[fechaCelebracion]&ciudadCelebracion[ciudad]
    public List<ClientCarreraDto> findCarrera(LocalDateTime fechaCelebracion, String ciudad);


    //**************************************************************************************************
    //****************************************** Carlos *************************************************
    //**************************************************************************************************

    // CT : GET http://XXX/ws-runfic-service/Carrera/[UN LONG]
    public ClientCarreraDto findCarrera(Long idCarrera) throws InstanceNotFoundException, InputValidationException;

    // CT : POST http://XXX/ws-runfic-service/Inscripcion/Dorsal
    public ClientInscripcionDto recogerDorsal(Long idInscripcion, String numTarjeta) throws InstanceNotFoundException,
            InputValidationException, CarreraYaCelebradaException, DorsalHaSidoRecogidoException, NumTarjetaIncorrectoException;


}
