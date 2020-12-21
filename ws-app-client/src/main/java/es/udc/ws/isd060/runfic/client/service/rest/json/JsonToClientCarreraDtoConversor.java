package es.udc.ws.isd060.runfic.client.service.rest.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import es.udc.ws.isd060.runfic.client.service.dto.ClientCarreraDto;
import es.udc.ws.util.json.ObjectMapperFactory;
import es.udc.ws.util.json.exceptions.ParsingException;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JsonToClientCarreraDtoConversor {

    public static ObjectNode toObjectNode(ClientCarreraDto carrera) throws IOException {

        ObjectNode carreraObject = JsonNodeFactory.instance.objectNode();

        if (carrera.getIdCarrera() != null) {
            carreraObject.put("idCarrera", carrera.getIdCarrera());
        }
        carreraObject.put("ciudadCelebracion", carrera.getCiudadCelebracion()).
                put("descripcion", carrera.getDescripcion()).
                put("precioInscripcion", carrera.getPrecioInscripcion()).
                put("fechaCelebracion", carrera.getFechaCelebracion().toString()).
                put("plazasDisponibles", carrera.getPlazasDisponibles()).
                put("plazasOcupadas", carrera.getPlazasOcupadas());

        return carreraObject;
    }

    public static ClientCarreraDto toClientCarreraDto(InputStream jsonMovie) throws ParsingException {
        try {

            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            JsonNode rootNode = objectMapper.readTree(jsonMovie);
            if (rootNode.getNodeType() != JsonNodeType.OBJECT) {
                throw new ParsingException("Unrecognized JSON (object expected)");
            } else {
                return toClientCarreraDto(rootNode);
            }
        } catch (ParsingException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

    public static List<ClientCarreraDto> toClientCarreraDtos(InputStream jsonCarreras) throws ParsingException {
        try {

            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            JsonNode rootNode = objectMapper.readTree(jsonCarreras);
            if (rootNode.getNodeType() != JsonNodeType.ARRAY) {
                throw new ParsingException("Unrecognized JSON (array expected)");
            } else {
                ArrayNode carrerasArray = (ArrayNode) rootNode;
                List<ClientCarreraDto> carreraDtos = new ArrayList<>(carrerasArray.size());
                for (JsonNode carreraNode : carrerasArray) {
                    carreraDtos.add(toClientCarreraDto(carreraNode));
                }

                return carreraDtos;
            }
        } catch (ParsingException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

    private static ClientCarreraDto toClientCarreraDto(JsonNode carreraNode) throws ParsingException {

            if (carreraNode.getNodeType() != JsonNodeType.OBJECT) {
                throw new ParsingException("Unrecognized JSON (object expected)");
            } else {
                ObjectNode carreraObject = (ObjectNode) carreraNode;

                JsonNode carreraIdNode = carreraObject.get("idCarrera");
                Long carreraId = (carreraIdNode != null) ? carreraIdNode.longValue() : null;

                String ciudadCelebracion = carreraObject.get("ciudadCelebracion").textValue().trim();
                String descripcion = carreraObject.get("descripcion").textValue().trim();
                float precioInscripcion = carreraObject.get("precioInscripcion").floatValue();
                LocalDateTime fechaCelebracion = LocalDateTime.parse(carreraObject.get("fechaCelebracion").textValue());
                Integer plazasDisponibles = carreraObject.get("plazasDisponibles").intValue();
                Integer plazasOcupadas = carreraObject.get("plazasOcupadas").intValue();

                return new ClientCarreraDto(carreraId, ciudadCelebracion, descripcion, precioInscripcion, fechaCelebracion,plazasDisponibles, plazasOcupadas);
            }
    }
}
