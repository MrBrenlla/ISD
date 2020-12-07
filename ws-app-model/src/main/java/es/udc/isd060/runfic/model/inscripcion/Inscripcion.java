package es.udc.isd060.runfic.model.inscripcion;

import java.time.LocalDateTime;
import java.util.Objects;

public class Inscripcion {
    private Long idInscripcion;
    private Long idCarrera;
    private Integer dorsal;
    private String numTarjeta;
    private String email;
    private LocalDateTime fechaInscripcion;
    private boolean recogido;

    // Carlos
    public static final Integer DORSAL_NULL = -1;

    public Inscripcion(Long idInscripcion, Long idCarrera, Integer dorsal, String numTarjeta, String emailUsuario, LocalDateTime fechaInscripcion, boolean recogido) {
        this.idInscripcion = idInscripcion;
        this.idCarrera = idCarrera;
        this.dorsal = dorsal;
        this.numTarjeta = numTarjeta;
        this.email = emailUsuario;
        this.fechaInscripcion = fechaInscripcion;
        this.recogido = recogido;
    }

    public Inscripcion(Long idCarrera, Integer dorsal, String numTarjeta, String emailUsuario, LocalDateTime fechaInscripcion, boolean recogido) {
        this.idCarrera = idCarrera;
        this.dorsal = dorsal;
        this.numTarjeta = numTarjeta;
        this.email = emailUsuario;
        this.fechaInscripcion = fechaInscripcion;
        this.recogido = recogido;
    }

    // Carlos
    public Inscripcion(Long idCarrera, String numTarjeta, String email) {
        this.idCarrera = idCarrera;
        this.dorsal = DORSAL_NULL;
        this.numTarjeta = numTarjeta;
        this.email = email;
        this.fechaInscripcion = LocalDateTime.now();
        this.recogido = false;
    }


    public Long getIdInscripcion() {
        return idInscripcion;
    }

    public Long getIdCarrera() {
        return idCarrera;
    }

    public int getDorsal() {
        return dorsal;
    }

    public String getTarjeta() {
        return numTarjeta;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getFechaInscripcion() {
        return fechaInscripcion;
    }

    public boolean isRecogido() {
        return recogido;
    }

    public void setIdInscripcion(Long idInscripcion) {
        this.idInscripcion = idInscripcion;
    }

    public void setIdCarrera(Long idCarrera) {
        this.idCarrera = idCarrera;
    }

    public void setDorsal(int dorsal) {
        this.dorsal = dorsal;
    }

    // Carlos
    public void generateDorsal() {
        this.dorsal = idCarrera.intValue();
    }

    public void setTarjeta(String numTarjeta) {
        this.numTarjeta = numTarjeta;
    }

    public void setEmail(String emailUsuario) {
        this.email = emailUsuario;
    }

    public void setFechaInscripcion(LocalDateTime fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public void setRecogido(boolean dorsalEsRecogido) {
        this.recogido = dorsalEsRecogido;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Inscripcion)) return false;
        Inscripcion that = (Inscripcion) o;
        return getIdInscripcion().equals( that.getIdInscripcion()) &&
                getIdCarrera().equals(that.getIdCarrera()) &&
                getDorsal() == that.getDorsal() &&
                isRecogido() == that.isRecogido() &&
                Objects.equals(getTarjeta(), that.getTarjeta()) &&
                Objects.equals(getEmail(), that.getEmail()) &&
                Objects.equals(getFechaInscripcion(), that.getFechaInscripcion());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdInscripcion(), getIdCarrera(), getDorsal(), getTarjeta(), getEmail(), getFechaInscripcion(), isRecogido());
    }

    @Override
    public String toString() {
        return "Inscripcion{" +
                "idInscripcion=" + idInscripcion +
                ", idCarrera=" + idCarrera +
                ", dorsal=" + dorsal +
                ", numTarjeta='" + numTarjeta + '\'' +
                ", emailUsuario='" + email + '\'' +
                ", fechaInscripcion=" + fechaInscripcion +
                ", dorsalEsRecogido=" + recogido +
                '}';
    }
}
