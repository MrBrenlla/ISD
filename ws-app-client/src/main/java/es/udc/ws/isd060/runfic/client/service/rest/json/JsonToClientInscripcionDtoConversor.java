package es.udc.ws.isd060.runfic.client.service.rest.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import es.udc.ws.isd060.runfic.client.service.dto.ClientCarreraDto;
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
        String s;
        LocalDateTime f= i.getFechaInscripcion();
        if(f==null) s=null;
        else s=f.toString();

        InscripcionObject.put("IdInscripcion", i.getIdInscripcion()).
                put("Dorsal",i.getDorsal()).
                put("Email", i.getEmail()).
                put("IdCarrera", i.getIdCarrera()).
                put("Tarjeta", i.getTarjeta()).
                put("FechaInscripcion", s).
                put("IsRecogido", i.isRecogido());

        return InscripcionObject;
    }
    public static ClientInscripcionDto toClientInscripcionDto(InputStream jsonInscripcion) throws ParsingException {
        try {

            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            JsonNode rootNode = objectMapper.readTree(jsonInscripcion);
            if (rootNode.getNodeType() != JsonNodeType.OBJECT) {
                throw new ParsingException("Unrecognized JSON (object expected)");
            } else {
                return toClientInscripcionDto(rootNode);
            }
        } catch (ParsingException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

    public static List<ClientInscripcionDto> toClientInscripcionDtos(InputStream jsonInscripcion) throws ParsingException {
        try {

            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            JsonNode rootNode = objectMapper.readTree(jsonInscripcion);
            if (rootNode.getNodeType() != JsonNodeType.ARRAY) {
                throw new ParsingException("Unrecognized JSON (array expected)");
            } else {
                ArrayNode iArray = (ArrayNode) rootNode;
                List<ClientInscripcionDto> iDtos = new ArrayList<>(iArray.size());
                for (JsonNode iNode : iArray) {
                    iDtos.add(toClientInscripcionDto(iNode));
                }

                return iDtos;
            }
        } catch (ParsingException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

    private static ClientInscripcionDto toClientInscripcionDto(JsonNode iNode) throws ParsingException {
        if (iNode.getNodeType() != JsonNodeType.OBJECT) {
            throw new ParsingException("Unrecognized JSON (object expected)");
        } else {
            ObjectNode iObject = (ObjectNode)iNode;

            JsonNode iIdNode = iObject.get("IdInscripcion");
            Long InsId = (iIdNode != null) ? iIdNode.longValue() : null;

            String email = iObject.get("Email").textValue().trim();
            String tarjeta =iObject.get("Tarjeta").textValue().trim();
            Long idC = iObject.get("IdCarrera").longValue();
            int dorsal = iObject.get("Dorsal").intValue();
            boolean recogido= iObject.get("Recogido").booleanValue();
            LocalDateTime fecha= LocalDateTime.parse(iObject.get("FechaInscripcion").textValue());

            return new ClientInscripcionDto(InsId,dorsal,idC,email,tarjeta,fecha,recogido);
        }
    }

}
