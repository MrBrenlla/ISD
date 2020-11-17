package es.udc.isd060.runfic.test.model.runficservice;

import es.udc.isd060.runfic.model.RunFicService.RunFicService;
import es.udc.isd060.runfic.model.RunFicService.RunFicServiceFactory;
import es.udc.isd060.runfic.model.carrera.Carrera;
import es.udc.isd060.runfic.model.inscripcion.SqlInscripcionDao;
import es.udc.isd060.runfic.model.inscripcion.SqlInscripcionDaoFactory;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.sql.DataSourceLocator;
import es.udc.ws.util.sql.SimpleDataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static es.udc.isd060.runfic.model.util.ModelConstants.RUNFIC_DATA_SOURCE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RunFicServiceTest {

    private static RunFicService runFicService = null;
    private static SqlInscripcionDao inscripcionDao = null;

    @BeforeAll
    public static void init() {

        /*
         * Create a simple data source and add it to "DataSourceLocator" (this
         * is needed to test "es.udc.ws.isd060.model.runficservice.RunFicService"
         */
        DataSource dataSource = new SimpleDataSource();

        /* Add "dataSource" to "DataSourceLocator". */
        DataSourceLocator.addDataSource(RUNFIC_DATA_SOURCE, dataSource);

        runFicService = RunFicServiceFactory.getService();

        inscripcionDao = SqlInscripcionDaoFactory.getDao();

    }

    private Carrera getValidCarrera(String ciudadCelebracion) {
        LocalDateTime date1 = LocalDateTime.of(2017, 2, 13, 15, 56);
        return new Carrera(ciudadCelebracion,"Descripciioon",1.5f,date1,date1,0,100);
    }

    private Carrera getValidCarrera() {
        return getValidCarrera("Barcelona");
    }

    private Carrera createCarrera(Carrera carrera) {

        Carrera addedCarrera = null;
        try {
            addedCarrera = runFicService.addCarrera(carrera);
        } catch (InputValidationException e) {
            throw new RuntimeException(e);
        }
        return addedCarrera;
    }

    private void removeCarrera(Long idCarrera) {
        try {
            runFicService.removeCarrera(idCarrera);
        } catch (InstanceNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testAddCarrera() throws InputValidationException, InstanceNotFoundException {

        Carrera carrera = getValidCarrera();
        Carrera addedCarrera = null;

        try {
            System.out.println(getValidCarrera());
            addedCarrera = runFicService.addCarrera(getValidCarrera());
        } finally {
            // Clear Database
            //if (addedCarrera!=null) {
              //  removeCarrera(addedCarrera.getIdCarrera());
            //}
        }
    }

}
