package es.udc.ws.isd060.runfic.client.service.rest;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.udc.ws.isd060.runfic.client.service.ClientRunFicService;
import es.udc.ws.isd060.runfic.client.service.dto.ClientCarreraDto;
import es.udc.ws.isd060.runfic.client.service.rest.json.JsonToClientCarreraDtoConversor;
import es.udc.ws.isd060.runfic.client.service.rest.json.JsonToClientExceptionConversor;
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
import java.time.LocalDateTime;
import java.util.List;

public class RestClientRunFicService implements ClientRunFicService {
    private final static String ENDPOINT_ADDRESS_PARAMETER = "RestClientRunFicService.endpointAddress";
    private String endpointAddress;

    //**************************************************************************************************
    //****************************************** Yago *************************************************
    //**************************************************************************************************
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

    @Override
    public void removeInscripcion(Long idInscripcion)  throws InstanceNotFoundException {

    }

    @Override
    public List<ClientCarreraDto> findCarrera(LocalDateTime fechaCelebracion, String ciudad) {

        return null;
    }

    //**************************************************************************************************
    //****************************************** Brais *************************************************
    //**************************************************************************************************

    //**************************************************************************************************
    //****************************************** Carlos *************************************************
    //**************************************************************************************************




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
