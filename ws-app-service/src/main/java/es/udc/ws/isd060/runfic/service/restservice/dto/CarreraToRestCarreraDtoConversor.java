package es.udc.ws.isd060.runfic.service.restservice.dto;

import es.udc.ws.isd060.runfic.model.carrera.Carrera;

import java.util.ArrayList;
import java.util.List;

public class CarreraToRestCarreraDtoConversor {


    // TODO comprobar necesarios , eliminar innecesarios

    public static List<RestCarreraDto> toRestCarreraDtos(List<Carrera> carreras) {
        List<RestCarreraDto> carreraDtos = new ArrayList<>(carreras.size());
        for (int i = 0; i < carreras.size(); i++) {
            Carrera carrera = carreras.get(i);
            carreraDtos.add(toRestCarreraDto(carrera));
        }
        return carreraDtos;
    }

    public static RestCarreraDto toRestCarreraDto(Carrera carrera) {

        return new RestCarreraDto(carrera.getIdCarrera(), carrera.getCiudadCelebracion(), carrera.getDescripcion(),
                carrera.getPrecioInscripcion(), carrera.getFechaCelebracion(), carrera.getPlazasDisponibles(), carrera.getPlazasOcupadas());
    }

    public static Carrera toCarrera(RestCarreraDto carrera) {

        return new Carrera( carrera.getCiudadCelebracion() , carrera.getDescripcion(), carrera.getPrecioInscripcion(),
                carrera.getFechaCelebracion() ,carrera.getPlazasDisponibles());
    }


}
