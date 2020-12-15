package es.udc.ws.isd060.runfic.service.restservice.dto;

import java.time.LocalDateTime;

public class RestInscripcionDto {
    private Long idInscripcion;
    private Integer dorsal;
    private Long idCarrera;
    private String emailUsuario;
    private String numTarjeta;
    private LocalDateTime fechaInscripcion;
    private boolean dorsalEsrecogido;

    public RestInscripcionDto(Long idInscripcion, Integer dorsal, Long idCarrera,
                              String emailUsuario, String numTarjeta, LocalDateTime fechaInscripcion, boolean dorsalEsrecogido) {
        this.idInscripcion = idInscripcion;
        this.dorsal = dorsal;
        this.idCarrera = idCarrera;
        this.emailUsuario = emailUsuario;
        this.numTarjeta = numTarjeta;
        this.fechaInscripcion = fechaInscripcion;
        this.dorsalEsrecogido = dorsalEsrecogido;
    }

    public Long getIdInscripcion() {
        return idInscripcion;
    }

    public Integer getDorsal() {
        return dorsal;
    }

    public Long getIdCarrera() {
        return idCarrera;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public String getNumTarjeta() {
        return numTarjeta;
    }

    public LocalDateTime getFechaInscripcion() {
        return fechaInscripcion;
    }

    public boolean isDorsalEsrecogido() {
        return dorsalEsrecogido;
    }
}
