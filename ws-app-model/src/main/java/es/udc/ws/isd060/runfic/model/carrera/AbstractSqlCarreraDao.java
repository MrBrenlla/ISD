package es.udc.ws.isd060.runfic.model.carrera;

import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public abstract class AbstractSqlCarreraDao implements SqlCarreraDao {

    protected AbstractSqlCarreraDao() { }

    //**************************************************************************************************
    //****************************************** Brais *************************************************
    //**************************************************************************************************
    @Override
    public void update(Connection connection, Long idCarrera) throws InstanceNotFoundException {

        Carrera c = find(connection , idCarrera);

        String queryString = "UPDATE Carrera"
                + " SET plazasOcupadas = " + (c.getPlazasOcupadas()+1)
                + " WHERE idCarrera = " + idCarrera;

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Execute query. */
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    //**************************************************************************************************
    //********************************************* Yago ***********************************************
    //**************************************************************************************************
    @Override
    public List<Carrera> find(Connection connection, LocalDateTime fechaCelebracion, String ciudad) {

        /* Create "queryString". */
        String queryString = "SELECT idCarrera, ciudadCelebracion, descripcion, precioInscripcion, fechaAlta, " +
                "fechaCelebracion, plazasDisponibles, plazasOcupadas FROM Carrera WHERE fechaCelebracion < ? AND fechaCelebracion > NOW() ";

        if(ciudad != null ) {
            queryString += " AND ciudadCelebracion = ?";
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)){

            int count = 1;
            preparedStatement.setTimestamp(count++,
                    Timestamp.valueOf(fechaCelebracion));

            if(ciudad != null ) {
                preparedStatement.setString(count,ciudad);
            }

            /* Execute query. */
            ResultSet resultSet = preparedStatement.executeQuery();

            /* Read movies. */
            List<Carrera> carreras = new ArrayList<>();

            while (resultSet.next()) {
                int i = 1;
                Long idCarrera = resultSet.getLong(i++);
                String ciudadCelebracion = resultSet.getString(i++);
                String descripcion = resultSet.getString(i++);
                Float precioInscripcion = resultSet.getFloat(i++);
                Timestamp fechaAltaAsTimestamp = resultSet.getTimestamp(i++);
                LocalDateTime fechaAlta = fechaAltaAsTimestamp.toLocalDateTime();
                Timestamp fechaCelebracion_AsTimestamp = resultSet.getTimestamp(i++);
                LocalDateTime fechaCelebracion_ = fechaCelebracion_AsTimestamp.toLocalDateTime();
                Integer plazasDisponibles = resultSet.getInt(i++);
                Integer plazasOcupadas = resultSet.getInt(i);

                carreras.add(new Carrera(idCarrera, ciudadCelebracion, descripcion, precioInscripcion, fechaAlta, fechaCelebracion_, plazasDisponibles, plazasOcupadas));
            }

            /* Return carreras. */
            return carreras;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void remove(Connection connection, Long idCarrera) throws InstanceNotFoundException {
        /* Create "queryString". */

        String queryString = "DELETE FROM Carrera WHERE" + " idCarrera = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setLong(i, idCarrera);

            /* Execute query. */
            int removedRows = preparedStatement.executeUpdate();

            if (removedRows == 0) {
                throw new InstanceNotFoundException(idCarrera, Carrera.class.getName());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


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
            preparedStatement.setLong(i, idCarrera);

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
            LocalDateTime fechaCelebracion = fechaCelebracionAsTimestamp.toLocalDateTime();
            Integer plazasDisponibles = resultSet.getInt(i++);
            Integer plazasOcupadas = resultSet.getInt(i);

            /* Return Inscripcion. */
            return new Carrera(idCarrera,ciudadCelebracion,descripcion,precioInscripcion,fechaAlta,
                    fechaCelebracion,plazasDisponibles,plazasOcupadas);


        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


}
