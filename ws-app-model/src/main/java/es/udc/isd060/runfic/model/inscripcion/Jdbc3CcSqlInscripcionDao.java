package es.udc.isd060.runfic.model.inscripcion;

public class Jdbc3CcSqlInscripcionDao extends AbstractSqlInscripcionDao{
    public Inscripcion create(Connection connection, Inscripcion inscripcion) {

        /* Create "queryString". */
        String queryString = "INSERT INTO Inscripcion"
                + " (idCarrera, dorsal, numTarjeta, email, fechaInscripcion, recogido)"
                + " VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                queryString, Statement.RETURN_GENERATED_KEYS)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setLong(i++, inscripcion.getIdCarrera());
            preparedStatement.setInt(i++, inscripcion.getDorsal().intValue());
            preparedStatement.setString(i++,inscripcion.getTarjeta());
            preparedStatement.setString(i++, inscripcion.getEmail());
            preparedStatement.setTimestamp(i++, Timestamp.valueOf(inscripcion.getFechaInscripcion()));
            preparedStatement.setBoolean(i, false);

            /* Execute query. */
            preparedStatement.executeUpdate();

            /* Get generated identifier. */
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (!resultSet.next()) {
                throw new SQLException(
                        "JDBC driver did not return generated key.");
            }
            Long idInscripcion = resultSet.getLong(1);

            /* Return Inscripcion. */
            return new Inscripcion(idInscripcion, inscripcion.getIdCarrera(), inscripcion.getDorsal().intValue(),
                    inscripcion.getTarjeta(), inscripcion.getEmail(),inscripcion.getFechaInscripcion(),
                    false);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
