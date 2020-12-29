package es.udc.ws.isd060.runfic.model.inscripcion;


import es.udc.ws.isd060.runfic.model.carrera.Carrera;
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

    private List<Inscripcion> auxfind(Connection connection, String email, Long idCarrera) {

        /* Create "queryString". */
        String queryString = "SELECT idInscripcion, idCarrera, dorsal, numTarjeta"
                + ", email, fechaInscripcion, recogido FROM Inscripcion WHERE email = ?" ;

        if (idCarrera != null)  queryString = queryString + " AND idCarrera = ?";


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
            System.out.println(inscripcions.toString());
            return inscripcions;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean find(Connection connection, String email, Long idCarrera){
       return ! (auxfind(connection,email,idCarrera).isEmpty());
    }

    @Override
    public List<Inscripcion> find(Connection connection , String email ){
        return auxfind(connection, email, null);
    }


    //**************************************************************************************************
    //********************************************* Yago ***********************************************
    //**************************************************************************************************
    @Override
    public void remove(Connection connection, Long idInscripcion) throws InstanceNotFoundException {

        /* Create "queryString". */
        String queryString = "DELETE FROM Inscripcion WHERE" + " idInscripcion = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setLong(i++, idInscripcion);

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

    /*
    // FIND alternativo para email y Carrera
    public Inscripcion findAlt(Connection connection, String email, Carrera carrera) throws InstanceNotFoundException {

        Inscripcion inscripcion = null;

        // Create "queryString".
        String queryString = "SELECT idInscripcion, idCarrera, dorsal, numTarjeta"
                + ", email, fechaInscripcion, recogido FROM Inscripcion" +
                " WHERE email = ? && idCarrera=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            // Fill "preparedStatement".
            int i=1;
            preparedStatement.setString(i++,email);
            preparedStatement.setLong(i++,carrera.getIdCarrera());

            // Execute query.
            ResultSet resultSet = preparedStatement.executeQuery();

            // Get from "resultSet"
            if (!resultSet.next()) {
                throw new InstanceNotFoundException(carrera.getIdCarrera(),
                        Inscripcion.class.getName());
            }
            i=1;
            Long idInscripcion = resultSet.getLong(i++);
            Long idCarrera = resultSet.getLong(i++);
            Integer dorsal = resultSet.getInt(i++);
            String numTarjeta = resultSet.getString(i++);
            String mail = resultSet.getString(i++);
            Timestamp fechaInscripcionAsTimestamp=resultSet.getTimestamp(i++);
            LocalDateTime fechaInscripcion = fechaInscripcionAsTimestamp.toLocalDateTime();
            boolean recogido = resultSet.getBoolean(i++);

            inscripcion = new Inscripcion(idInscripcion,idCarrera,dorsal,numTarjeta,
                    mail,fechaInscripcion,recogido);

            if (resultSet.next()) {
                throw new RuntimeException();
            }

        } catch (SQLException e) {

        }

        return inscripcion;

    }
*/
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
            Timestamp fechaInscripcionAsTimestamp = resultSet.getTimestamp(i++);
            LocalDateTime fechaInscripcion = fechaInscripcionAsTimestamp.toLocalDateTime();
            boolean recogido = resultSet.getBoolean(i);

            /* Return Inscripcion. */
            return new Inscripcion(idInscripcion,idCarrera,dorsal,numTarjeta,email,fechaInscripcion,recogido);


        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void update(Connection connection, Inscripcion inscripcion)
            throws InstanceNotFoundException {



        // Create "queryString".
        // TODO JOIN? 
        // Nuesta implementación es genérica -> debemos incluirlo
        // TODO fechaInscripcion
        /*String queryString = "UPDATE inscripcion"
                + " SET idCarrera = ? ,dorsal  = ?, numTarjeta = ? , fechaInscripcion = ? , email = ? ,  recogido  = ?"
                + " WHERE idInscripcion  = ?";
         */
        String queryString = "UPDATE inscripcion "+
                 " SET idCarrera = ? ,dorsal  = ?, numTarjeta = ? , email = ? ,  recogido  = ?"
                + " WHERE idInscripcion  = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {


            // Fill "preparedStatement".
            int i = 1;
            preparedStatement.setLong(i++, inscripcion.getIdCarrera());
            preparedStatement.setInt(i++, inscripcion.getDorsal());
            preparedStatement.setString(i++,inscripcion.getTarjeta());
            preparedStatement.setString(i++,inscripcion.getEmail());
            preparedStatement.setBoolean(i++, inscripcion.isRecogido());

            preparedStatement.setLong(i++, inscripcion.getIdInscripcion());


            // Execute query.
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
