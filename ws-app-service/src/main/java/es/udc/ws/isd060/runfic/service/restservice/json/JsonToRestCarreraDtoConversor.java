package es.udc.ws.isd060.runfic.service.restservice.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import es.udc.ws.isd060.runfic.service.restservice.dto.RestCarreraDto;
import es.udc.ws.util.json.ObjectMapperFactory;
import es.udc.ws.util.json.exceptions.ParsingException;

import java.awt.*;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

public class JsonToRestCarreraDtoConversor {

    public static ObjectNode toObjectNode(RestCarreraDto carrera) {

        ObjectNode carreraObject = JsonNodeFactory.instance.objectNode();

        if (carrera.getIdCarrera() != null) {
            carreraObject.put("idCarrera", carrera.getIdCarrera());
        }
        carreraObject.put("ciudadCelebracion", carrera.getCiudadCelebracion()).
                put("descripcion", carrera.getDescripcion()).
                put("precioInscripcion", carrera.getPrecioInscripcion()).
                put("fechaCelebracion", carrera.getFechaCelebracion().toString()).
                put("plazasDisponibles", carrera.getPlazasDisponibles());
        carreraObject.put("plazasOcupadas", carrera.getPlazasOcupadas());
        return carreraObject;
    }

    public static ArrayNode toArrayNode(List<RestCarreraDto> carreras) {

        ArrayNode carrerasNode = JsonNodeFactory.instance.arrayNode();
        for (int i = 0; i < carreras.size(); i++) {
            RestCarreraDto carreraDto = carreras.get(i);
            ObjectNode carreraObject = toObjectNode(carreraDto);
            carrerasNode.add(carreraObject);
        }

        return carrerasNode;
    }

    public static RestCarreraDto toServiceCarreraDto(InputStream jsonCarrera) throws ParsingException {
        try {
            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            JsonNode rootNode = objectMapper.readTree(jsonCarrera);

            if (rootNode.getNodeType() != JsonNodeType.OBJECT) {
                throw new ParsingException("Unrecognized JSON (object expected)");
            } else {
                ObjectNode carreraObject = (ObjectNode) rootNode;

                JsonNode carreraIdNode = carreraObject.get("idCarrera");
                Long carreraId = (carreraIdNode != null) ? carreraIdNode.longValue() : null;

                String ciudadCelebracion = carreraObject.get("ciudadCelebracion").textValue().trim();
                String descripcion = carreraObject.get("descripcion").textValue().trim();
                float precioInscripcion = carreraObject.get("precioInscripcion").floatValue();
                LocalDateTime fechaCelebracion = LocalDateTime.parse(carreraObject.get("fechaCelebracion").textValue());
                Integer plazasDisponibles = carreraObject.get("plazasDisponibles").intValue();
                Integer plazasOcupadas;
                if(carreraObject.has("plazasOcupadas"))
                {
                    plazasOcupadas = carreraObject.get("plazasOcupadas").intValue();
                }
                else
                    plazasOcupadas = null;

                return new RestCarreraDto(carreraId, ciudadCelebracion, descripcion, precioInscripcion, fechaCelebracion,plazasDisponibles, plazasOcupadas);
            }
        } catch (ParsingException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

}
