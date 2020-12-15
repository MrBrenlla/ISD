package es.udc.ws.isd060.runfic.service.restservice.dto;

import es.udc.isd060.runfic.model.inscripcion.Inscripcion;

public class InscripcionToRestInscripcionConversor {

    public static RestInscripcionDto toRestInscripcionDto(Inscripcion inscripcion) {

        // TODO comprobar necesarios , eliminar innecesarios

        return new RestInscripcionDto(inscripcion.getIdInscripcion(), inscripcion.getDorsal(), inscripcion.getIdCarrera(), inscripcion.getEmail(), inscripcion.getTarjeta(),
                inscripcion.getFechaInscripcion(), inscripcion.isRecogido());
    }
}
