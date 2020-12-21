package es.udc.isd060.runfic.model.RunFicService.exceptions;

public class NumTarjetaIncorrectoException extends Exception{
    public NumTarjetaIncorrectoException() {
        super("El numero de tarjeta no corresponde con el de la Inscripcion");
    }
}
