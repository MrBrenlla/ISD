package es.udc.isd060.runfic.model.inscripcion;


import es.udc.ws.util.exceptions.InstanceNotFoundException;
import jdk.internal.reflect.FieldAccessor;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

public abstract class AbstractSqlInscripcionDao implements SqlInscripcionDao {

    protected AbstractSqlInscripcionDao() {
    }
    //**************************************************************************************************
    //****************************************** Brais *************************************************
    //**************************************************************************************************

    @override
    public List<Inscripcion> find(Connection connection, String email, Long idCarrera)
            throws InstanceNotFoundException {

        /* Create "queryString". */
        String queryString = "SELECT idInscripcion, idCarrera, dorsal, numTarjeta"
                + ", email, fechaInscripcion, recogido FROM Movie WHERE email = " + email;

        if (idCarrera != null) {
            queryString = queryString + " & idCarrera = ?";
            /* Fill "preparedStatement". */
            preparedStatement.setLong(1, idCarrera);
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Execute query. */
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                throw new InstanceNotFoundException(idCarrera, email,
                        Inscripcion.class.getName());
            }

            List<Inscripcion> inscripcions = new ArrayList<Inscripcion>();

            /* Get results. */
            while (resultSet.next()) {

                i = 1;
                Long idInscripcion = resultSet.getLong(i++);
                Long idCarrera = resultSet.getLong(i++);
                Integer dorsal = new Integer(resultSet.getInt(i++));
                String tarjeta = resultSet.getString(i++);
                String email = resultSet.getString(i++);
                LocalDateTime fechaIscripcion = resultSet.getTimestamp(i++).toLocalDateTime();
                boolean recogido = resultSet.getBoolean(i++);

                inscripcions.add(new Inscripcion(idInscripcion, idCarrera, dorsal, tarjeta, email, fechaIscripcion, recogido));

            }
            /* Return movie. */
            return inscripcions;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @override
    public List<Inscripcion> find(Connection connection , String email ) {
        return find(connection, email, NULL);
    }

    //**************************************************************************************************
    //********************************************* Yago ***********************************************
    //**************************************************************************************************



    //**************************************************************************************************
    //******************************************** Carlos **********************************************
    //**************************************************************************************************

    Inscripcion find(Connection connection, Long idInscripcion) throws InstanceNotFoundException {
        /* Create "queryString". */
        String queryString = "SELECT idInscripcion, idCarrera, dorsal, numTarjeta"
                + ", email, fechaInscripcion, recogido FROM Inscripcion WHERE idInscripcion = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {
            /* Fill "preparedStatement". */
            int i=1;
            preparedStatement.setLong(i, idInscripcion);

            /* Execute query. */
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                throw new InstanceNotFoundException(idInscripcion,
                        Inscripcion.class.getName());
            }

            /* Get results. */
            i = 1;
            idInscripcion = resultSet.getLong(i++);
            Long idCarrera = resultSet.getLong(i++);
            Integer dorsal = resultSet.getInt(i++);
            String  numTarjeta  = resultSet.getString(i++);
            String email = resultSet.getString(i++);
            LocalDateTime fechaInscripcion = fechaInscripcion.adjustInto(resultSet.getTimestamp(i++));
            boolean recogido = resultSet.getBoolean(i++);

            /* Return Inscripcion. */
            return new Inscripcion(idInscripcion,idCarrera,dorsal,numTarjeta,email,fechaInscripcion,recogido);


        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    void update(Connection connection , Inscripcion inscripcion) throws InstanceNotFoundException {

        /* Create "queryString". */
        String queryString = "UPDATE Inscripcion "+
                "SET idInscripcion = ? , IdCarrera = ? , dorsal = ? , numTarjeta = ? , email = ? , fechaInscripcion = ? , recogido = ?" +
                "WHERE idInscripcion = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setLong(i++,inscripcion.getIdInscripcion());
            preparedStatement.setLong(i++,inscripcion.getIdCarrera());
            preparedStatement.setInt(i++,inscripcion.getDorsal());
            preparedStatement.setString(i++,inscripcion.getTarjeta());
            preparedStatement.setString(i++, inscripcion.getEmail());
            Timestamp fechaInscripcion = fechaInscripcion.adjustInto(inscripcion.getFechaInscripcion());
            preparedStatement.setTimestamp(i++,fechaInscripcion);
            preparedStatement.setBoolean(i++,inscripcion.isRecogido());
            preparedStatement.setLong(i++,inscripcion.getIdInscripcion());

            /* Execute query. */
            int updatedRows = preparedStatement.executeUpdate();

            if (updatedRows == 0) {
                throw new InstanceNotFoundException(inscripcion.getIdInscripcion(),
                        Inscripcion.class.getName());
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }


}
