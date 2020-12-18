package es.udc.ws.isd060.runfic.service.restservice.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import es.udc.ws.isd060.runfic.service.restservice.dto.RestInscripcionDto;
import es.udc.ws.util.json.ObjectMapperFactory;
import es.udc.ws.util.json.exceptions.ParsingException;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

public class JsonToRestInscripcionDtoConversor {

    public static ObjectNode toObjectNode(RestInscripcionDto i) {

        ObjectNode InscripcionObject = JsonNodeFactory.instance.objectNode();

        if (i.getIdCarrera() != null) {
            InscripcionObject.put("IdInscripcion", i.getIdInscripcion());
        }
        InscripcionObject.put("Dorsal",i.getDorsal()).
                put("email", i.getEmail()).
                put("IdCarrera", i.getIdCarrera()).
                put("Tarjeta", i.getTarjeta()).
                put("FechaInscripcion", i.getFechaInscripcion().toString()).
                put("IsRecogido", i.isRecogido());

        return InscripcionObject;
    }

    public static ArrayNode toArrayNode(List<RestInscripcionDto> inscripcions) {

        ArrayNode InscripcionNode = JsonNodeFactory.instance.arrayNode();
        for (int j = 0; j < inscripcions.size(); j++) {
            RestInscripcionDto i = inscripcions.get(j);
            ObjectNode movieObject = toObjectNode(i);
            InscripcionNode.add(movieObject);
        }

        return InscripcionNode;
    }

    public static RestInscripcionDto toServiceInscripcionDto(InputStream jsonMovie) throws ParsingException {
        try {
            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            JsonNode rootNode = objectMapper.readTree(jsonMovie);

            if (rootNode.getNodeType() != JsonNodeType.OBJECT) {
                throw new ParsingException("Unrecognized JSON (object expected)");
            } else {
                ObjectNode inscripcionObject = (ObjectNode) rootNode;

                JsonNode inscripcionIdNode = inscripcionObject.get("IdInscripcion");
                Long inscripcionId = (inscripcionIdNode != null) ? inscripcionIdNode.longValue() : null;

                String email = inscripcionObject.get("email").textValue().trim();
                String tarjeta = inscripcionObject.get("Tarjeta").textValue().trim();
                Integer dorsal =  inscripcionObject.get("dorsal").intValue();
                Long idCarrera = inscripcionObject.get("IdCarrera").longValue();
                LocalDateTime fecha =  LocalDateTime.parse(inscripcionObject.get("FechaInscripcion").textValue());
                boolean recogido = inscripcionObject.get("IsRecogido").booleanValue();
                return new RestInscripcionDto(inscripcionId, dorsal, idCarrera, email, tarjeta, fecha, recogido);
            }
        } catch (ParsingException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

}
