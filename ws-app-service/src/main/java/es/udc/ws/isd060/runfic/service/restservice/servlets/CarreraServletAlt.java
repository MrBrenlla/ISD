package es.udc.ws.isd060.runfic.service.restservice.servlets;

import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.isd060.runfic.service.restservice.servlets.util.ServletUtils;
import es.udc.ws.util.validation.PropertyValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static es.udc.ws.isd060.runfic.service.restservice.servlets.util.ServletUtils.CARRERA_SUBPATH;

public class CarreraServletAlt extends CarreraServlet {

    private final static  Integer GET_TYPE_NULL=-1;
    private final static  Integer GET_TYPE_FIND_BY_ID=0;
    private final static  Integer GET_TYPE_FIND_BY_FECHACELEBRACION_NOMBRECIUDAD=1;

    // TODO TESTS
    // Función que determina si el get es el de find by id o find by fechaCelebracion y nombreCiudad o una invalid request
    private int determineGetType(String path){

        // Comprobamos que el path no esté vacío
        try {
            PropertyValidator.validateMandatoryString("path",path);
        } catch (InputValidationException e) {
            // GET http://XXX/ws-runfic-service/carreras/
            return GET_TYPE_NULL;
        }

        if (ServletUtils.isDesiredPath(path.substring(0,CARRERA_SUBPATH.length()),CARRERA_SUBPATH)) {
            // GET http://XXX/ws-runfic-service/carreras
            if (path.length()>CARRERA_SUBPATH.length()+1) {
                // GET http://XXX/ws-runfic-service/carreras[ALGO MAS]
                if (path.charAt(CARRERA_SUBPATH.length()+1)=='?') {
                    // GET http://XXX/ws-runfic-service/carreras?
                    if (path.length()>CARRERA_SUBPATH.length()+2) {
                        // GET http://XXX/ws-runfic-service/carreras?[ALGO MAS]
                        // CF : public List<Carrera> findCarrera (LocalDateTime fechaCelebracion , String nombreCiudad );
                        return GET_TYPE_FIND_BY_FECHACELEBRACION_NOMBRECIUDAD;
                    } else {
                        // GET http://XXX/ws-runfic-service/carreras?
                        return GET_TYPE_NULL;
                    }
                } else if (path.charAt(CARRERA_SUBPATH.length()+1)=='/'){
                    // GET http://XXX/ws-runfic-service/carreras/
                    if (path.length()>CARRERA_SUBPATH.length()+2) {
                        // GET http://XXX/ws-runfic-service/carreras/[ALGO MAS]
                        try {
                            Long.valueOf(path.substring(CARRERA_SUBPATH.length() + 2));
                        } catch (IndexOutOfBoundsException e){
                            // No debería pasar
                            throw new RuntimeException(e);
                        } catch ( NumberFormatException e){
                            // GET http://XXX/ws-runfic-service/carreras/[ALGO QUE NO ES UN LONG]
                            return  GET_TYPE_NULL;
                        }
                        //[else]
                        // GET http://XXX/ws-runfic-service/carreras/[UN LONG]
                        // CF : public Carrera findCarrera (Long idCarrera);
                        return GET_TYPE_FIND_BY_ID;
                    } else return GET_TYPE_NULL;
                }
                else {
                    return GET_TYPE_NULL;
                }
            } else{
                return GET_TYPE_NULL;
            }
        } else return GET_TYPE_NULL;
    }


    // CF : public List<Carrera> findCarrera (LocalDateTime fechaCelebracion , String nombreCiudad );
    // CF : public Carrera findCarrera (Long idCarrera);
    // TODO
    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        String path = ServletUtils.normalizePath(httpServletRequest.getPathInfo());
        int getType = determineGetType(path);

        String saleIdAsString = path.substring(1);
        Long saleId;
        try {
            saleId = Long.valueOf(saleIdAsString);
        } catch (NumberFormatException ex) {
            // GET http://XXX/ws-runfic-service/carreras[ALGO MAS]
            path.substring(0, CARRERA_SUBPATH.length()-1);
        }

/*
        if ( getType == GET_TYPE_FIND_BY_FECHACELEBRACION_NOMBRECIUDAD ){
            super.doGet(httpServletRequest,httpServletResponse);
        } else if ( getType == GET_TYPE_FIND_BY_ID ) {
            // TODO
            System.out.println("Not Implemented yet");
        } else if ( getType == GET_TYPE_INVALID_REQUEST ){
            ServletUtils.writeServiceResponse(httpServletResponse, HttpServletResponse.SC_BAD_REQUEST,
                    JsonToExceptionConversor.toInputValidationException(
                            new InputValidationException("Invalid Request: " + "invalid path " + path)),
                    null);
        }*/
    }


}
