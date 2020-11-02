package es.udc.isd060.runfic.model.carrera;

import java.time.LocalDateTime;
import java.util.Objects;

public class Carrera {
    private int idCarrera;
    private String ciudadCelebracion;
    private String descripcion;
    private Float precioInscripcion;
    private LocalDateTime fechaAlta;
    private LocalDateTime fechaCelebracion;
    private int plazasDisponibles;
    private int plazasOcupadas;


    public Carrera(int idCarrera, String ciudadCelebracion, String descripcion, Float precioInscripcion, LocalDateTime fechaAlta, LocalDateTime fechaCelebracion, int plazasDisponibles, int plazasOcupadas) {
        this.idCarrera = idCarrera;
        this.ciudadCelebracion = ciudadCelebracion;
        this.descripcion = descripcion;
        this.precioInscripcion = precioInscripcion;
        this.fechaAlta = fechaAlta;
        this.fechaCelebracion = fechaCelebracion;
        this.plazasDisponibles = plazasDisponibles;
        this.plazasOcupadas = plazasOcupadas;
    }

    public int getIdCarrera() {
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

    public LocalDateTime getFechaAlta() {
        return fechaAlta;
    }

    public LocalDateTime getFechaCelebracion() {
        return fechaCelebracion;
    }

    public int getPlazasDisponibles() {
        return plazasDisponibles;
    }

    public int getPlazasOcupadas() {
        return plazasOcupadas;
    }

    public void setIdCarrera(int idCarrera) {
        this.idCarrera = idCarrera;
    }

    public void setCiudadCelebracion(String ciudadCelebracion) {
        this.ciudadCelebracion = ciudadCelebracion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPrecioInscripcion(Float precioInscripcion) {
        this.precioInscripcion = precioInscripcion;
    }

    public void setFechaAlta(LocalDateTime fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public void setFechaCelebracion(LocalDateTime fechaCelebracion) {
        this.fechaCelebracion = fechaCelebracion;
    }

    public void setPlazasDisponibles(int plazasDisponibles) {
        this.plazasDisponibles = plazasDisponibles;
    }

    public void setPlazasOcupadas(int plazasOcupadas) {
        this.plazasOcupadas = plazasOcupadas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Carrera)) return false;
        Carrera carrera = (Carrera) o;
        return getIdCarrera() == carrera.getIdCarrera() &&
                getPlazasDisponibles() == carrera.getPlazasDisponibles() &&
                getPlazasOcupadas() == carrera.getPlazasOcupadas() &&
                Objects.equals(getCiudadCelebracion(), carrera.getCiudadCelebracion()) &&
                Objects.equals(getDescripcion(), carrera.getDescripcion()) &&
                Objects.equals(getPrecioInscripcion(), carrera.getPrecioInscripcion()) &&
                Objects.equals(getFechaAlta(), carrera.getFechaAlta()) &&
                Objects.equals(getFechaCelebracion(), carrera.getFechaCelebracion());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdCarrera(), getCiudadCelebracion(), getDescripcion(), getPrecioInscripcion(), getFechaAlta(), getFechaCelebracion(), getPlazasDisponibles(), getPlazasOcupadas());
    }

    @Override
    public String toString() {
        return "Carrera{" +
                "idCarrera=" + idCarrera +
                ", ciudadCelebracion='" + ciudadCelebracion + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precioInscripcion=" + precioInscripcion +
                ", fechaAlta=" + fechaAlta +
                ", fechaCelebracion=" + fechaCelebracion +
                ", plazasDisponibles=" + plazasDisponibles +
                ", plazasOcupadas=" + plazasOcupadas +
                '}';
    }
}
