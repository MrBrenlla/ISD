package es.udc.ws.isd060.runfic.client.service.dto;

import java.time.LocalDateTime;

public class ClientCarreraDto {
    private Long idCarrera;
    private String ciudadCelebracion;
    private String descripcion;
    private Float  precioInscripcion;
    private LocalDateTime fechaCelebracion;
    private Integer plazasDisponibles;
    private Integer plazasLibres;

    public ClientCarreraDto() {

    }

    public ClientCarreraDto(Long idCarrera, String ciudadCelebracion, String descripcion, Float precioInscripcion, LocalDateTime fechaCelebracion, Integer plazasDisponibles) {
        this.idCarrera = idCarrera;
        this.ciudadCelebracion = ciudadCelebracion;
        this.descripcion = descripcion;
        this.precioInscripcion = precioInscripcion;
        this.fechaCelebracion = fechaCelebracion;
        this.plazasDisponibles = plazasDisponibles;
    }

    public Long getIdCarrera() {
        return idCarrera;
    }
    public void setIdCarrera(Long idCarrera) {this.idCarrera = idCarrera;}

    public String getCiudadCelebracion() {
        return ciudadCelebracion;
    }
    public void setCiudadCelebracion(String ciudadCelebracion) { this.ciudadCelebracion = ciudadCelebracion; }

    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion;}

    public Float getPrecioInscripcion() {
        return precioInscripcion;
    }
    public void setPrecioInscripcion(Float precioInscripcion) { this.precioInscripcion = precioInscripcion;}

    public LocalDateTime getFechaCelebracion() { return fechaCelebracion; }
    public void setFechaCelebracion(LocalDateTime fechaCelebracion) { this.fechaCelebracion = fechaCelebracion; }

    public Integer getPlazasDisponibles() {
        return plazasDisponibles;
    }
    public void setPlazasDisponibles(Integer plazasDisponibles) { this.plazasDisponibles = plazasDisponibles; }

    public Integer getPlazasLibres() {
        return plazasLibres;
    }
    public void setPlazasLibres(Integer plazasLibres) { this.plazasLibres = plazasLibres; }

    @Override
    public String toString() {
        return "CarreraDto [idCarrera=" + idCarrera + ", ciudadCelebracion=" + ciudadCelebracion
                + ", descripcion=" + descripcion + " precioInscripcion= " + precioInscripcion + " fechaCelebracion= "
                + fechaCelebracion + ", plazasDisponibles=" + plazasDisponibles + ", plazasLibres=" + plazasLibres + "]";
    }
}
