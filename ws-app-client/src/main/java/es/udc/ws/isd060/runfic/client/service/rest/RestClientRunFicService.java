package es.udc.ws.isd060.runfic.client.service.rest;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.udc.ws.isd060.runfic.client.service.ClientRunFicService;
import es.udc.ws.isd060.runfic.client.service.dto.ClientCarreraDto;
import es.udc.ws.isd060.runfic.client.service.dto.ClientInscripcionDto;
import es.udc.ws.isd060.runfic.client.service.dto.ClientRecogerdorsalDto;
import es.udc.ws.isd060.runfic.client.service.rest.json.JsonToClientCarreraDtoConversor;
import es.udc.ws.isd060.runfic.client.service.rest.json.JsonToClientExceptionConversor;
import es.udc.ws.isd060.runfic.client.service.rest.json.JsonToClientInscripcionDtoConversor;
import es.udc.ws.isd060.runfic.client.service.rest.json.JsonToClientRecogerDorsalDtoConversor;
import es.udc.ws.isd060.runfic.model.RunFicService.exceptions.CarreraYaCelebradaException;
import es.udc.ws.isd060.runfic.model.RunFicService.exceptions.DorsalHaSidoRecogidoException;
import es.udc.ws.isd060.runfic.model.RunFicService.exceptions.NumTarjetaIncorrectoException;
import es.udc.ws.util.configuration.ConfigurationParametersManager;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.json.ObjectMapperFactory;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.List;

public class RestClientRunFicService implements ClientRunFicService {
    private final static String ENDPOINT_ADDRESS_PARAMETER = "RestClientRunFicService.endpointAddress";
    private String endpointAddress;


    public final  String  CARRERA_SUBPATH = "Carrera";
    public final  String  INSCRIPCION_SUBPATH = "Inscripcion";
    public final  String  RECOGERDORSAL_SUBPATH = "Dorsal";

    // Nota LOS Métodos que se comentan por CT ( Called To) son los que envían la petición a la URL indicacda

    //**************************************************************************************************
    //****************************************** Yago *************************************************
    //**************************************************************************************************

    // CT : POST http://XXX/ws-runfic-service/Carrera
    @Override
    public Long addCarrera(ClientCarreraDto carrera) throws InputValidationException {
        try {
            HttpResponse response = Request.Post(getEndpointAddress() + "Carrera"). //Add 'Carrera' for call addCarrera and findCarrera methods
                    bodyStream(toInputStream(carrera), ContentType.create("application/json")).
                    execute().returnResponse();

            validateStatusCode(HttpStatus.SC_CREATED, response);

            return JsonToClientCarreraDtoConversor.toClientCarreraDto(response.getEntity().getContent()).getIdCarrera();

        } catch (InputValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    // CT : GET http://XXX/ws-runfic-service/Carrera?fechaCelebracion=[fechaCelebracion]&ciudadCelebracion[ciudad]
    @Override
    public List<ClientCarreraDto> findCarrera(LocalDateTime fechaCelebracion, String ciudad) {
        try {
            HttpResponse response = Request.Get(getEndpointAddress() + "Carrera?fechaCelebracion="
                    + URLEncoder.encode(fechaCelebracion.toString(), "UTF-8") + "&ciudadCelebracion="
                    +URLEncoder.encode(ciudad, "UTF-8")).
                    execute().returnResponse();

            validateStatusCode(HttpStatus.SC_OK, response);

            return JsonToClientCarreraDtoConversor.toClientCarreraDtos(response.getEntity()
                    .getContent());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //**************************************************************************************************
    //****************************************** Brais *************************************************
    //**************************************************************************************************

    // CT : POST http://XXX/ws-runfic-service/Inscripcion
    @Override
    public Long addInscripcion(ClientInscripcionDto inscripcion) throws InputValidationException {
        try {
            HttpResponse response = Request.Post(getEndpointAddress() + "Inscripcion").
                    bodyStream(toInputStream(inscripcion), ContentType.create("application/json")).
                    execute().returnResponse();

            validateStatusCode(HttpStatus.SC_CREATED, response);

            return JsonToClientInscripcionDtoConversor.toClientInscripcionDto(response.getEntity().getContent()).getIdCarrera();

        } catch (InputValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    // CT : GET http://XXX/ws-runfic-service/Inscripcion?email=[email]
    @Override
    public List<ClientInscripcionDto> findIscripcion(String email) {
        try {
            HttpResponse response = Request.Get(getEndpointAddress() + "Inscripcion?Email="
                    +URLEncoder.encode(email, "UTF-8")).
                    execute().returnResponse();
            validateStatusCode(HttpStatus.SC_OK, response);

            return JsonToClientInscripcionDtoConversor.toClientInscripcionDtos(response.getEntity()
                    .getContent());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //**************************************************************************************************
    //****************************************** Carlos *************************************************
    //**************************************************************************************************

    // CT : GET http://XXX/ws-runfic-service/Carrera/[UN LONG]
    @Override
    public ClientCarreraDto findCarrera(Long idCarrera) throws InstanceNotFoundException, InputValidationException {
        try {
            HttpResponse httpResponse = Request.Get(getEndpointAddress() + "Carrera/" + idCarrera)
                    .execute().returnResponse();

            validateStatusCode(HttpStatus.SC_OK, httpResponse);

            ClientCarreraDto clientCarreraDto = JsonToClientCarreraDtoConversor
                    .toClientCarreraDto(httpResponse.getEntity().getContent());

            return JsonToClientCarreraDtoConversor.toClientCarreraDto(httpResponse.getEntity()
                    .getContent());

        } catch (InstanceNotFoundException | InputValidationException e) {
            throw e;
        } catch ( Exception e) {
            throw new RuntimeException(e);
        }
    }

    // CT : POST http://XXX/ws-runfic-service/Inscripcion/Dorsal
    @Override
    public ClientInscripcionDto recogerDorsal(Long idInscripcion, String numTarjeta) throws InstanceNotFoundException,
            InputValidationException, CarreraYaCelebradaException, DorsalHaSidoRecogidoException {
        try {
            ClientRecogerdorsalDto clientRecogerdorsalDto = new ClientRecogerdorsalDto(idInscripcion,numTarjeta);
            HttpResponse httpResponse = Request.Post(getEndpointAddress() + "Inscripcion/Dorsal" )
                    . bodyStream(toInputStream(clientRecogerdorsalDto), ContentType.create("application/json"))
                    .execute().returnResponse();

            validateStatusCode(HttpStatus.SC_OK, httpResponse);

            ClientInscripcionDto clientInscripcionDto = JsonToClientInscripcionDtoConversor
                    .toClientInscripcionDto(httpResponse.getEntity().getContent());

            return JsonToClientInscripcionDtoConversor.toClientInscripcionDto(httpResponse.getEntity()
                    .getContent());

        } catch ( DorsalHaSidoRecogidoException | CarreraYaCelebradaException  e) {
            throw e;
        } catch(NumTarjetaIncorrectoException e){
            throw new InstanceNotFoundException(String.class,"NumTarjeta");
        } catch ( InstanceNotFoundException | InputValidationException e){
            throw e;
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    //**************************************************************************************************

    private synchronized String getEndpointAddress() {
        if (endpointAddress == null) {
            endpointAddress = ConfigurationParametersManager
                    .getParameter(ENDPOINT_ADDRESS_PARAMETER);
        }
        return endpointAddress;
    }

    private InputStream toInputStream(ClientCarreraDto carrera) {

        try {

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            objectMapper.writer(new DefaultPrettyPrinter()).writeValue(outputStream,
                    JsonToClientCarreraDtoConversor.toObjectNode(carrera));

            return new ByteArrayInputStream(outputStream.toByteArray());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private InputStream toInputStream(ClientInscripcionDto i) {

        try {

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            objectMapper.writer(new DefaultPrettyPrinter()).writeValue(outputStream,
                    JsonToClientInscripcionDtoConversor.toObjectNode(i));

            return new ByteArrayInputStream(outputStream.toByteArray());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private InputStream toInputStream(ClientRecogerdorsalDto clientRecogerdorsalDto) {
        try {

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            objectMapper.writer(new DefaultPrettyPrinter()).writeValue(outputStream,
                    JsonToClientRecogerDorsalDtoConversor.toObjectNode(clientRecogerdorsalDto));

            return new ByteArrayInputStream(outputStream.toByteArray());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void validateStatusCode(int successCode, HttpResponse response) throws Exception {

        try {

            int statusCode = response.getStatusLine().getStatusCode();

            /* Success? */
            if (statusCode == successCode) {
                return;
            }

            /* Handler error. */
            switch (statusCode) {

                case HttpStatus.SC_NOT_FOUND:
                    throw JsonToClientExceptionConversor.fromNotFoundErrorCode(
                            response.getEntity().getContent());

                case HttpStatus.SC_BAD_REQUEST:
                    throw JsonToClientExceptionConversor.fromBadRequestErrorCode(
                            response.getEntity().getContent());

                case HttpStatus.SC_GONE:
                    throw JsonToClientExceptionConversor.fromGoneErrorCode(
                            response.getEntity().getContent());

                default:
                    throw new RuntimeException("HTTP error; status code = "
                            + statusCode);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
