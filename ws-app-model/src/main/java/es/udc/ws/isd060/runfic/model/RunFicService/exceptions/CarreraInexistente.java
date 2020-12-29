package es.udc.ws.isd060.runfic.model.RunFicService.exceptions;

public class CarreraInexistente extends Exception {
    public CarreraInexistente() {
        super("La carrera no existe");
    }
}
