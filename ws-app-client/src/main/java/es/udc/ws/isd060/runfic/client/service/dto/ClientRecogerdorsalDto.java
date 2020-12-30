package es.udc.ws.isd060.runfic.client.service.dto;

import java.util.Objects;

public class ClientRecogerdorsalDto {
    private Long codRecogerDorsal;
    private String numTarjeta;

    public ClientRecogerdorsalDto(Long codRecogerDorsal, String numTarjeta) {
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
        if (!(o instanceof ClientRecogerdorsalDto)) return false;
        ClientRecogerdorsalDto that = (ClientRecogerdorsalDto) o;
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

}
