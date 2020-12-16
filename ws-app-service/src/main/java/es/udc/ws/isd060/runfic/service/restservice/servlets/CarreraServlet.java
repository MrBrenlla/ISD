package es.udc.ws.isd060.runfic.service.restservice.servlets;

import es.udc.isd060.runfic.model.carrera.Carrera;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class CarreraServlet {
    // TODO all

    // Nota LOS Métodos que se comentan por CF ( Called For) son llamados por la funcion correspondiente de la capa servicios
    // ( los que estan implementados son llamados por los comentados)
    // VER TEMA 6 ( DIAP. 45 )
    // un doXXX puede tener más de un CF
    // Aclaracion en \docs\DiagramaServlets.dia
    //**************************************************************************************************
    //****************************************** Yago *************************************************
    //**************************************************************************************************

    // CF : public Carrera addCarrera (Carrera carrera  );
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    //**************************************************************************************************
    //****************************************** Carlos y Yago *************************************************
    //**************************************************************************************************


    // CF : public List<Carrera> findCarrera (LocalDateTime fechaCelebracion , String nombreCiudad );
    // CF : public Carrera findCarrera ( Integer idCarrera );
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }







}
