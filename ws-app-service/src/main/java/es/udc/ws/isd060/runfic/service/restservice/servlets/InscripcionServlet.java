package es.udc.ws.isd060.runfic.service.restservice.servlets;

import es.udc.isd060.runfic.model.carrera.Carrera;
import es.udc.isd060.runfic.model.inscripcion.Inscripcion;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class InscripcionServlet {
    // TODO all

    // Nota LOS Métodos que se comentan por CF ( Called For) son llamados por la funcion correspondiente de la capa servicios
    // ( los que estan implementados son llamados por los comentados)
    // VER TEMA 6 ( DIAP. 45 )


    //**************************************************************************************************
    //****************************************** Brais *************************************************
    //**************************************************************************************************

    // CF : List<Inscripcion > findInscripcion (String email );
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }


    //**************************************************************************************************
    //****************************************** Carlos y Brais *************************************************
    //**************************************************************************************************


    // CF : Inscripcion addInscripcion (String email , String numTarjeta , Carrera carrera );
    // CF : public Inscripcion recogerDorsal ( Integer codReserva , String numTarjeta );
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }






}
