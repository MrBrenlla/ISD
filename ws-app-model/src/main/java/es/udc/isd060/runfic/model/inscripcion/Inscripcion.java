package es.udc.isd060.runfic.model.inscripcion;

import java.time.LocalDateTime;
import java.util.Objects;

public class Inscripcion {
    private int idInscripcion;
    private int idCarrera;
    private int dorsal;
    private String numTarjeta;
    private String emailUsuario;
    private LocalDateTime fechaInscripcion;
    private boolean dorsalEsRecogido;

    public Inscripcion(int idInscripcion, int idCarrera, int dorsal, String numTarjeta, String emailUsuario, LocalDateTime fechaInscripcion, boolean dorsalEsRecogido) {
        this.idInscripcion = idInscripcion;
        this.idCarrera = idCarrera;
        this.dorsal = dorsal;
        this.numTarjeta = numTarjeta;
        this.emailUsuario = emailUsuario;
        this.fechaInscripcion = fechaInscripcion;
        this.dorsalEsRecogido = dorsalEsRecogido;
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
        return emailUsuario;
    }

    public LocalDateTime getFechaInscripcion() {
        return fechaInscripcion;
    }

    public boolean isDorsalEsRecogido() {
        return dorsalEsRecogido;
    }

    public void setIdInscripcion(int idInscripcion) {
        this.idInscripcion = idInscripcion;
    }

    public void setIdCarrera(int idCarrera) {
        this.idCarrera = idCarrera;
    }

    public void setDorsal(int dorsal) {
        this.dorsal = dorsal;
    }

    public void setNumTarjeta(String numTarjeta) {
        this.numTarjeta = numTarjeta;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public void setFechaInscripcion(LocalDateTime fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public void setDorsalEsRecogido(boolean dorsalEsRecogido) {
        this.dorsalEsRecogido = dorsalEsRecogido;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Inscripcion)) return false;
        Inscripcion that = (Inscripcion) o;
        return getIdInscripcion() == that.getIdInscripcion() &&
                getIdCarrera() == that.getIdCarrera() &&
                getDorsal() == that.getDorsal() &&
                isDorsalEsRecogido() == that.isDorsalEsRecogido() &&
                Objects.equals(getNumTarjeta(), that.getNumTarjeta()) &&
                Objects.equals(getEmailUsuario(), that.getEmailUsuario()) &&
                Objects.equals(getFechaInscripcion(), that.getFechaInscripcion());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdInscripcion(), getIdCarrera(), getDorsal(), getNumTarjeta(), getEmailUsuario(), getFechaInscripcion(), isDorsalEsRecogido());
    }

    @Override
    public String toString() {
        return "Inscripcion{" +
                "idInscripcion=" + idInscripcion +
                ", idCarrera=" + idCarrera +
                ", dorsal=" + dorsal +
                ", numTarjeta='" + numTarjeta + '\'' +
                ", emailUsuario='" + emailUsuario + '\'' +
                ", fechaInscripcion=" + fechaInscripcion +
                ", dorsalEsRecogido=" + dorsalEsRecogido +
                '}';
    }
}
