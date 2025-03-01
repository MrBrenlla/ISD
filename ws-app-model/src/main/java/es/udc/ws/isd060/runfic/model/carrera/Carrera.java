package es.udc.ws.isd060.runfic.model.carrera;

import java.time.LocalDateTime;
import java.util.Objects;

public class Carrera {
    private Long idCarrera;
    private String ciudadCelebracion;
    private String descripcion;
    private Float precioInscripcion;
    private LocalDateTime fechaAlta;
    private LocalDateTime fechaCelebracion;
    private Integer plazasDisponibles;
    private Integer plazasOcupadas;

    public Carrera(String ciudadCelebracion, String descripcion, Float precioInscripcion, LocalDateTime fechaAlta, LocalDateTime fechaCelebracion, Integer plazasDisponibles, Integer plazasOcupadas) {

        this.ciudadCelebracion = ciudadCelebracion;
        this.descripcion = descripcion;
        this.precioInscripcion = precioInscripcion;
        this.fechaAlta = fechaAlta;
        this.fechaCelebracion = fechaCelebracion;
        this.plazasDisponibles = plazasDisponibles;
        this.plazasOcupadas = plazasOcupadas;
    }

    /*
    public Carrera(Long idCarrera, Carrera otraCarrera ) {
        this.idCarrera = idCarrera;
        this.ciudadCelebracion = otraCarrera.getCiudadCelebracion();
        this.descripcion = otraCarrera.getDescripcion();
        this.precioInscripcion = otraCarrera.getPrecioInscripcion();
        this.fechaAlta = otraCarrera.getFechaAlta();
        this.fechaCelebracion = otraCarrera.getFechaCelebracion();
        this.plazasDisponibles = otraCarrera.getPlazasDisponibles();
        this.plazasOcupadas = otraCarrera.getPlazasOcupadas();
    }*/

    public Carrera(Long idCarrera, String ciudadCelebracion, String descripcion, Float precioInscripcion, LocalDateTime fechaAlta, LocalDateTime fechaCelebracion, Integer plazasDisponibles, Integer plazasOcupadas) {
        this.idCarrera = idCarrera;
        this.ciudadCelebracion = ciudadCelebracion;
        this.descripcion = descripcion;
        this.precioInscripcion = precioInscripcion;
        this.fechaAlta = fechaAlta;
        this.fechaCelebracion = fechaCelebracion;
        this.plazasDisponibles = plazasDisponibles;
        this.plazasOcupadas = plazasOcupadas;
    }

    // Carlos
    public Carrera(String ciudadCelebracion, String descripcion, Float precioInscripcion, LocalDateTime fechaCelebracion, Integer plazasDisponibles) {
        this.ciudadCelebracion = ciudadCelebracion;
        this.descripcion = descripcion;
        this.precioInscripcion = precioInscripcion;
        this.fechaAlta = LocalDateTime.now();
        this.fechaCelebracion = fechaCelebracion;
        this.plazasDisponibles = plazasDisponibles;
        this.plazasOcupadas = 0;
    }

    /*
        public Carrera(String ciudadCelebracion, String descripcion, Float precioInscripcion, LocalDateTime fechaAlta, LocalDateTime fechaCelebracion, Integer plazasDisponibles, Integer plazasOcupadas) {

        this.ciudadCelebracion = ciudadCelebracion;
        this.descripcion = descripcion;
        this.precioInscripcion = precioInscripcion;
        this.fechaAlta = fechaAlta;
        this.fechaCelebracion = fechaCelebracion;
        this.plazasDisponibles = plazasDisponibles;
        this.plazasOcupadas = plazasOcupadas;
    }
     */


    //Carlos
    public static Carrera copy ( Carrera original ){
        return new Carrera(original.ciudadCelebracion , original.descripcion,original.precioInscripcion,
                original.fechaAlta,original.fechaCelebracion, original.plazasDisponibles , original.plazasOcupadas);
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

    public LocalDateTime getFechaAlta() {
        return fechaAlta;
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

    public void setIdCarrera(Long idCarrera) {
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

    public void setPlazasDisponibles(Integer plazasDisponibles) {
        this.plazasDisponibles = plazasDisponibles;
    }

    public void setPlazasOcupadas(Integer plazasOcupadas) {
        this.plazasOcupadas = plazasOcupadas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Carrera)) return false;
        Carrera carrera = (Carrera) o;
        return getIdCarrera().equals(carrera.getIdCarrera()) &&
                getPlazasDisponibles().equals(carrera.getPlazasDisponibles()) &&
                getPlazasOcupadas().equals( carrera.getPlazasOcupadas()) &&
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

    // Carlos
    public boolean same(Object o) {
        if (this == o) return true;
        if (!(o instanceof Carrera)) return false;
        Carrera carrera = (Carrera) o;
        return  getPlazasDisponibles().equals(carrera.getPlazasDisponibles()) &&
                Objects.equals(getCiudadCelebracion(), carrera.getCiudadCelebracion()) &&
                Objects.equals(getDescripcion(), carrera.getDescripcion()) &&
                Objects.equals(getPrecioInscripcion(), carrera.getPrecioInscripcion()) &&
                getFechaCelebracion().equals(carrera.getFechaCelebracion());
    }
}

