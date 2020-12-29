package es.udc.ws.isd060.runfic.model.RunFicService.exceptions;

public class CarreraYaCelebradaException extends Exception {
    public CarreraYaCelebradaException() {
        super("La carrera ya ha sido celebrada");
    }
}
