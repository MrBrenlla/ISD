package es.udc.isd060.runfic.model.RunFicService.exceptions;

public class UsuarioInscrito extends Exception {
    public UsuarioInscrito() {
        super("El usuario ya está inscrito en esta carrera");
    }
}
