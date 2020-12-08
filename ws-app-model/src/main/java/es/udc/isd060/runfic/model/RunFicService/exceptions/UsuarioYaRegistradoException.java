package es.udc.isd060.runfic.model.RunFicService.exceptions;

public class UsuarioYaRegistradoException extends Exception{
    public UsuarioYaRegistradoException() {
        super("El usuario ya ha sido registrado");
    }
}
