package es.udc.ws.isd060.runfic.service.thriftservice;

import es.udc.ws.isd060.runfic.model.inscripcion.Inscripcion;
import es.udc.ws.isd060.runfic.thrift.ThriftInscripcionDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class InscripcionToThriftInscripcionDtoConversor {

    public static Inscripcion toInscripcion(ThriftInscripcionDto i) {
        return new Inscripcion(i.getInscripcionId(), i.getCarreraId(),i.getDorsal(),
                i.getTarjeta(),i.getEmail(), LocalDateTime.parse(i.getFechaInscripcion()),
                i.isRecogido());
    }

    public static List<ThriftInscripcionDto> toThriftInscripcionDtos(List<Inscripcion> inscripcions) {

        List<ThriftInscripcionDto> dtos = new ArrayList<>(inscripcions.size());

        for (Inscripcion i : inscripcions) {
            dtos.add(toThriftMovieDto(i));
        }
        return dtos;

    }

    private static ThriftInscripcionDto toThriftMovieDto(Inscripcion i) {

        return new ThriftInscripcionDto(i.getIdInscripcion(),i.getIdCarrera(),i.getTarjeta(),
                i.getEmail(),i.getDorsal(),i.isRecogido(),i.getFechaInscripcion().toString());

    }

}
