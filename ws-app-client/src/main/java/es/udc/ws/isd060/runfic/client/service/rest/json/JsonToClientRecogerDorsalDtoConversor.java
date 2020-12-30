package es.udc.ws.isd060.runfic.client.service.rest.json;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import es.udc.ws.isd060.runfic.client.service.dto.ClientRecogerdorsalDto;


public class JsonToClientRecogerDorsalDtoConversor {
    public static Object toObjectNode(ClientRecogerdorsalDto clientRecogerdorsalDto) {
        ObjectNode inscripcionObject = JsonNodeFactory.instance.objectNode();

        inscripcionObject.put("codRecogerDorsal", clientRecogerdorsalDto.getCodRecogerDorsal()).
                put("numTarjeta",clientRecogerdorsalDto.getNumTarjeta());

        return inscripcionObject;
    }
}
