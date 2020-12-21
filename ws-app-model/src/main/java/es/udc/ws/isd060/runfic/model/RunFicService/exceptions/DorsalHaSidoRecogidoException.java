package es.udc.isd060.runfic.model.RunFicService.exceptions;

public class DorsalHaSidoRecogidoException extends Exception {
    public DorsalHaSidoRecogidoException() {
        super("El dorsal ya ha sido recogido");
    }
}
