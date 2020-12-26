package es.udc.ws.isd060.runfic.client.service.dto;

import java.time.LocalDateTime;

public class ClientInscripcionDto {

    private Long idInscripcion;
    private Integer dorsal;
    private Long idCarrera;
    private String email;
    private String tarjeta;
    private LocalDateTime fechaInscripcion;
    private boolean recogido;

    public ClientInscripcionDto() {

    }

    public ClientInscripcionDto(Long idInscripcion, Integer dorsal, Long idCarrera,
                              String email, String tarjeta, LocalDateTime fechaInscripcion, boolean recogido) {
        this.idInscripcion = idInscripcion;
        this.dorsal = dorsal;
        this.idCarrera = idCarrera;
        this.email = email;
        this.tarjeta = tarjeta;
        this.fechaInscripcion = fechaInscripcion;
        this.recogido = recogido;
    }

    public Long getIdInscripcion() { return idInscripcion; }
    public void setIdInscripcion(Long idInscripcion) { this.idInscripcion = idInscripcion; }

    public Integer getDorsal() {  return dorsal; }
    public void setDorsal(Integer dorsal) { this.dorsal = dorsal; }

    public Long getIdCarrera() { return idCarrera; }
    public void setIdCarrera(Long idCarrera) { this.idCarrera = idCarrera; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTarjeta() { return tarjeta; }
    public void setTarjeta(String tarjeta) { this.tarjeta = tarjeta; }

    public LocalDateTime getFechaInscripcion() { return fechaInscripcion; }
    public void setFechaInscripcion(LocalDateTime fechaInscripcion) { this.fechaInscripcion = fechaInscripcion; }

    public boolean isRecogido() { return recogido; }
    public void setRecogido(boolean recogido) { this.recogido = recogido; }

    @Override
    public String toString() {
        return "Inscripcion{" +
                "idInscripcion=" + idInscripcion +
                ", idCarrera=" + idCarrera +
                ", dorsal=" + dorsal +
                ", numTarjeta='" + tarjeta + '\'' +
                ", emailUsuario='" + email + '\'' +
                ", fechaInscripcion=" + fechaInscripcion +
                ", dorsalEsRecogido=" + recogido +
                '}';
    }
}
