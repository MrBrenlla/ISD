package es.udc.ws.isd060.runfic.service.restservice.servlets;

import es.udc.isd060.runfic.model.RunFicService.RunFicServiceFactory;
import es.udc.isd060.runfic.model.RunFicService.exceptions.CarreraInexistente;
import es.udc.isd060.runfic.model.RunFicService.exceptions.FueraDePlazo;
import es.udc.isd060.runfic.model.RunFicService.exceptions.SinPlazas;
import es.udc.isd060.runfic.model.RunFicService.exceptions.UsuarioInscrito;
import es.udc.isd060.runfic.model.carrera.Carrera;
import es.udc.isd060.runfic.model.inscripcion.Inscripcion;
import es.udc.ws.isd060.runfic.service.restservice.dto.InscripcionToRestInscripcionConversor;
import es.udc.ws.isd060.runfic.service.restservice.dto.RestInscripcionDto;
import es.udc.ws.isd060.runfic.service.restservice.json.JsonToExceptionConversor;
import es.udc.ws.isd060.runfic.service.restservice.json.JsonToRestInscripcionDtoConversor;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.json.exceptions.ParsingException;
import es.udc.ws.util.servlet.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InscripcionServlet extends HttpServlet {
    // TODO all

    // Nota LOS Métodos que se comentan por CF ( Called For) son llamados por la funcion correspondiente de la capa servicios
    // ( los que estan implementados son llamados por los comentados)
    // VER TEMA 6 ( DIAP. 45 )
    // un doXXX puede tener más de un CF
    // Aclaracion en \docs\DiagramaServlets.dia

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
    //****************************************** Brais *************************************************
    //**************************************************************************************************


    // CF : Inscripcion addInscripcion (String email , String numTarjeta , Carrera carrera );
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = ServletUtils.normalizePath(req.getPathInfo());
        if (path != null && path.length() > 0) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    JsonToExceptionConversor.toInputValidationException(
                            new InputValidationException("Invalid Request: " + "invalid path " + path)),
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
        Inscripcion inscripcion
        try {
            inscripcion = RunFicServiceFactory.getService().addInscripcion(i.getEmail(),i.getTarjeta(),i.getIdCarrera(),);
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

        String movieURL = ServletUtils.normalizePath(req.getRequestURL().toString()) + "/" + i.getMovieId();
        Map<String, String> headers = new HashMap<>(1);
        headers.put("Location", movieURL);

        ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_CREATED,
                JsonToRestInscripcionDtoConversor.toObjectNode(i), headers);
    }




    //**************************************************************************************************
    //****************************************** Carlos ************************************************
    //**************************************************************************************************
    // CF : public Inscripcion recogerDorsal ( Integer codReserva , String numTarjeta );
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

}
