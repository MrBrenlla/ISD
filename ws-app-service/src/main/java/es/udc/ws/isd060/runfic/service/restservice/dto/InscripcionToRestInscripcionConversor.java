package es.udc.ws.isd060.runfic.service.restservice.dto;

import es.udc.ws.isd060.runfic.model.inscripcion.Inscripcion;

import java.util.ArrayList;
import java.util.List;

public class InscripcionToRestInscripcionConversor {

    public static RestInscripcionDto toRestInscripcionDto(Inscripcion inscripcion) {

        return new RestInscripcionDto(inscripcion.getIdInscripcion(), inscripcion.getDorsal(), inscripcion.getIdCarrera(), inscripcion.getEmail(), inscripcion.getTarjeta(),
                inscripcion.getFechaInscripcion(), inscripcion.isRecogido());
    }

    public static List<RestInscripcionDto> toRestInscripcionDto(List<Inscripcion> inscripcions) {

        ArrayList<RestInscripcionDto> list= new ArrayList<>();

        for(Inscripcion i : inscripcions){
            list.add(toRestInscripcionDto(i));
        }

        return list;
    }

    public static Inscripcion toInscripcion(RestInscripcionDto r) {

        return new Inscripcion(r.getIdInscripcion(), r.getIdCarrera(), r.getDorsal(), r.getEmail(), r.getTarjeta(),
                r.getFechaInscripcion(), r.isRecogido());
    }
}
