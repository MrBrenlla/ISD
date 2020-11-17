package es.udc.isd060.runfic.model.carrera;

import es.udc.isd060.runfic.model.inscripcion.Inscripcion;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;

public abstract class AbstractSqlCarreraDao implements SqlCarreraDao {

    protected AbstractSqlCarreraDao() {
    }

    //**************************************************************************************************
    //****************************************** Brais *************************************************
    //**************************************************************************************************

    @Override
    public boolean update(Connection connection, Long idCarrera) throws InstanceNotFoundException {

        Carrera c = find(connection , idCarrera);

        String queryString = "UPDATE Carrera"
                + " SET plazasOcupadas = " + (c.getPlazasOcupadas()+1)
                + " WHERE idCarrera = " + idCarrera;

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Execute query. */
            return 1 == preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    //**************************************************************************************************
    //********************************************* Yago ***********************************************
    //**************************************************************************************************



    //**************************************************************************************************
    //******************************************** Carlos **********************************************
    //**************************************************************************************************
    @Override
    public Carrera find(Connection connection, Long idCarrera) throws InstanceNotFoundException{
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
            i=1;
            idCarrera = resultSet.getLong(i++);
            String ciudadCelebracion = resultSet.getString(i++);
            String descripcion = resultSet.getString(i++);
            Float precioInscripcion = resultSet.getFloat(i++);
            Timestamp fechaAltaAsTimestamp=resultSet.getTimestamp(i++);
            LocalDateTime fechaAlta = fechaAltaAsTimestamp.toLocalDateTime();
            Timestamp fechaCelebracionAsTimestamp=resultSet.getTimestamp(i++);
            LocalDateTime fechaCelebracion = fechaAltaAsTimestamp.toLocalDateTime();
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
