package es.udc.isd060.runfic.model.carrera;

import es.udc.isd060.runfic.model.inscripcion.Inscripcion;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public abstract class AbstractSqlCarreraDao implements SqlCarreraDao {

    protected AbstractSqlCarreraDao() {
    }

    //**************************************************************************************************
    //****************************************** Brais *************************************************
    //**************************************************************************************************


    //**************************************************************************************************
    //********************************************* Yago ***********************************************
    //**************************************************************************************************



    //**************************************************************************************************
    //******************************************** Carlos **********************************************
    //**************************************************************************************************
/*
 private Long idCarrera;
    private String ciudadCelebracion;
    private String descripcion;
    private Float precioInscripcion;
    private LocalDateTime fechaAlta;
    private LocalDateTime fechaCelebracion;
    private Integer plazasDisponibles;
    private Integer plazasOcupadas;

 */
    Carrera find (Connection connection , Long idCarrera) throws InstanceNotFoundException {
        /* Create "queryString". */
        String queryString = "SELECT idCarrera, ciudadCelebracion, descripcion"
                + ", precioInscripcion, fechaAlta, fechaCelebracion ,plazasDisponibles , plazasOcupadas "
        + "FROM carrera  WHERE idCarrera = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setLong(i++, idCarrera);

            /* Execute query. */
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                throw new InstanceNotFoundException(idCarrera,
                        Carrera.class.getName());
            }

            /* Get results. */
            int i=1;
            idCarrera = resultSet.getLong(i++);
            String ciudadCelebracion = resultSet.getString(i++);
            String descripcion = resultSet.getString(i++);
            Float precioInscripcion = resultSet.getFloat(i++);
            LocalDateTime fechaAlta = fechaAlta.adjustInto(resultSet.getTimestamp(i++));
            LocalDateTime fechaCelebracion = fechaCelebracion.adjustInto(resultSet.getTimestamp(i++));
            Integer plazasDisponibles = resultSet.getInt(i++);
            Integer plazasOcupadas = resultSet.getInt(i++);

            /* Return Inscripcion. */
            return new Carrera(idCarrera,ciudadCelebracion,descripcion,precioInscripcion,fechaAlta,
                    fechaCelebracion,plazasDisponibles,plazasOcupadas);


        }catch (SQLException e) {
            throw new RuntimeException(e);
        }







    }


}
