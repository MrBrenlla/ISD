package es.udc.isd060.runfic.model.RunFicService.exceptions;

public class SinPlazas extends Exception {
    public SinPlazas() {
        super("La carrera ya esta llena");
    }
}