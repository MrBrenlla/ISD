package es.udc.isd060.runfic.model.carrera;

import es.udc.isd060.runfic.model.inscripcion.Inscripcion;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class Jdbc3CcSqlCarreraDao extends AbstractSqlCarreraDao {

    //**************************************************************************************************
    //****************************************** Brais *************************************************
    //**************************************************************************************************


    @Override
    public boolean update(Connection connection, Long idCarrera) {

        Carrera c = self.find(connection , idCarrera);

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


}
