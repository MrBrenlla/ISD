package es.udc.ws.isd060.runfic.client.service.thrift;

import es.udc.ws.isd060.runfic.client.service.ClientRunFicService;
import es.udc.ws.isd060.runfic.client.service.dto.ClientCarreraDto;
import es.udc.ws.isd060.runfic.client.service.dto.ClientInscripcionDto;
import es.udc.ws.isd060.runfic.model.RunFicService.exceptions.CarreraYaCelebradaException;
import es.udc.ws.isd060.runfic.model.RunFicService.exceptions.DorsalHaSidoRecogidoException;
import es.udc.ws.isd060.runfic.model.RunFicService.exceptions.NumTarjetaIncorrectoException;
import es.udc.ws.isd060.runfic.thrift.*;
import es.udc.ws.util.configuration.ConfigurationParametersManager;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.THttpClient;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import java.time.LocalDateTime;
import java.util.List;

public class ThriftClientRunFicService implements ClientRunFicService {

    private final static String ENDPOINT_ADDRESS_PARAMETER =
            "ThriftClientRunFicService.endpointAddress";

    private final static String endpointAddress =
            ConfigurationParametersManager.getParameter(ENDPOINT_ADDRESS_PARAMETER);

    public Long addInscripcion(ClientInscripcionDto inscripcion) throws InputValidationException {

        ThriftRunFicService.Client client = getClient();
        TTransport transport = client.getInputProtocol().getTransport();

        try  {

            transport.open();

            return client.addInscripcion(ClientInscripcionDtoToThriftInscripcionDtoConversor.toThriftInscripcionDto(inscripcion));

        } catch (ThriftInputValidationException e) {
            throw new InputValidationException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            transport.close();
        }

    }

    @Override
    public List<ClientInscripcionDto> findInscripcion(String email) {

        ThriftRunFicService.Client client = getClient();
        TTransport transport = client.getInputProtocol().getTransport();

        try  {

            transport.open();

            return ClientInscripcionDtoToThriftInscripcionDtoConversor.toClientInscripcionDto(client.findInscripcions(email));

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            transport.close();
        }

    }

    @Override
    public Long addCarrera(ClientCarreraDto carrera) throws InputValidationException {
        return null;
    }

    @Override
    public List<ClientCarreraDto> findCarrera(LocalDateTime fechaCelebracion, String ciudad) {
        return null;
    }

    @Override
    public ClientCarreraDto findCarrera(Long idCarrera) throws InstanceNotFoundException, InputValidationException {
        return null;
    }

    @Override
    public ClientInscripcionDto recogerDorsal(Long idInscripcion, String numTarjeta) throws InstanceNotFoundException, InputValidationException, CarreraYaCelebradaException, DorsalHaSidoRecogidoException, NumTarjetaIncorrectoException {
        return null;
    }


    private ThriftRunFicService.Client getClient() {

        try {

            TTransport transport = new THttpClient(endpointAddress);
            TProtocol protocol = new TBinaryProtocol(transport);

            return new ThriftRunFicService.Client(protocol);

        } catch (TTransportException e) {
            throw new RuntimeException(e);
        }

    }

}
