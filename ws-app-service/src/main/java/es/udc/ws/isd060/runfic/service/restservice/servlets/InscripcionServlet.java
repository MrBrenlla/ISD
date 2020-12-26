package es.udc.ws.isd060.runfic.service.restservice.servlets;

import es.udc.ws.isd060.runfic.model.RunFicService.RunFicService;
import es.udc.ws.isd060.runfic.model.RunFicService.RunFicServiceFactory;

import es.udc.ws.isd060.runfic.model.RunFicService.exceptions.*;
import es.udc.ws.isd060.runfic.model.inscripcion.Inscripcion;
import es.udc.ws.isd060.runfic.service.restservice.dto.InscripcionToRestInscripcionConversor;
import es.udc.ws.isd060.runfic.service.restservice.dto.RestInscripcionDto;
import es.udc.ws.isd060.runfic.service.restservice.dto.RestRecogerdorsalDto;
import es.udc.ws.isd060.runfic.service.restservice.json.JsonToExceptionConversor;
import es.udc.ws.isd060.runfic.service.restservice.json.JsonToRestInscripcionDtoConversor;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.json.exceptions.ParsingException;

// Es una clase que extiende del ServletUtils original por lo que tiene todos sus métodos aparte de algunos nuestros
import es.udc.ws.isd060.runfic.service.restservice.servlets.util.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InscripcionServlet extends HttpServlet {

    // Nota LOS Métodos que se comentan por CF ( Called For) son llamados por la funcion correspondiente de la capa servicios
    // ( los que estan implementados son llamados por los comentados)
    // VER TEMA 6 ( DIAP. 45 )
    // un doXXX puede tener más de un CF
    // Aclaracion en \docs\DiagramaServlets.dia

    public static final String DEFAULT_INSCRIPCION_DEBUG_FILE = "C:\\software\\ws-javaexamples-3.4.0\\debug\\debugSales.txt";

    //**************************************************************************************************
    //****************************************** Brais *************************************************
    //**************************************************************************************************

    // CF : List<Inscripcion > findInscripcion (String email );
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String path = ServletUtils.normalizePath(req.getPathInfo());
        if (path == null || path.length() == 0) {
            String email = req.getParameter("email");
            List<Inscripcion> inscripcions = RunFicServiceFactory.getService().findInscripcion(email);
            List<RestInscripcionDto> movieDtos = InscripcionToRestInscripcionConversor.toRestInscripcionDto(inscripcions);
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_OK,
                    JsonToRestInscripcionDtoConversor.toArrayNode(movieDtos), null);
        } else {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    JsonToExceptionConversor.toInputValidationException(
                            new InputValidationException("Invalid Request: " + "invalid path " + path)),
                    null);
        }

    }


    //**************************************************************************************************
    //****************************************** Brais y Carlos ****************************************
    //**************************************************************************************************



    // CF : Inscripcion addInscripcion (String email , String numTarjeta , Carrera carrera );
    // CF : public Inscripcion recogerDorsal ( Integer codReserva , String numTarjeta );
    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        String path = ServletUtils.normalizePath(httpServletRequest.getRequestURI());
        int subpathType = ServletUtils.determineSubpathTypePostInscripcion(path);

        if ( subpathType == ServletUtils.POST_SUBPATH_TYPE_ADDINSCRIPCION ){
            // CF : Inscripcion addInscripcion (String email , String numTarjeta , Carrera carrera );
            doPostAddInscripcion(httpServletRequest,httpServletResponse);
        } else if ( subpathType == ServletUtils.POST_SUBPATH_TYPE_RECOGERDORSAL) {
            // CF : public Inscripcion recogerDorsal ( Integer codReserva , String numTarjeta );
            System.out.println("HOLA RECOGERDORSAL");
            doPostRecogerDorsal(httpServletRequest,httpServletResponse);
        } else if ( subpathType == ServletUtils.POST_SUBPATH_TYPE_NULL ) {
            // BAD REQUEST
            ServletUtils.writeServiceResponse(httpServletResponse, HttpServletResponse.SC_BAD_REQUEST,
                    JsonToExceptionConversor.toInputValidationException(
                            new InputValidationException("Invalid Request: " + "invalid path " + path)),
                    null);
            return;
        }

    }

    //**************************************************************************************************
    //****************************************** Brais *************************************************
    //**************************************************************************************************


    // TU CODIGO TAL Y COMO LO TENÍAS PERO COMENTADO

    // CF : Inscripcion addInscripcion (String email , String numTarjeta , Carrera carrera );
    protected void doPostAddInscripcion(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String path = ServletUtils.normalizePath(req.getPathInfo());
        if (path == null | path.length() <= 0) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    JsonToExceptionConversor.toInputValidationException(
                            new InputValidationException("Invalid Request BRAIS" + "invalid path " + path)),
                    null);
            return;
        }

        RestInscripcionDto i;
        try {
            i = JsonToRestInscripcionDtoConversor.toServiceInscripcionDto(req.getInputStream());
        } catch (ParsingException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST, JsonToExceptionConversor
                    .toInputValidationException(new InputValidationException(ex.getMessage())), null);
            return;
        }

        if(i.getTarjeta()==null||i.getEmail()==null||i.getIdCarrera()==null){
           ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST, JsonToExceptionConversor
                    .toInputValidationException(new InputValidationException("Faltan parametros")), null);
            return;
        }
        Inscripcion inscripcion;
        try {
            inscripcion = RunFicServiceFactory.getService().addInscripcion(i.getEmail(),i.getTarjeta(),i.getIdCarrera());
        } catch (InputValidationException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    JsonToExceptionConversor.toInputValidationException(ex), null);
            return;
        } catch (FueraDePlazo ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    JsonToExceptionConversor.toFueraDePlazo(ex), null);
            return;
        } catch (SinPlazas ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    JsonToExceptionConversor.toSinPlazas(ex), null);
            return;
        } catch (UsuarioInscrito ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    JsonToExceptionConversor.toUsuarioInscrito(ex), null);
            return;
        } catch (CarreraInexistente ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    JsonToExceptionConversor.toCarreraInexistente(ex), null);
            return;
        }
        i = InscripcionToRestInscripcionConversor.toRestInscripcionDto(inscripcion);

        String movieURL = ServletUtils.normalizePath(req.getRequestURL().toString()) + "/" + i.getIdInscripcion();
        Map<String, String> headers = new HashMap<>(1);
        headers.put("Location", movieURL);

        ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_CREATED,
                JsonToRestInscripcionDtoConversor.toObjectNode(i), headers);
    }



    // AÑADIR FUNCIONES EXTRA SI SON NECESARIAS

    //**************************************************************************************************
    //****************************************** Carlos *************************************************
    //**************************************************************************************************



    // CF : public Inscripcion recogerDorsal ( Integer codReserva , String numTarjeta );
    private void doPostRecogerDorsal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws ServletException, IOException {
        // NOTA : recogerDorsal pasa los parámetros por el cuerpo
        //TODO ORDEN EXCEPCIONES
        RestRecogerdorsalDto restRecogerdorsalDto ;
        try {
            // Generamos el dto a partir del inputStream de la request
            restRecogerdorsalDto = new RestRecogerdorsalDto(httpServletRequest.getInputStream());
        } catch (ParsingException ex) {
            // BAD REQUEST
            ServletUtils.badRequestExceptionResponse(httpServletResponse,ex);
            return;
        }
        // Obtenemos Instancia de RunFicService
        RunFicService runFicService =RunFicServiceFactory.getService();
        // Guardamos en unas variables los datos de restRecogerDorsalDto
        Long idInscripcion = restRecogerdorsalDto.getCodRecogerDorsal();
        String numTarjeta = restRecogerdorsalDto.getNumTarjeta();
        // Resultado de recogerDorsal
        Inscripcion inscripcion = null;
        try {
            inscripcion = runFicService.recogerDorsal(idInscripcion,numTarjeta);
        } catch (InputValidationException ex) {
            // BAD REQUEST
            ServletUtils.badRequestExceptionResponse(httpServletResponse,ex);
            return;
        } catch (DorsalHaSidoRecogidoException e) {
            ServletUtils.writeCustomExceptionResponse(e,httpServletResponse,
                    HttpServletResponse.SC_FORBIDDEN,"DorsalHaSidoRecogidoException");
        } catch (InstanceNotFoundException e) {
           ServletUtils.writeCustomExceptionResponse(e,httpServletResponse,
                    HttpServletResponse.SC_NOT_FOUND,"InstanceNotFoundException");
        } catch (CarreraYaCelebradaException e) {
            ServletUtils.writeCustomExceptionResponse(e,httpServletResponse,
                    HttpServletResponse.SC_GONE,"CarreraYaCelebradaException");
        } catch (NumTarjetaIncorrectoException e) {
            ServletUtils.writeCustomExceptionResponse(e,httpServletResponse,
                    HttpServletResponse.SC_NOT_FOUND,"NumTarjetaIncorrectoException");
        } catch ( Exception e) {
            // No debería pasar
            throw new RuntimeException(e);
        }

        // SI OK
        writeRecogerDorsalOkResponse(inscripcion,httpServletRequest,httpServletResponse);

    }




    private  void writeRecogerDorsalOkResponse ( Inscripcion inscripcion , HttpServletRequest httpServletRequest ,
                                                 HttpServletResponse httpServletResponse ) {
        RestInscripcionDto restInscripcionDto = new RestInscripcionDto(inscripcion);
        String requestPath = ServletUtils.normalizePath(httpServletRequest.getRequestURL().toString());
        String inscripcionUrl = ServletUtils.normalizePath(httpServletRequest.getRequestURL().toString()) +
                "/" + inscripcion.getIdInscripcion().toString();

        Map<String, String> headers = new HashMap<>(1);
        headers.put("Location", inscripcionUrl);


        try {
            es.udc.ws.util.servlet.ServletUtils.writeServiceResponse(httpServletResponse, HttpServletResponse.SC_CREATED,
                    JsonToRestInscripcionDtoConversor.toObjectNode(restInscripcionDto), headers);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }




    //**************************************************************************************************
    //****************************************** Carlos ************************************************
    //**************************************************************************************************
    // CF : public Inscripcion recogerDorsal ( Integer codReserva , String numTarjeta );
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    //**************************************************************************************************
    //****************************************** Yago **************************************************
    //**************************************************************************************************
    //removeInscripcion(Inscripcion inscripcion);

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = ServletUtils.normalizePath(req.getPathInfo());
        if (path == null || path.length() == 0) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST, JsonToExceptionConversor
                            .toInputValidationException(new InputValidationException("Invalid Request: CARLOS 1" + "invalid inscripcion id")),
                    null);
            return;
        }
        String inscripcionIdAsString = path.substring(1);
        Long inscripcionId;
        try {
            inscripcionId = Long.valueOf(inscripcionIdAsString);
        } catch (NumberFormatException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    JsonToExceptionConversor.toInputValidationException(new InputValidationException(
                            "Invalid Request: CARLOS 2" + "invalid inscripcion id '" + inscripcionIdAsString + "'")),
                    null);

            return;
        }
        try {
            RunFicServiceFactory.getService().removeInscripcion(inscripcionId);
        } catch (InstanceNotFoundException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NOT_FOUND,
                    JsonToExceptionConversor.toInstanceNotFoundException(ex), null);
            return;
        }
        ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NO_CONTENT, null, null);
    }

}
