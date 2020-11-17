package es.udc.isd060.runfic.model.carrera;

import es.udc.isd060.runfic.model.inscripcion.Inscripcion;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

public class Jdbc3CcSqlCarreraDao extends AbstractSqlCarreraDao {

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
    @Override
    public Carrera create(Connection connection, Carrera carrera) {
        /* Create "queryString". */
        String queryString = "INSERT INTO Carrera"
                + " ( ciudadCelebracion, descripcion, precioInscripcion, fechaAlta, fechaCelebracion, plazasDisponibles, plazasOcupadas )"
                + " VALUES ( ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                queryString, Statement.RETURN_GENERATED_KEYS)) {
            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setString(i++,carrera.getCiudadCelebracion());
            preparedStatement.setString(i++, carrera.getDescripcion());
            preparedStatement.setFloat(i++,carrera.getPrecioInscripcion());
            preparedStatement.setTimestamp(i++, Timestamp.valueOf(carrera.getFechaAlta()));
            preparedStatement.setTimestamp(i++, Timestamp.valueOf(carrera.getFechaCelebracion()));
            preparedStatement.setInt(i++,carrera.getPlazasDisponibles());
            preparedStatement.setInt(i++,carrera.getPlazasOcupadas());

            /* Execute query. */
            preparedStatement.executeUpdate();

            /* Get generated identifier. */
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (!resultSet.next()) {
                throw new SQLException(
                        "JDBC driver did not return generated key.");
            }

            Long idCarrera = resultSet.getLong(1);

            /* Return Carrera */
            return new Carrera(idCarrera,carrera);

        } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    }

}
