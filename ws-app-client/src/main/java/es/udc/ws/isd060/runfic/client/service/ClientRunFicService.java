package es.udc.ws.isd060.runfic.client.service;

import es.udc.ws.isd060.runfic.client.service.dto.ClientCarreraDto;
import es.udc.ws.isd060.runfic.client.service.dto.ClientInscripcionDto;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

public interface ClientRunFicService {
    //**************************************************************************************************
    //****************************************** Brais *************************************************
    //**************************************************************************************************
    public Long addInscripcion(ClientInscripcionDto inscripcion) throws InputValidationException;

    public List<ClientInscripcionDto> findIscripcion(String email);

    //**************************************************************************************************
    //****************************************** Yago *************************************************
    //**************************************************************************************************
    public Long addCarrera(ClientCarreraDto carrera) throws InputValidationException;

    public List<ClientCarreraDto> findCarrera(LocalDateTime fechaCelebracion, String ciudad);

    public void removeInscripcion(Long idInscripcion)  throws InstanceNotFoundException;

    //**************************************************************************************************
    //****************************************** Carlos *************************************************
    //**************************************************************************************************

}
