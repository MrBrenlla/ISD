package es.udc.ws.isd060.runfic.service.restservice.servlets;

import es.udc.ws.isd060.runfic.model.RunFicService.RunFicService;
import es.udc.ws.isd060.runfic.model.RunFicService.RunFicServiceFactory;
import es.udc.ws.isd060.runfic.model.RunFicService.exceptions.CarreraYaCelebradaException;
import es.udc.ws.isd060.runfic.model.RunFicService.exceptions.DorsalHaSidoRecogidoException;
import es.udc.ws.isd060.runfic.model.RunFicService.exceptions.NumTarjetaIncorrectoException;
import es.udc.ws.isd060.runfic.model.inscripcion.Inscripcion;
import es.udc.ws.isd060.runfic.service.restservice.dto.RestInscripcionDto;
import es.udc.ws.isd060.runfic.service.restservice.dto.RestRecogerdorsalDto;
import es.udc.ws.isd060.runfic.service.restservice.json.JsonToExceptionConversor;
import es.udc.ws.isd060.runfic.service.restservice.json.JsonToRestInscripcionDtoConversor;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.isd060.runfic.service.restservice.servlets.util.ServletUtils;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.json.exceptions.ParsingException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class InscripcionServletAlt extends InscripcionServlet {


    //*********************************************************************************************************
    //****************************************** Carlos********************************************************
    //*********************************************************************************************************



    // NOTA : recogerDorsal pasa los parámetros por es cuerpo

    // CF : Inscripcion addInscripcion (String email , String numTarjeta , Carrera carrera );
    // CF : public Inscripcion recogerDorsal ( Integer codReserva , String numTarjeta );
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        String path = ServletUtils.normalizePath(httpServletRequest.getPathInfo());
        int subpathType = ServletUtils.determineSubpathTypePostInscripcion(path);

        if ( subpathType == ServletUtils.POST_SUBPATH_TYPE_ADDINSCRIPCION ){
            // CF : Inscripcion addInscripcion (String email , String numTarjeta , Carrera carrera );
            //super.doPost(httpServletRequest,httpServletResponse);
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


    // CF : public Inscripcion recogerDorsal ( Integer codReserva , String numTarjeta );
    private void doPostRecogerDorsal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws ServletException, IOException {
        // CF : public Inscripcion recogerDorsal ( Integer codReserva , String numTarjeta );
        RestRecogerdorsalDto restRecogerdorsalDto ;
        try {
            // Generamos el dto a partir del inputStream de la request
            restRecogerdorsalDto = new RestRecogerdorsalDto(httpServletRequest.getInputStream());
        } catch (ParsingException ex) {
            es.udc.ws.util.servlet.ServletUtils.writeServiceResponse(httpServletResponse, HttpServletResponse.SC_BAD_REQUEST, JsonToExceptionConversor
                    .toInputValidationException(new InputValidationException(ex.getMessage())), null);
            return;
        }
        // Obtenemos Instancia de RunFicService
        RunFicService runFicService =RunFicServiceFactory.getService();
        // Guardamos en unas variables los datos de restRecogerDorsalDto
        Long idInscripcion = restRecogerdorsalDto.getIdInscripcion();
        String numTarjeta = restRecogerdorsalDto.getNumTarjeta();
        // Resultado de recogerDorsal
        Inscripcion inscripcion = null;
        try {
            inscripcion = runFicService.recogerDorsal(idInscripcion,numTarjeta);
        } catch (InputValidationException ex) {
            es.udc.ws.util.servlet.ServletUtils.writeServiceResponse(httpServletResponse, HttpServletResponse.SC_BAD_REQUEST,
                    JsonToExceptionConversor.toInputValidationException(ex), null);
            return;
        } catch (DorsalHaSidoRecogidoException e) {
            writeRecogerDorsalCustomExceptionResponse(e,httpServletResponse,
                    HttpServletResponse.SC_FORBIDDEN,"DorsalHaSidoRecogidoException");
        } catch (InstanceNotFoundException e) {
            writeRecogerDorsalCustomExceptionResponse(e,httpServletResponse,
                    HttpServletResponse.SC_NOT_FOUND,"InstanceNotFoundException");
        } catch (CarreraYaCelebradaException e) {
            writeRecogerDorsalCustomExceptionResponse(e,httpServletResponse,
                    HttpServletResponse.SC_GONE,"CarreraYaCelebradaException");
        } catch (NumTarjetaIncorrectoException e) {
            writeRecogerDorsalCustomExceptionResponse(e,httpServletResponse,
                    HttpServletResponse.SC_NOT_FOUND,"NumTarjetaIncorrectoException");
        } catch ( Exception e) {
            throw new RuntimeException(e);
        }

        // SI OK
        writeRecogerDorsalOkResponse(inscripcion,httpServletRequest,httpServletResponse);

    }

    private void writeRecogerDorsalCustomExceptionResponse(DorsalHaSidoRecogidoException e, HttpServletResponse httpServletResponse, int scForbidden) {
    }

    private void writeRecogerDorsalCustomExceptionResponse(Exception exception,HttpServletResponse httpServletResponse , Integer httpServletResponseType
                                                           , String exceptionTypeName) {
        try {
            es.udc.ws.util.servlet.ServletUtils.writeServiceResponse(httpServletResponse, httpServletResponseType,
                    JsonToExceptionConversor.toCustomException(exception,exceptionTypeName),null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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


}
