package es.udc.isd060.runfic.model.inscripcion;

import es.udc.ws.util.exceptions.InstanceNotFoundException;

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




}
