package es.udc.ws.isd060.runfic.service.restservice.servlets;

import es.udc.ws.isd060.runfic.model.RunFicService.RunFicServiceFactory;

import es.udc.ws.isd060.runfic.model.carrera.Carrera;
import es.udc.ws.isd060.runfic.service.restservice.dto.CarreraToRestCarreraDtoConversor;
import es.udc.ws.isd060.runfic.service.restservice.dto.RestCarreraDto;
import es.udc.ws.isd060.runfic.service.restservice.json.JsonToExceptionConversor;
import es.udc.ws.isd060.runfic.service.restservice.json.JsonToRestCarreraDtoConversor;
import es.udc.ws.isd060.runfic.service.restservice.servlets.util.Debug;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.json.exceptions.ParsingException;
import es.udc.ws.isd060.runfic.service.restservice.servlets.util.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarreraServlet extends HttpServlet {
    // TODO all

    // Nota LOS Métodos que se comentan por CF ( Called For) son llamados por la funcion correspondiente de la capa servicios
    // ( los que estan implementados son llamados por los comentados)
    // VER TEMA 6 ( DIAP. 45 )
    // un doXXX puede tener más de un CF
    // Aclaracion en \docs\DiagramaServlets.dia

    public static final String DEFAULT_CARRERA_DEBUG_FILE_PATH = "C:\\debug\\carrera";

    //**************************************************************************************************
    //****************************************** Yago *************************************************
    //**************************************************************************************************

    // CF : public Carrera addCarrera (Carrera carrera);
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = ServletUtils.normalizePath(req.getPathInfo());
        if (path != null && path.length() > 0) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    JsonToExceptionConversor.toInputValidationException(
                            new InputValidationException("Invalid Request: " + "invalid path " + path)),
                    null);
            return;
        }
        RestCarreraDto carreraDto;
        try {
            carreraDto = JsonToRestCarreraDtoConversor.toServiceCarreraDto(req.getInputStream());
        } catch (ParsingException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST, JsonToExceptionConversor
                    .toInputValidationException(new InputValidationException(ex.getMessage())), null);
            return;
        }
        Carrera carrera = CarreraToRestCarreraDtoConversor.toCarrera(carreraDto);
        try {
            carrera = RunFicServiceFactory.getService().addCarrera(carrera);
        } catch (InputValidationException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    JsonToExceptionConversor.toInputValidationException(ex), null);
            return;
        }
        carreraDto = CarreraToRestCarreraDtoConversor.toRestCarreraDto(carrera);

        String carreraURL = ServletUtils.normalizePath(req.getRequestURL().toString()) + "/" + carrera.getIdCarrera();
        Map<String, String> headers = new HashMap<>(1);
        headers.put("Location", carreraURL);

        ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_CREATED,
                JsonToRestCarreraDtoConversor.toObjectNode(carreraDto), headers);

    }

    //**************************************************************************************************
    // CF : public List<Carrera> findCarrera (LocalDateTime fechaCelebracion , String nombreCiudad );
    //**Se debe añadir -> // CF : public List<Carrera> findCarrera (Long idCarrera);
    //***********************************-COMÚN-*************************************************
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //*************************************************************************************************
        String path = ServletUtils.normalizePath(req.getPathInfo());
        //**************************************YAGO*******************************************************
        if (path == null || path.length() == 0) {
            String fechaCelebracion = req.getParameter("fechaCelebracion");
            String ciudad = req.getParameter("ciudadCelebracion");
            List<Carrera> carreras = RunFicServiceFactory.getService().findCarrera(LocalDateTime.parse(fechaCelebracion),ciudad);
            List<RestCarreraDto> carreraDtos = CarreraToRestCarreraDtoConversor.toRestCarreraDtos(carreras);
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_OK,
                    JsonToRestCarreraDtoConversor.toArrayNode(carreraDtos), null);
        }
        //**************************************************************************************************
        else {
            // http://XXX/ws-runfic-service/carreras[ALGO MAS]
            char charAt0;
            try {
                charAt0 = path.charAt(0);
            } catch (IndexOutOfBoundsException e){
                // No debería pasar
                throw new RuntimeException(e);
            }
            if (charAt0 == '/'){
                // http://XXX/ws-runfic-service/carreras/[ALGO MAS]
                Long idCarrera;
                try {
                     idCarrera = Long.valueOf(path.substring(1));
                } catch ( NumberFormatException e ) {
                    // http://XXX/ws-runfic-service/carreras/[ALGO QUE NO ES UN LONG]
                    // BAD REQUEST
                    ServletUtils.badRequestExceptionResponse(resp,e);
                    return;
                } catch ( IndexOutOfBoundsException e){
                    // No debería pasar
                    throw  new RuntimeException(e);
                }
                // http://XXX/ws-runfic-service/carreras/[UN LONG]
                // CF : public List<Carrera> findCarrera (Long idCarrera);
                doGetFindById(req,resp,idCarrera);
            } else {
                // http://XXX/ws-runfic-service/carreras[BASURA]
                // BAD REQUEST
                ServletUtils.badRequestExceptionResponse(resp,
                        new InputValidationException("Invalid Request: " + "invalid path " + path));
                return;
            }


            //If parámetros nulos -> Mostramos vacío el JSON
            /*
            String parameters = path.substring(1);
            if(parameters == null || parameters="")
                //Devolver JSON-VACÍO --- **********Implementación addicional - No Obligatoria **********
            */


            //Else -> Hacer Búsqueda por Id (si se requiere) - ***************************************CARLOS*******************************
            //CF : public List<Carrera> findCarrera (Long idCarrera);


            //O también se puede lanzar exception: (Cuando sea necesario)
            /*ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    JsonToExceptionConversor.toInputValidationException(
                            new InputValidationException("Invalid Request: " + "invalid path " + path)),
                    null);*/
        }
    }



    //**************************************************************************************************
    //****************************************** Carlos ************************************************
    //**************************************************************************************************

    // CF : public List<Carrera> findCarrera (Long idCarrera);
    private void doGetFindById(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse ,
                               Long idCarrera) throws IOException {
        Carrera carrera = null;
        try {
            carrera = RunFicServiceFactory.getService().findCarrera(idCarrera);
        } catch ( InstanceNotFoundException e){
            // Id no encontrado
            ServletUtils.writeCustomExceptionResponse(e,httpServletResponse,HttpServletResponse.SC_NOT_FOUND,
                    "InstanceNotFoundException");
        }
        //[else]
        // OK RESPONSE
        RestCarreraDto restCarreraDto = new RestCarreraDto(carrera);
        es.udc.ws.util.servlet.ServletUtils.writeServiceResponse(httpServletResponse, HttpServletResponse.SC_OK,
                JsonToRestCarreraDtoConversor.toObjectNode(restCarreraDto), null);

    }



}
