package es.udc.isd060.runfic.model.util;

import es.udc.isd060.runfic.model.RunFicService.RunFicService;
import es.udc.isd060.runfic.model.RunFicService.RunFicServiceFactory;
import es.udc.isd060.runfic.model.carrera.Carrera;
import es.udc.isd060.runfic.model.inscripcion.Inscripcion;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.validation.PropertyValidator;

import java.time.LocalDateTime;

import static es.udc.isd060.runfic.model.util.ModelConstants.MAX_PRICE;

public class ModelPropertyValidator {

    //**************************************************************************************************
    //****************************************** Carlos *************************************************
    //**************************************************************************************************

    // Carlos
    private static final int DIFF_TEST_DAYS = 10;

    // Carlos
    // Necesario Duplicar código porque no carga de los test
    private static Carrera getValidCarrera(int seed) {
        LocalDateTime fechaCelebracion = LocalDateTime.now().plusDays(DIFF_TEST_DAYS);
        return new Carrera("VALID","seed="+seed,(seed%MAX_PRICE)*0.001f,fechaCelebracion, (int) (seed%MAX_PRICE));
    }



    // Carlos
    public static void validateNumTarjeta (String numTarjeta) throws InputValidationException {
        if (numTarjeta.length() != 16){
            throw new InputValidationException("Invalid Tarjeta");
        }
    }

    // Carlos
    public static void validateEmail (String email) throws InputValidationException{
        PropertyValidator.validateMandatoryString("email", email);
        if (!email.contains("@")) throw new InputValidationException("Non é un email valido");
    }

    // Carlos
    public static  boolean validateInscripcion(@org.jetbrains.annotations.NotNull Inscripcion inscripcion) throws InputValidationException {

        boolean valid = true;
        try {
            validateEmail(inscripcion.getEmail());
            validateNumTarjeta(inscripcion.getTarjeta());
        } catch ( InputValidationException e){
            throw new InputValidationException("La inscripcion "+inscripcion.toString()+
                    " no ha sido validada");
        }

        return valid;
    }


    // Carlos
    public static  boolean validateCarrera ( @org.jetbrains.annotations.NotNull Carrera carrera) throws InputValidationException {

        boolean isValid = true;

        isValid = (isValid | ( carrera.getCiudadCelebracion()!=null));
        isValid = (isValid | (carrera.getDescripcion()!=null));
        isValid = (isValid | (carrera.getPlazasDisponibles()>=0));
        isValid = (isValid | (carrera.getPlazasOcupadas()>=0));
        isValid = (isValid | ((carrera.getPlazasDisponibles())>=(carrera.getPlazasOcupadas())));
        isValid = (isValid | (carrera.getPrecioInscripcion()>=0));
        isValid = (isValid | (carrera.getPrecioInscripcion()<=ModelConstants.MAX_PRICE));


        if (!isValid) {
            throw new InputValidationException("La carrera "+carrera.toString()+
                    " no ha sido validada");
        }
        return isValid;
    }



}
