package es.udc.isd060.runfic.test.model.runficservice;

import es.udc.isd060.runfic.model.RunFicService.RunFicService;
import es.udc.isd060.runfic.model.RunFicService.RunFicServiceFactory;
import es.udc.isd060.runfic.model.RunFicService.exceptions.CarreraYaCelebradaException;
import es.udc.isd060.runfic.model.RunFicService.exceptions.DorsalHaSidoRecogidoException;
import es.udc.isd060.runfic.model.RunFicService.exceptions.NumTarjetaIncorrectoException;
import es.udc.isd060.runfic.model.carrera.Carrera;
import es.udc.isd060.runfic.model.carrera.SqlCarreraDao;
import es.udc.isd060.runfic.model.carrera.SqlCarreraDaoFactory;
import es.udc.isd060.runfic.model.inscripcion.Inscripcion;
import es.udc.isd060.runfic.model.inscripcion.SqlInscripcionDao;
import es.udc.isd060.runfic.model.inscripcion.SqlInscripcionDaoFactory;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.sql.DataSourceLocator;
import es.udc.ws.util.sql.SimpleDataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static es.udc.isd060.runfic.model.util.ModelConstants.MAX_PRICE;
import static es.udc.isd060.runfic.model.util.ModelConstants.RUNFIC_DATA_SOURCE;
import static org.junit.jupiter.api.Assertions.*;

public class RunFicServiceTest {
    private static DataSource dataSource = null;
    private static RunFicService runFicService = null;
    private static SqlCarreraDao carreraDao = null;
    private static SqlInscripcionDao inscripcionDao = null;
    private static LocalDateTime hoxe = LocalDateTime.of(2020, 11, 17, 19, 8);

    private final long NON_EXISTENT_CARRERA_ID = -1;

    @BeforeAll
    public static void init() {

        /*
         * Create a simple data source and add it to "DataSourceLocator" (this
         * is needed to test "es.udc.ws.isd060.model.runficservice.RunFicService"
         */
        dataSource = new SimpleDataSource();

        /* Add "dataSource" to "DataSourceLocator". */
        DataSourceLocator.addDataSource(RUNFIC_DATA_SOURCE, dataSource);

        runFicService = RunFicServiceFactory.getService();
        carreraDao = SqlCarreraDaoFactory.getDao();
        inscripcionDao = SqlInscripcionDaoFactory.getDao();

    }

    private String getValidTarjeta(){
        return "1234567812345678";
    }

    private String getValidEmail(){
        return "test@udc.es";
    }

    private Carrera getValidCarrera(String ciudadCelebracion) {
        LocalDateTime date1 = LocalDateTime.of(2021, 2, 13, 15, 30);
        return new Carrera(null,ciudadCelebracion,"Descripcion", 5.5f,LocalDateTime.now(),date1,0,100);
    }

    private Carrera getValidCarrera_2() {
        LocalDateTime date1 = LocalDateTime.of(2022, 5, 11, 19, 30);
        return new Carrera(null,"Mallorca","Descripcion", 2.5f,LocalDateTime.now(),date1,0,120);
    }


    private Carrera getValidCarrera() {
        return getValidCarrera("Barcelona");
    }

    private Carrera getValidCarrera( LocalDateTime fechaCelebracion){
        return new Carrera("test", "descripcion", 1.0f , LocalDateTime.now(),
                fechaCelebracion, 100 , 1);
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

    private void removeCarrera(Carrera carrera) {
        try (Connection connection = dataSource.getConnection()) {

            carreraDao.remove(connection, carrera.getIdCarrera());
            /* Commit. */
            connection.commit();


        } catch (SQLException | InstanceNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private Inscripcion getValidInscripcion(Long idCarrera) {
        LocalDateTime date1 = LocalDateTime.of(2021, 2, 13, 15, 30);
        return new Inscripcion(idCarrera,2,"0","pepito@gmail.com",date1,false);
    }

    private Inscripcion getValidInscripcion() {
        return getValidInscripcion(1L);
    }

    private Inscripcion createInscripcion(Inscripcion inscripcion) {

        Inscripcion addedInscripcion = null;
        try {
            addedInscripcion = runFicService.addInscripcion(inscripcion.getEmail(),inscripcion.getEmail(),inscripcion.getIdCarrera());
        } catch (InputValidationException e) {
            throw new RuntimeException(e);
        }
        return addedInscripcion;
    }

    private void removeInscripcion(Inscripcion inscripcion) {
        try (Connection connection = dataSource.getConnection()) {

            inscripcionDao.remove(connection, inscripcion.getIdInscripcion());
            /* Commit. */
            connection.commit();


        } catch (SQLException | InstanceNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }



    //**************************************************************************************************
    //********************************************* Yago ***********************************************
    //**************************************************************************************************

    @Test
    public void testAddCarrera() throws InputValidationException, InstanceNotFoundException {

        Carrera carrera = getValidCarrera();
        Carrera addedCarrera = null;

        try {
            System.out.println(getValidCarrera());
            addedCarrera = runFicService.addCarrera(carrera);
            Carrera foundCarrera = runFicService.findCarrera(addedCarrera.getIdCarrera());
            assertEquals(addedCarrera.getIdCarrera(), foundCarrera.getIdCarrera());
        } finally {
            // Clear Database
            if (addedCarrera!=null) {
                removeCarrera(addedCarrera);
            }
        }
    }

    @Test
    public void testRemoveCarrera() throws InstanceNotFoundException {

        Carrera carrera = createCarrera(getValidCarrera());

        runFicService.removeCarrera(carrera.getIdCarrera());

        assertThrows(InstanceNotFoundException.class, () -> runFicService.findCarrera(carrera.getIdCarrera()));

    }

    @Test
    public void testRemoveNonExistentCarrera() {
        assertThrows(InstanceNotFoundException.class, () -> runFicService.removeCarrera(NON_EXISTENT_CARRERA_ID));
    }


    @Test
    public void testFindByDateAndCity() throws InputValidationException, SQLException {
        Carrera carrera1 = runFicService.addCarrera(getValidCarrera());
        Carrera carrera2 = runFicService.addCarrera(getValidCarrera_2());
        LocalDateTime fecha = LocalDateTime.of(2020, 1, 1, 19, 8);

        try {
            List<Carrera> carreras = runFicService.findCarrera(fecha, "Mallorca");
            assertEquals(carrera2.getIdCarrera(), carreras.get(carreras.size() - 1).getIdCarrera());
        }finally {
            removeCarrera(carrera1);
            removeCarrera(carrera2);
        }
    }

    @Test
    public void testAddInvalidMovie() {

        // Check carrera ciudad not null
        assertThrows(InputValidationException.class, () -> {
            Carrera carrera = getValidCarrera();
            carrera.setCiudadCelebracion(null);
            Carrera addedCarrera = runFicService.addCarrera(carrera);
            removeCarrera(addedCarrera);
        });

        // Check carrera ciudad not empty
        assertThrows(InputValidationException.class, () -> {
            Carrera carrera = getValidCarrera();
            carrera.setCiudadCelebracion("");
            Carrera addedCarrera = runFicService.addCarrera(carrera);
            removeCarrera(addedCarrera);
        });

        // Check carrera descripcion not null
        assertThrows(InputValidationException.class, () -> {
            Carrera carrera = getValidCarrera();
            carrera.setDescripcion(null);
            Carrera addedCarrera = runFicService.addCarrera(carrera);
            removeCarrera(addedCarrera);
        });

        // Check carrera descripcion not empty
        assertThrows(InputValidationException.class, () -> {
            Carrera carrera = getValidCarrera();
            carrera.setDescripcion("");
            Carrera addedCarrera = runFicService.addCarrera(carrera);
            removeCarrera(addedCarrera);
        });

        // Check carrera precioInscripcion >= 0
        assertThrows(InputValidationException.class, () -> {
            Carrera carrera = getValidCarrera();
            carrera.setPrecioInscripcion((float) -1);
            Carrera addedCarrera = runFicService.addCarrera(carrera);
            removeCarrera(addedCarrera);
        });

        // Check carrera plazasDisponibles >= 0
        assertThrows(InputValidationException.class, () -> {
            Carrera carrera = getValidCarrera();
            carrera.setPlazasDisponibles(-1);
            Carrera addedCarrera = runFicService.addCarrera(carrera);
            removeCarrera(addedCarrera);
        });

        // Check carrera plazasOcupadas >= 0
        assertThrows(InputValidationException.class, () -> {
            Carrera carrera = getValidCarrera();
            carrera.setPlazasOcupadas(-1);
            Carrera addedCarrera = runFicService.addCarrera(carrera);
            removeCarrera(addedCarrera);
        });


    }

    @Test
    public void testRemoveInscripcion() throws InstanceNotFoundException {

        Inscripcion inscripcion = createInscripcion(getValidInscripcion());

        removeInscripcion(inscripcion);

        assertTrue(runFicService.findInscripcion(inscripcion.getEmail()).isEmpty());

    }

    @Test
    public void testRemoveNonExistentInscripcion() {
        assertThrows(InstanceNotFoundException.class, () -> runFicService.removeCarrera(NON_EXISTENT_CARRERA_ID));
    }




    //**************************************************************************************************
    //******************************************** Carlos **********************************************
    //**************************************************************************************************


    @Test
    public void testFindCarreraExistenteByIdCarrera()  {
        Carrera carrera = getValidCarrera();
        try {
            carrera = runFicService.addCarrera(carrera);
            runFicService.findCarrera(carrera.getIdCarrera());

        }catch (InputValidationException | InstanceNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (carrera!=null) removeCarrera(carrera);
        }
    }

/*
    @Test(expected = InputValidationException.class)
    public void testFindCarreraInexistenteByIdCarrera()  {
        Carrera carrera = getValidCarrera("Insertada");
        try {
            carrera = runFicService.addCarrera(carrera);
            runFicService.findCarrera(carrerrecogerDorsala.getIdCarrera()+1);

        }catch ( InstanceNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (carrera!=null) removeCarrera(carrera);
        }
    }
*/

    @Test
    public void testRecogerDorsalValido(){
        Carrera carrera = getValidCarrera();
        Inscripcion inscripcion = null;
        try {
            carrera = runFicService.addCarrera(carrera);
            inscripcion = runFicService.addInscripcion(getValidEmail(),getValidTarjeta(), carrera.getIdCarrera());
            runFicService.recogerDorsal(inscripcion.getIdInscripcion(),inscripcion.getTarjeta());

        }catch (InputValidationException | InstanceNotFoundException |
                CarreraYaCelebradaException | NumTarjetaIncorrectoException |
                DorsalHaSidoRecogidoException e) {
            e.printStackTrace();
        } finally {
            if (inscripcion!=null) removeInscripcion(inscripcion); // Con Cascade no hace falta
            if (carrera!=null) removeCarrera(carrera);
        }
    }
// TODO preguntar a profesor por expected
/*
    @Test(expected = CarreraYaCelebradaException.class)
    public void testRecogerDorsalCarreraYaCelebrada(){
        Carrera carrera = getValidCarrera(LocalDateTime.now().minusDays(1));
        Inscripcion inscripcion = null;
        try {
            carrera = runFicService.addCarrera(carrera);
            inscripcion = runFicService.addInscripcion(getValidEmail(),getValidTarjeta(),carrera.getIdCarrera());
            runFicService.recogerDorsal(inscripcion.getIdInscripcion(),inscripcion.getTarjeta());

        }catch (InputValidationException | InstanceNotFoundException |
                NumTarjetaIncorrectoException |
                DorsalHaSidoRecogidoException e) {
            e.printStackTrace();
        } finally {
            if (inscripcion!=null) removeInscripcion(inscripcion); // Con Cascade no hace falta
            if (carrera!=null) removeCarrera(carrera);
        }
    }
*/

/*
    @Test(expected=NumTarjetaIncorrectoException.class)
    public void testRecogerDorsalNumTarjetaNoExiste(){
        Carrera carrera = getValidCarrera();
        Inscripcion inscripcion = null;
        try {
            carrera = runFicService.addCarrera(carrera);
            inscripcion = runFicService.addInscripcion(getValidEmail(),"9234567812345678", carrera.getIdCarrera());
            runFicService.recogerDorsal(inscripcion.getIdInscripcion(),inscripcion.getTarjeta());

        }catch (InputValidationException | InstanceNotFoundException |
                 NumTarjetaIncorrectoException |
                DorsalHaSidoRecogidoException e) {
            e.printStackTrace();
        } finally {
            if (inscripcion!=null) removeInscripcion(inscripcion); // Con Cascade no hace falta
            if (carrera!=null) removeCarrera(carrera);
        }
    }
*/

    /*
    @Test(expected=InputValidationException.class)
    public void testRecogerDorsalEmailNoExiste(){
        Carrera carrera = getValidCarrera();
        Inscripcion inscripcion = null;
        try {
            carrera = runFicService.addCarrera(carrera);
            inscripcion = runFicService.addInscripcion("error@test",getValidTarjeta(), carrera.getIdCarrera());
            runFicService.recogerDorsal(inscripcion.getIdInscripcion(),inscripcion.getTarjeta());

        }catch (InstanceNotFoundException |
                CarreraYaCelebradaException | NumTarjetaIncorrectoException |
                DorsalHaSidoRecogidoException e) {
            e.printStackTrace();
        } finally {
            if (inscripcion!=null) removeInscripcion(inscripcion); // Con Cascade no hace falta
            if (carrera!=null) removeCarrera(carrera);
        }
    }
*/



    //**************************************************************************************************
    //****************************************** Brais *************************************************
    //**************************************************************************************************

    @Test
    public void testAddInscripcion()  {
        Inscripcion i=null;
        Carrera carrera = null;
        try {
            carrera = createCarrera(getValidCarrera());
            Inscripcion inscripcion=new Inscripcion(1L,carrera.getIdCarrera(),1,"1234567812345678",
                    "b@gmail.com",hoxe,false);

            i = runFicService.addInscripcion("b@gmail.com","1234567812345678", carrera.getIdCarrera());
            assertEquals(inscripcion,i);
            Carrera aux = runFicService.findCarrera(i.getIdCarrera());
            assertEquals(carrera.getPlazasOcupadas()+1,aux.getPlazasOcupadas());
        } catch (InputValidationException | InstanceNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (carrera!=null) removeCarrera(carrera);
            if (i!=null) removeInscripcion(i);
        }
    }

    public void testFindInscripcion()  {
        Inscripcion i=null;
        Carrera carrera = null;
        try {
            carrera = createCarrera(getValidCarrera());
            Inscripcion inscripcion=new Inscripcion(1L,carrera.getIdCarrera(),1,"1234567812345678",
                    "b@gmail.com",hoxe,false);

            i = runFicService.addInscripcion("b@gmail.com","1234567812345678", carrera.getIdCarrera());
            List<Inscripcion> l= new ArrayList<Inscripcion>();
            l.add(i);
            assertEquals(l,runFicService.findInscripcion(i.getEmail()));
        } catch (InputValidationException e) {
            e.printStackTrace();
        } finally {
            if (carrera!=null) removeCarrera(carrera);
            if (i!=null) removeInscripcion(i);
        }
    }

}
