package es.udc.isd060.runfic.model.RunFicService.exceptions;

public class PlazoDeInscripcionYaTerminadoException extends Exception{
    public PlazoDeInscripcionYaTerminadoException() {
        super("El plazo de inscripcion de la carrera ya ha teminado ( la carrera es dentro de menos de 24h )");
    }
}
