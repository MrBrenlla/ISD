package es.udc.ws.isd060.runfic.client.service.thrift;

import es.udc.ws.isd060.runfic.client.service.dto.ClientInscripcionDto;
import es.udc.ws.isd060.runfic.thrift.ThriftInscripcionDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ClientInscripcionDtoToThriftInscripcionDtoConversor {

    public static ThriftInscripcionDto toThriftInscripcionDto(
        ClientInscripcionDto i) {

        Long InscripcionId = i.getIdInscripcion();
        return new ThriftInscripcionDto(
            InscripcionId == null ? -1 : InscripcionId.longValue(),
            i.getIdCarrera().longValue(),i.getTarjeta(),i.getEmail(),0,false,
                i.getFechaInscripcion()==null ? "" : i.getFechaInscripcion().toString());
    }

    public static List<ClientInscripcionDto> toClientInscripcionDto(List<ThriftInscripcionDto> inscropcions) {

        List<ClientInscripcionDto> clientInscripcionDtos = new ArrayList<>(inscropcions.size());

        for (ThriftInscripcionDto i : inscropcions) {
            clientInscripcionDtos.add(toClientInscripcionDto(i));
        }
        return clientInscripcionDtos;

    }

    public static ClientInscripcionDto toClientInscripcionDto(ThriftInscripcionDto i) {

        return new ClientInscripcionDto(i.getInscripcionId(),i.getDorsal(),i.carreraId,i.email,i.tarjeta,LocalDateTime.parse(i.fechaInscripcion),i.recogido);

    }

}
