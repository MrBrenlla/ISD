package es.udc.ws.isd060.runfic.service.restservice.dto;

import java.time.LocalDateTime;

public class RestInscripcionDto {
    private Long idInscripcion;
    private Integer dorsal;
    private Long idCarrera;
    private String email;
    private String tarjeta;
    private LocalDateTime fechaInscripcion;
    private boolean recogido;

    public RestInscripcionDto(Long idInscripcion, Integer dorsal, Long idCarrera,
                              String email, String tarjeta, LocalDateTime fechaInscripcion, boolean recogido) {
        this.idInscripcion = idInscripcion;
        this.dorsal = dorsal;
        this.idCarrera = idCarrera;
        this.email = email;
        this.tarjeta = tarjeta;
        this.fechaInscripcion = fechaInscripcion;
        this.recogido = recogido;
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

    public String getEmail() {
        return email;
    }

    public String getTarjeta() {
        return tarjeta;
    }

    public LocalDateTime getFechaInscripcion() {
        return fechaInscripcion;
    }

    public boolean isRecogido() {
        return recogido;
    }
}
