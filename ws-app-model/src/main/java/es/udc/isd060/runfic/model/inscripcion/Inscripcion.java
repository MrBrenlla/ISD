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

    public Inscripcion(Long idInscripcion, Long idCarrera, Integer dorsal, String numTarjeta, String emailUsuario, LocalDateTime fechaInscripcion, boolean recogido) {
        this.idInscripcion = idInscripcion;
        this.idCarrera = idCarrera;
        this.dorsal = dorsal;
        this.numTarjeta = numTarjeta;
        this.email = emailUsuario;
        this.fechaInscripcion = fechaInscripcion;
        this.recogido = recogido;
    }


    public int getIdInscripcion() {
        return idInscripcion;
    }

    public int getIdCarrera() {
        return idCarrera;
    }

    public int getDorsal() {
        return dorsal;
    }

    public String getNumTarjeta() {
        return numTarjeta;
    }

    public String getEmailUsuario() {
        return email;
    }

    public LocalDateTime getFechaInscripcion() {
        return fechaInscripcion;
    }

    public boolean isDorsalRecogido() {
        return recogido;
    }

    public void setIdInscripcion(Long idInscripcion) {
        this.idInscripcion = idInscripcion;
    }

    public void setIdCarrera(Long idCarrera) {
        this.idCarrera = idCarrera;
    }

    public void setDorsal(int dorsal) {
        this.dorsal = new Integer(dorsal);
    }

    public void setNumTarjeta(String numTarjeta) {
        this.numTarjeta = numTarjeta;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.email = emailUsuario;
    }

    public void setFechaInscripcion(LocalDateTime fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public void setDorsalEsRecogido(boolean dorsalEsRecogido) {
        this.recogido = dorsalEsRecogido;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Inscripcion)) return false;
        Inscripcion that = (Inscripcion) o;
        return getIdInscripcion() == that.getIdInscripcion() &&
                getIdCarrera() == that.getIdCarrera() &&
                getDorsal() == that.getDorsal() &&
                isDorsalRecogido() == that.isDorsalRecogido() &&
                Objects.equals(getNumTarjeta(), that.getNumTarjeta()) &&
                Objects.equals(getEmailUsuario(), that.getEmailUsuario()) &&
                Objects.equals(getFechaInscripcion(), that.getFechaInscripcion());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdInscripcion(), getIdCarrera(), getDorsal(), getNumTarjeta(), getEmailUsuario(), getFechaInscripcion(), isDorsalRecogido());
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
