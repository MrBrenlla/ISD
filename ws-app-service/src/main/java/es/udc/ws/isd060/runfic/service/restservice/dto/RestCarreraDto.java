package es.udc.ws.isd060.runfic.service.restservice.dto;

import java.time.LocalDateTime;

public class RestCarreraDto {
    private Long idCarrera;
    private String ciudadCelebracion;
    private String descripcion;
    private Float  precioInscripcion;
    private LocalDateTime fechaCelebracion;
    private Integer plazasDisponibles;
    private Integer plazasOcupadas;

    // Necesario para Carrera to RestCarreraDtoConversor
    public RestCarreraDto(Long idCarrera, String ciudadCelebracion, String descripcion, Float precioInscripcion, LocalDateTime fechaCelebracion, Integer plazasDisponibles, Integer plazasOcupadas) {
        this.idCarrera = idCarrera;
        this.ciudadCelebracion = ciudadCelebracion;
        this.descripcion = descripcion;
        this.precioInscripcion = precioInscripcion;
        this.fechaCelebracion = fechaCelebracion;
        this.plazasDisponibles = plazasDisponibles;
        this.plazasOcupadas = plazasOcupadas;
    }

    public Long getIdCarrera() {
        return idCarrera;
    }

    public String getCiudadCelebracion() {
        return ciudadCelebracion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Float getPrecioInscripcion() {
        return precioInscripcion;
    }

    public LocalDateTime getFechaCelebracion() {
        return fechaCelebracion;
    }

    public Integer getPlazasDisponibles() {
        return plazasDisponibles;
    }

    public Integer getPlazasOcupadas() {
        return plazasOcupadas;
    }


}
