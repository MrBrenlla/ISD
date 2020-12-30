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
    private Long codRecogerDorsal;
    private String numTarjeta;

    public RestRecogerdorsalDto(Long codRecogerDorsal, String numTarjeta) {
        this.codRecogerDorsal = codRecogerDorsal;
        this.numTarjeta = numTarjeta;
    }

    public Long getCodRecogerDorsal() {
        return codRecogerDorsal;
    }

    public String getNumTarjeta() {
        return numTarjeta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RestRecogerdorsalDto)) return false;
        RestRecogerdorsalDto that = (RestRecogerdorsalDto) o;
        return codRecogerDorsal.equals(that.codRecogerDorsal) && numTarjeta.equals(that.numTarjeta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codRecogerDorsal, numTarjeta);
    }

    @Override
    public String toString() {
        return "RestRecogerdorsalDto{" +
                "codRecogerDorsal=" + codRecogerDorsal +
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

                JsonNode idInscripcionNode = recogerdorsalObject.get("codRecogerDorsal");
                 this.codRecogerDorsal = (idInscripcionNode != null) ? idInscripcionNode.longValue() : null;

                 this.numTarjeta = recogerdorsalObject.get("numTarjeta").textValue().trim();

            }
        } catch (ParsingException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

}
