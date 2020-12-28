package es.udc.ws.isd060.runfic.service.restservice.servlets.util;

import es.udc.ws.isd060.runfic.service.restservice.json.JsonToExceptionConversor;
import es.udc.ws.util.exceptions.InputValidationException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.PatternSyntaxException;

public class ServletUtils extends es.udc.ws.util.servlet.ServletUtils {


    private static final int MAX_SUBPATH_DEPTH = 2;


    // Servlet Subpath entities
    public final static int CARRERA_SUBPATH_TYPE=0;
    public final static int INSCRIPCION_SUBPATH_TYPE=1;

    public final static int SUBPATH_TYPE_NULL =-1;

    public final static String  WS_APP_SERVICE_SUBPATH = "ws-app-service";
    public final static String  CARRERA_SUBPATH = ServletUtils.WS_APP_SERVICE_SUBPATH+"/"+"Carrera";
    public final static String  INSCRIPCION_SUBPATH = ServletUtils.WS_APP_SERVICE_SUBPATH+"/"+"Inscripcion";


    // OVERLOADED post subpath
    public final static  Integer POST_SUBPATH_TYPE_NULL=-10;
    public final static  Integer POST_SUBPATH_TYPE_ADDINSCRIPCION=10;
    public final static  Integer POST_SUBPATH_TYPE_RECOGERDORSAL=11;

    public final static  String POST_SUBPATH_RECOGERDORSAL="Dorsal";

    // TODO cambiar "subpath" por "path"

/*
    // Determina el tipo de peticion dependiendo de el subpath
    //  (lo que queda luego de hacer { String path = ServletUtils.normalizePath(httpServletRequest.getPathInfo()); })
    public final static int determineSubpathType ( String subpath ){
        if ( subpath.equals(CARRERA_SUBPATH) ) {
            return CARRERA_SUBPATH_TYPE;
        }
        else if ( subpath.equals(INSCRIPCION_SUBPATH) ) {
            return INSCRIPCION_SUBPATH_TYPE;
        }
        else return NULL_SUBPATH_TYPE;
    }


    public final static int determinePostSubpathType( String subpath ){
        String[] splittedSubpath;
        try {
            splittedSubpath = subpath.split("/", MAX_SUBPATH_DEPTH);
        } catch (PatternSyntaxException e) {
            throw  new RuntimeException(e);
        }
        if ( splittedSubpath[0].equals( INSCRIPCION_SUBPATH_TYPE) ) {
            return determineSubpathTypePostInscripcion(subpath);
        } else if (  splittedSubpath[0].equals( CARRERA_SUBPATH_TYPE) ) {
            return determineSubpathTypePostCarrera(subpath);
        } else {
            return NULL_SUBPATH_TYPE;
        }
    }

 */

    // OJO FUNCION COMPLICADA
    // Validamos que los parámetros del subpath estén y sean válidos COMO MÍNIMO hasta cierta profundidad (subpathMax)
    public static void validateSubpath( String subpath , int subpathMax ){

        if ( subpathMax <= 0) {
            throw new RuntimeException();
        } else {


            String[] splittedSubpath;
            // "splitteamos" el subpath en subpathMax trozos
            try {
                splittedSubpath = subpath.split("/", subpathMax);
            } catch (PatternSyntaxException e) {
                throw new RuntimeException(e);
            }
            validateSubpathAux(splittedSubpath, subpath, subpathMax);
        }
    }


    // TODO ARREGLAR [/,//,///,etc.]
    // OJO FUNCION COMPLICADA
    // Validamos que los parámetros del subpath estén y sean válidos COMO MÍNIMO hasta cierta profundidad (subpathMax)
    public static void validateSubpathAux(String[] splittedSubpath , String subpath , int subpathMax ){

        if ( subpathMax != 0) {
            //Caso general

            //System.out.println(subpathMax);

            if (splittedSubpath[subpathMax-1] == null) {
                throw new RuntimeException();
            }
            if (splittedSubpath[subpathMax-1].equals("")) {
                throw new RuntimeException();
            }
            if (splittedSubpath[subpathMax-1].equals(" ")) {
                throw new RuntimeException();
            }

            ServletUtils.validateSubpathAux(splittedSubpath,subpath,subpathMax-1);

        } else {
            //  TODO Caso Base
        }

    }

    /*
    private static void validateSubpath( String subpath  ){
         validateSubpath(  subpath , MAX_SUBPATH_DEPTH );
    }
*/

    // Determinamos el tipo de operacion a realizar en el caso  de doPost de Carrera
    private static int determineSubpathTypePostCarrera(String subpath) {
        // TODO IMPLEMENTAR
        return SUBPATH_TYPE_NULL;
    }


    // OJO FUNCION COMPLICADA
    // Determinamos el tipo de operacion a realizar en el caso  de doPost de Inscripcion
    // CF : Inscripcion addInscripcion (String email , String numTarjeta , Carrera carrera );
    // CF : public Inscripcion recogerDorsal ( Integer codReserva , String numTarjeta );
    //TODO mover a InscripcionServletAlt
    public  static int determineSubpathTypePostInscripcion(String subpath) {

        // POST http://XXX/ws-runfic-service/inscripcion
        validateSubpath(subpath, 1);
        String[] splittedSubpath;
        try {
            splittedSubpath = subpath.split("/", MAX_SUBPATH_DEPTH);
        } catch (PatternSyntaxException e) {
            throw new RuntimeException(e);
        }
        try {
            validateSubpath(subpath, 2);
        } catch (RuntimeException e) {
            System.out.println(splittedSubpath[1]);
                if (ServletUtils.normalizePath(splittedSubpath[1]).equals(INSCRIPCION_SUBPATH+"/"+POST_SUBPATH_RECOGERDORSAL)) {
                    // POST http://XXX/ws-runfic-service/inscripcion/recogerdorsal
                    // CF : public Inscripcion recogerDorsal ( Integer codReserva , String numTarjeta );
                    // PARÁMETROS POR CUERPO DE LA PETICIÓN
                    return POST_SUBPATH_TYPE_RECOGERDORSAL;
                } else {
                    //System.out.println(splittedSubpath[1]);
                    // POST http://XXX/ws-runfic-service/inscripcion/XXX
                   if ( ServletUtils.normalizePath(splittedSubpath[1]).equals(INSCRIPCION_SUBPATH) ){
                        // POST http://XXX/ws-runfic-service/inscripcion
                       // POST http://XXX/ws-runfic-service/inscripcion/[NADA MAS]
                        // CF : Inscripcion addInscripcion (String email , String numTarjeta , Carrera carrera );
                        // PARÁMETROS POR CUERPO DE LA PETICIÓN
                        return POST_SUBPATH_TYPE_ADDINSCRIPCION;
                    } else {
                        // http://XXX/ws-runfic-service/inscripcion/XXX
                        // BAD REQUEST
                        // No Tiramos runtime Exception para que doPost envíe el BAD_REQUEST
                        return POST_SUBPATH_TYPE_NULL;
                    }

                }
            }
            // [else]
            // http://XXX/ws-runfic-service/inscripcion/XXX
            // BAD REQUEST
            // No Tiramos runtime Exception para que doPost envíe el BAD_REQUEST
            return POST_SUBPATH_TYPE_NULL;

        }


    public static boolean isDesiredPath(String givenPath, String desiredPath){

        // Normalizamos el path
        givenPath=ServletUtils.normalizePath(givenPath);
        // Comprobamos que el path no esté vacío
        if ( givenPath.length()==0) return false;
        //System.out.println(givenPath.substring(1, givenPath.length()));
        // Comprobamos que el path normalizado sea /carreras
        if (givenPath.substring(1, givenPath.length()).equals(desiredPath)){
            return true;
        }
        else return false;

    }

    public static  void writeCustomExceptionResponse(Exception exception, HttpServletResponse httpServletResponse , Integer httpServletResponseType
            , String exceptionTypeName) {
        try {
            es.udc.ws.util.servlet.ServletUtils.writeServiceResponse(httpServletResponse, httpServletResponseType,
                    JsonToExceptionConversor.toCustomException(exception,exceptionTypeName),null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void badRequestExceptionResponse( HttpServletResponse httpServletResponse ,
                                              Exception exception) throws IOException {
        es.udc.ws.util.servlet.ServletUtils.writeServiceResponse(httpServletResponse, HttpServletResponse.SC_BAD_REQUEST, JsonToExceptionConversor
                .toInputValidationException(new InputValidationException(exception.getMessage())), null);
    }



    }
