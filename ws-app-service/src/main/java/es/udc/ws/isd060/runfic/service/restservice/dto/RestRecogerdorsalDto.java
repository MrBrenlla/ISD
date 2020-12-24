package es.udc.ws.isd060.runfic.service.restservice.dto;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import es.udc.ws.util.json.ObjectMapperFactory;
import es.udc.ws.util.json.exceptions.ParsingException;

import java.io.InputStream;
import java.util.Objects;

public class RestRecogerdorsalDto {
    private Long idInscripcion;
    private String numTarjeta;

    public RestRecogerdorsalDto(Long idInscripcion, String numTarjeta) {
        this.idInscripcion = idInscripcion;
        this.numTarjeta = numTarjeta;
    }

    public Long getIdInscripcion() {
        return idInscripcion;
    }

    public String getNumTarjeta() {
        return numTarjeta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RestRecogerdorsalDto)) return false;
        RestRecogerdorsalDto that = (RestRecogerdorsalDto) o;
        return idInscripcion.equals(that.idInscripcion) && numTarjeta.equals(that.numTarjeta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idInscripcion, numTarjeta);
    }

    @Override
    public String toString() {
        return "RestRecogerdorsalDto{" +
                "idInscripcion=" + idInscripcion +
                ", numTarjeta='" + numTarjeta + '\'' +
                '}';
    }

    public RestRecogerdorsalDto(InputStream inputStream) throws ParsingException {
        try {
            // Obtenemos Instancia de ObjectMapper
            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            // Llamamos a objectMapper.readTree para je intente enerar el arbol Jackson
            JsonNode rootNode = objectMapper.readTree(inputStream);

            if (rootNode.getNodeType() != JsonNodeType.OBJECT) {
                // Si el elemento del nodo raíz de es arbol no es un OBJECT -> ParsingException
                throw new ParsingException("Unrecognized JSON (object expected)");
            } else {
                ObjectNode recogerdorsalObject = (ObjectNode) rootNode;

                JsonNode idInscripcionNode = recogerdorsalObject.get("idInscripcion");
                 this.idInscripcion = (idInscripcionNode != null) ? idInscripcionNode.longValue() : null;

                 this.numTarjeta = recogerdorsalObject.get("numTarjeta").textValue().trim();

            }
        } catch (ParsingException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

}
