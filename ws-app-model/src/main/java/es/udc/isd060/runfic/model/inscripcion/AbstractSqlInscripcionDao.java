package es.udc.isd060.runfic.model.inscripcion;


import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public abstract class AbstractSqlInscripcionDao implements SqlInscripcionDao {

    protected AbstractSqlInscripcionDao() {
    }
    //**************************************************************************************************
    //****************************************** Brais *************************************************
    //**************************************************************************************************
    @Override
    public List<Inscripcion> find(Connection connection, String email, Long idCarrera) {

        /* Create "queryString". */
        String queryString = "SELECT idInscripcion, idCarrera, dorsal, numTarjeta"
                + ", email, fechaInscripcion, recogido FROM Inscripcion WHERE email = ?" ;

        if (idCarrera != null)  queryString = queryString + " & idCarrera = ?";


        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            preparedStatement.setString(1, email);
            if (idCarrera != null) preparedStatement.setLong(2, idCarrera);
            /* Execute query. */
            ResultSet resultSet = preparedStatement.executeQuery();


            List<Inscripcion> inscripcions = new ArrayList<>();

            int i;

            /* Get results. */
            while (resultSet.next()) {

                i = 1;
                Long idInscripcion = resultSet.getLong(i++);
                Long id = resultSet.getLong(i++);
                Integer dorsal = resultSet.getInt(i++);
                String tarjeta = resultSet.getString(i++);
                String mail = resultSet.getString(i++);
                LocalDateTime fechaIscripcion = resultSet.getTimestamp(i++).toLocalDateTime();
                boolean recogido = resultSet.getBoolean(i);

                inscripcions.add(new Inscripcion(idInscripcion, id, dorsal, tarjeta, mail, fechaIscripcion, recogido));

            }
            /* Return movie. */
            return inscripcions;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Inscripcion> find(Connection connection , String email ){
        return find(connection, email, null);
    }


    //**************************************************************************************************
    //********************************************* Yago ***********************************************
    //**************************************************************************************************
    @Override
    public void remove (Connection connection , Long idInscripcion) throws InstanceNotFoundException {

        /* Create "queryString". */

        String queryString = "DELETE FROM Inscripcion  WHERE" + " idInscripcion  = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setLong(i, idInscripcion);

            /* Execute query. */
            int removedRows = preparedStatement.executeUpdate();

            if (removedRows == 0) {
                throw new InstanceNotFoundException(idInscripcion, Inscripcion.class.getName());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    //**************************************************************************************************
    //******************************************** Carlos **********************************************
    //**************************************************************************************************

    public Inscripcion find(Connection connection, Long idInscripcion) throws InstanceNotFoundException {
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
            Timestamp fechaInscripcionAsTimestamp=resultSet.getTimestamp(i++);
            LocalDateTime fechaInscripcion = fechaInscripcionAsTimestamp.toLocalDateTime();
            boolean recogido = resultSet.getBoolean(i);

            /* Return Inscripcion. */
            return new Inscripcion(idInscripcion,idCarrera,dorsal,numTarjeta,email,fechaInscripcion,recogido);


        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public void update(Connection connection, Inscripcion inscripcion) throws InstanceNotFoundException {

        /* Create "queryString". */
        String queryString = "UPDATE Inscripcion "+
                "SET idInscripcion = ? , IdCarrera = ? , dorsal = ? , numTarjeta = ? , email = ? , fechaInscripcion = ? , recogido = ?" +
                "WHERE idInscripcion = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setLong(i++,inscripcion.getIdInscripcion());
            Long idCarrera = inscripcion.getIdCarrera();
            preparedStatement.setLong(i++,idCarrera);
            preparedStatement.setInt(i++,inscripcion.getDorsal());
            preparedStatement.setString(i++,inscripcion.getTarjeta());
            preparedStatement.setString(i++, inscripcion.getEmail());
            preparedStatement.setTimestamp(i++,Timestamp.valueOf(inscripcion.getFechaInscripcion()));
            preparedStatement.setBoolean(i++,inscripcion.isRecogido());
            preparedStatement.setLong(i,inscripcion.getIdInscripcion());

            /* Execute query. */
            int updatedRows = preparedStatement.executeUpdate();

            if (updatedRows == 0) {
                throw new InstanceNotFoundException(inscripcion.getIdInscripcion(),
                        Inscripcion.class.getName());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


}
