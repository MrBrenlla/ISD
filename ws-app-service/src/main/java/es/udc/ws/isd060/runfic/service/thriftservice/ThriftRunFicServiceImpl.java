package es.udc.ws.isd060.runfic.service.thriftservice;

import es.udc.ws.isd060.runfic.model.RunFicService.RunFicService;
import es.udc.ws.isd060.runfic.model.RunFicService.RunFicServiceFactory;
import es.udc.ws.isd060.runfic.model.RunFicService.exceptions.CarreraInexistente;
import es.udc.ws.isd060.runfic.model.RunFicService.exceptions.FueraDePlazo;
import es.udc.ws.isd060.runfic.model.RunFicService.exceptions.SinPlazas;
import es.udc.ws.isd060.runfic.model.RunFicService.exceptions.UsuarioInscrito;
import es.udc.ws.isd060.runfic.model.carrera.Carrera;
import es.udc.ws.isd060.runfic.model.inscripcion.Inscripcion;
import es.udc.ws.isd060.runfic.thrift.*;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.util.List;

public class ThriftRunFicServiceImpl implements ThriftRunFicService.Iface {

    @Override
    public long addInscripcion(ThriftInscripcionDto inscripcion) throws ThriftInputValidationException, ThriftFueraDePlazo, ThriftSinPlazas, ThriftUsuarioInscrito, ThriftCarreraInexistente {

        Inscripcion i = InscripcionToThriftInscripcionDtoConversor.toInscripcion(inscripcion);

        try {
            return RunFicServiceFactory.getService().addInscripcion(i.getEmail(),i.getTarjeta(),i.getIdCarrera()).getIdInscripcion();
        } catch (InputValidationException e) {
            throw new ThriftInputValidationException(e.getMessage());
        } catch (FueraDePlazo e) {
            throw new ThriftFueraDePlazo(e.getMessage());
        } catch (SinPlazas e) {
            throw new ThriftSinPlazas(e.getMessage());
        } catch (UsuarioInscrito e) {
            throw new ThriftUsuarioInscrito(e.getMessage());
        } catch (CarreraInexistente e) {
            throw new ThriftCarreraInexistente(e.getMessage());
        }
    }

    @Override
    public List<ThriftInscripcionDto> findInscripcions(String email) throws ThriftInputValidationException {

        List<Inscripcion> inscripcions = null;
        try {
            inscripcions = RunFicServiceFactory.getService().findInscripcion(email);
        } catch (InputValidationException e) {
            throw new ThriftInputValidationException(e.getMessage());
        }

        return InscripcionToThriftInscripcionDtoConversor.toThriftInscripcionDtos(inscripcions);
    }
}
