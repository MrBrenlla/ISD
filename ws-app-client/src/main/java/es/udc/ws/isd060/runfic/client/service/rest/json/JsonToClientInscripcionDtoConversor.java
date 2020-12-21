package es.udc.ws.isd060.runfic.client.service.rest.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import es.udc.ws.isd060.runfic.client.service.dto.ClientInscripcionDto;
import es.udc.ws.util.json.ObjectMapperFactory;
import es.udc.ws.util.json.exceptions.ParsingException;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JsonToClientInscripcionDtoConversor {
    public static ObjectNode toObjectNode(ClientInscripcionDto i) throws IOException {

        ObjectNode InscripcionObject = JsonNodeFactory.instance.objectNode();

        if (i.getIdCarrera() != null) {
            InscripcionObject.put("IdInscripcion", i.getIdInscripcion());
        }
        InscripcionObject.put("Dorsal",i.getDorsal()).
                put("Email", i.getEmail()).
                put("IdCarrera", i.getIdCarrera()).
                put("Tarjeta", i.getTarjeta()).
                put("FechaInscripcion", i.getFechaInscripcion().toString()).
                put("IsRecogido", i.isRecogido());

        return InscripcionObject;
    }

    public static List<ClientInscripcionDto> toClientInscripcionDtos(InputStream jsonInscripciones) throws ParsingException {
        try {

            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            JsonNode rootNode = objectMapper.readTree(jsonInscripciones);
            if (rootNode.getNodeType() != JsonNodeType.ARRAY) {
                throw new ParsingException("Unrecognized JSON (array expected)");
            } else {
                ArrayNode inscripcionesArray = (ArrayNode) rootNode;
                List<ClientInscripcionDto> inscripcionDtos = new ArrayList<>(inscripcionesArray.size());
                for (JsonNode inscripcionNode : inscripcionesArray) {
                    inscripcionDtos.add(toClientInscripcionDto(inscripcionNode));
                }

                return inscripcionDtos;
            }
        } catch (ParsingException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }


    public static ClientInscripcionDto toClientInscripcionDto(JsonNode inscripcionNode) throws ParsingException {
            if (inscripcionNode.getNodeType() != JsonNodeType.OBJECT) {
                throw new ParsingException("Unrecognized JSON (object expected)");
            } else {
                ObjectNode inscripcionObject = (ObjectNode) inscripcionNode;

                JsonNode inscripcionIdNode = inscripcionObject.get("IdInscripcion");
                Long inscripcionId = (inscripcionIdNode != null) ? inscripcionIdNode.longValue() : null;
                String email = inscripcionObject.get("Email").textValue().trim();
                String tarjeta = inscripcionObject.get("Tarjeta").textValue().trim();
                inscripcionIdNode = inscripcionObject.get("IdCarrera");
                Long idCarrera = (inscripcionIdNode != null) ? inscripcionIdNode.longValue() : null;
                inscripcionIdNode = inscripcionObject.get("Dorsal");
                Integer dorsal = (inscripcionIdNode != null) ? inscripcionIdNode.intValue() : null;
                inscripcionIdNode = inscripcionObject.get("Fecha");
                LocalDateTime fecha = (inscripcionIdNode != null) ? LocalDateTime.parse(inscripcionIdNode.textValue()) : null;
                inscripcionIdNode = inscripcionObject.get("IsRecogido");
                boolean recogido = (inscripcionIdNode != null) ? inscripcionIdNode.booleanValue() : false;
                System.out.println("listo");

                return new ClientInscripcionDto(inscripcionId, dorsal, idCarrera, email, tarjeta, fecha, recogido);
            }
    }

}
