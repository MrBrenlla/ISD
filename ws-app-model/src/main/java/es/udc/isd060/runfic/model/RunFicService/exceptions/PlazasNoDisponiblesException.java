package es.udc.isd060.runfic.model.RunFicService.exceptions;

public class PlazasNoDisponiblesException extends  Exception{
    public PlazasNoDisponiblesException() {
        super("El numero máximo de participantes ha sido alcanzado");
    }
}
