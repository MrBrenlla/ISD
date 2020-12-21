package es.udc.isd060.runfic.model.util;

public class ErrorConstants {

    //**************************************************************************************************
    //****************************************** Carlos *************************************************
    //**************************************************************************************************


    public static final String ERR_ADDINSCRIPCION_EMAILTARJETAIDCARRERA= "Inscripcion addInscripcion(String email, String tarjeta, long carrera) throws InputValidationException, CarreraInexistente,UsuarioInscrito,FueraDePlazo,SinPlazas;";
    public static final String ERR_FINDINSCRIPCION_EMAIL = "List<Inscripcion> findInscripcion(String email);";
    public static final String ERR_ADDCARRERA_CARRERA = "Carrera addCarrera(Carrera carrera) throws InputValidationException;";
    public static final String ERR_FINDCARRERA_FECHACELEBRACION = "List<Carrera> findCarrera(LocalDateTime fechaCelebracion);";
    public static final String ERR_FINDCARRERA_FECHACELEBRACION_CIUDAD = "List<Carrera> findCarrera(LocalDateTime fechaCelebracion, String ciudad);";
    public static final String ERR_FINDCARRERA_IDCARRERA = "Carrera findCarrera(Long idCarrera) throws InstanceNotFoundException;";
    public static final String ERR_RECOGERDORSAL_IDINSCRIPCION_NUMTARJETA = "Inscripcion recogerDorsal(Long idInscripcion, String numTarjeta) throws InstanceNotFoundException, DorsalHaSidoRecogidoException, NumTarjetaIncorrectoException, CarreraYaCelebradaException, InputValidationException;";


}
