package es.udc.isd060.runfic.model.RunFicServiceService;

import es.udc.isd060.runfic.model.carrera.Carrera;
import es.udc.isd060.runfic.model.inscripcion.Inscripcion;

import java.time.LocalDateTime;
import java.util.List;

public class RunFicServiceServiceImpl implements  RunFicServiceService{
    // TODO Implementar métodos
    @Override
    public Carrera addCarrera(Carrera carrera) {
        return null;
    }

    @Override
    public Carrera findCarrera(Integer idCarrera) {
        return null;
    }

    @Override
    public Inscripcion addInscripcion(String email, String numTarjeta, Carrera carrera) {
        return null;
    }

    @Override
    public List<Inscripcion> findInscripcion(String email) {
        return null;
    }

    @Override
    public Inscripcion recogerDorsal(Integer codReserva, String numTarjeta) {
        return null;
    }

    @Override
    public List<Carrera> findCarrera(LocalDateTime fechaCelebracion, String nombreCiudad) {
        return null;
    }
}
