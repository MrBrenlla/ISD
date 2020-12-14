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
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static es.udc.isd060.runfic.model.util.ModelConstants.RUNFIC_DATA_SOURCE;
import static org.junit.jupiter.api.Assertions.*;

public class RunFicServiceTest {
    private static DataSource dataSource = null;
    private static RunFicService runFicService = null;
    private static SqlCarreraDao carreraDao = null;
    private static SqlInscripcionDao inscripcionDao = null;

    private static final long SIMULATION_TEST_TIME = 1000;


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
        LocalDateTime date1 = LocalDateTime.now().plusDays(45);
        return new Carrera(null,ciudadCelebracion,"Descripcion", 5.5f,LocalDateTime.now(),date1,0,100);
    }

    private Carrera getValidCarrera_2() {
        LocalDateTime date1 = LocalDateTime.of(2022, 5, 11, 19, 30);
        return new Carrera(null,"Mallorca","Descripcion", 2.5f,LocalDateTime.now(),date1,0,120);
    }


    private Carrera getValidCarrera() {
        return getValidCarrera("Barcelona");
    }

    private Carrera createCarrera(Carrera carrera) {

        Carrera addedCarrera = null;
        try {
            addedCarrera = runFicService.addCarrera(carrera);
        } catch (InputValidationException e) {
            if(addedCarrera!=null)removeCarrera(addedCarrera);
            throw new RuntimeException(e);
        }
        return addedCarrera;
    }


    private void removeCarrera(Carrera carrera) {

        DataSource dataSource = DataSourceLocator.getDataSource(RUNFIC_DATA_SOURCE);

        try (Connection connection = dataSource.getConnection()) {

            try {

                /* Prepare connection. */
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);

                /* Do work. */
                carreraDao.remove(connection, carrera.getIdCarrera());

                /* Commit. */
                connection.commit();

            } catch (InstanceNotFoundException e) {
                connection.commit();
                throw new RuntimeException(e);
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(e);
            } catch (RuntimeException | Error e) {
                connection.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private Inscripcion getValidInscripcion(Long idCarrera) {
        LocalDateTime date1 = LocalDateTime.of(2021, 2, 13, 15, 30);
        return new Inscripcion(idCarrera,2,"0","pepito@gmail.com",date1,false);
    }


    private void removeInscripcion(Inscripcion inscripcion) {

        DataSource dataSource = DataSourceLocator.getDataSource(RUNFIC_DATA_SOURCE);

        try (Connection connection = dataSource.getConnection()) {

            try {

                /* Prepare connection. */
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);

                /* Do work. */
                inscripcionDao.remove(connection, inscripcion.getIdInscripcion());

                /* Commit. */
                connection.commit();

            } catch (InstanceNotFoundException e) {
                connection.commit();
                throw new RuntimeException(e);
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(e);
            } catch (RuntimeException | Error e) {
                connection.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    //**************************************************************************************************
    //********************************************* Yago ***********************************************
    //**************************************************************************************************

    @Test
    public void testAddCarreraAndCheckValues() throws InputValidationException, InstanceNotFoundException {

        Carrera addedCarrera = null;
        Carrera carrera = null;

        try {
            carrera = createCarrera(getValidCarrera("Negreira"));
            // Create Movie
            LocalDateTime antes= LocalDateTime.now().withNano(0);
            addedCarrera = runFicService.addCarrera(carrera);
            LocalDateTime despois= LocalDateTime.now().withNano(0);

            assertTrue(antes.isBefore(addedCarrera.getFechaCelebracion())|antes.isEqual(addedCarrera.getFechaCelebracion()));

            // Find Movie
            Carrera foundCarrera = runFicService.findCarrera(addedCarrera.getIdCarrera());

            assertEquals(addedCarrera.getIdCarrera(), foundCarrera.getIdCarrera());
            assertEquals(foundCarrera.getCiudadCelebracion(),carrera.getCiudadCelebracion());
            assertEquals(foundCarrera.getDescripcion(),carrera.getDescripcion());
            assertEquals(foundCarrera.getPrecioInscripcion(),carrera.getPrecioInscripcion());
            assertEquals(foundCarrera.getPlazasDisponibles(),carrera.getPlazasDisponibles());
            assertEquals(foundCarrera.getPlazasOcupadas(),carrera.getPlazasOcupadas());
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

        removeCarrera(carrera);

        assertThrows(InstanceNotFoundException.class, () -> runFicService.findCarrera(carrera.getIdCarrera()));

    }


    @Test
    public void testFindByDateAndCity() throws InputValidationException {
        LocalDateTime beforeCreationDate = LocalDateTime.now().withNano(0);

        List<Carrera> carreras = new LinkedList<Carrera>();
        Carrera carrera1 = createCarrera(getValidCarrera("Santiago"));
        carreras.add(carrera1);

        LocalDateTime afterCreationDate = LocalDateTime.now().withNano(0);

        Carrera carrera2 = createCarrera(getValidCarrera("Negreira"));
        carreras.add(carrera2);
        Carrera carrera3= createCarrera(getValidCarrera("Barcelona"));
        carreras.add(carrera3);

        try {
            LocalDateTime fecha = LocalDateTime.now().plusDays(45);
            List<Carrera> foundCarreras = runFicService.findCarrera(fecha);

            assertEquals(carreras.size(), foundCarreras.size());

            foundCarreras = runFicService.findCarrera(fecha);
            assertEquals(3, foundCarreras.size());
            assertEquals(carreras.get(0).getIdCarrera(), foundCarreras.get(0).getIdCarrera());

            foundCarreras = runFicService.findCarrera(fecha,"Barcelona");
            assertEquals(1, foundCarreras.size());
            assertEquals(3, foundCarreras.get(0).getIdCarrera());

            foundCarreras = runFicService.findCarrera(fecha,"Barcelona");
            assertEquals(1, foundCarreras.size());
            assertTrue((foundCarreras.get(0).getFechaCelebracion().compareTo(beforeCreationDate) >= 0));

            foundCarreras = runFicService.findCarrera(LocalDateTime.now().plusDays(80));
            assertEquals(3, foundCarreras.size());
        } finally {
            // Clear Database
            for (Carrera carrera : carreras) {
                removeCarrera(carrera);
            }
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
    public void testRemoveInscripcion() throws InputValidationException {

        Carrera carrera = createCarrera(getValidCarrera());
        Inscripcion i = runFicService.addInscripcion("b@gmail.com","1234567812345678", carrera.getIdCarrera());

        removeInscripcion(i);

        assertTrue(runFicService.findInscripcion(i.getEmail()).isEmpty());

    }

    //**************************************************************************************************
    //******************************************** Carlos **********************************************
    //**************************************************************************************************

    // Buscar carrera y crear carrera se prueban al mismo tiempo , es decir para probar uno el otro se supone
    // que funciona


    @Test
    public void testRecogerDorsalDatosValidos(){
        Carrera carrera = createCarrera(getValidCarrera("Santiago"));
        Inscripcion inscripcion = new Inscripcion(carrera.getIdCarrera(),carrera.getIdCarrera(),carrera.getPlazasOcupadas()+1,"1234567812345678",
                "b@gmail.com",null,false);
        Inscripcion inscripcion1;
        try {
            carrera = runFicService.addCarrera(carrera);
            inscripcion = runFicService.addInscripcion(getValidEmail(),getValidTarjeta(), carrera.getIdCarrera());
            inscripcion1 = runFicService.recogerDorsal(inscripcion.getIdInscripcion(),inscripcion.getTarjeta());
            assertTrue(inscripcion1.isRecogido());
        }catch (InputValidationException | InstanceNotFoundException |
                CarreraYaCelebradaException | NumTarjetaIncorrectoException |
                DorsalHaSidoRecogidoException e) {
            e.printStackTrace();
        } finally {
            if (inscripcion!=null) removeInscripcion(inscripcion); // Con Cascade no hace falta
            if (carrera!=null) removeCarrera(carrera);
        }
    }


    // TODO mejorar sintaxis
    @Test
    public void testRecogerDorsalDatosInvalidos(){


        // Test idInscripcion incorrecto
        assertThrows(InstanceNotFoundException.class , () -> {
            // Obtenemos una Carrera
            Carrera carrera =  createCarrera(getValidCarrera("Santiago"));

            // Obtenemos una Inscripcion
            Inscripcion inscripcion = runFicService.addInscripcion(getValidEmail(),getValidTarjeta(),
                    carrera.getIdCarrera());

            // Recogemos dorsal idInscripcion incorrecto
            runFicService.recogerDorsal(inscripcion.getIdInscripcion()+1,inscripcion.getTarjeta());

            removeCarrera(carrera);
        });

        // Test numTarjeta incorrecto
        assertThrows(NumTarjetaIncorrectoException.class , () -> {
            // Obtenemos una Carrera
            Carrera carrera = createCarrera(getValidCarrera("Santiago"));

            // Obtenemos una Inscripcion
            Inscripcion inscripcion = runFicService.addInscripcion(getValidEmail(),getValidTarjeta(),
                    carrera.getIdCarrera());

            // Recogemos dorsal con un numero de tarjeta incorrecto
            runFicService.recogerDorsal(inscripcion.getIdInscripcion(),
                    inscripcion.getTarjeta().replace("1","2"));
        });

        // Test dorsal recogido
        /*assertThrows(DorsalHaSidoRecogidoException.class , () -> {
            // Obtenemos una Carrera
            Carrera carrera = createCarrera(getValidCarrera("Santiago"));

            // Obtenemos una Inscripcion
            Inscripcion inscripcion = runFicService.addInscripcion(getValidEmail(),getValidTarjeta(),
                    carrera.getIdCarrera());

            // Recogemos dorsal 2 veces ( la segunda debería saltar la excepción )
            runFicService.recogerDorsal(inscripcion.getIdInscripcion(), inscripcion.getTarjeta());
            runFicService.recogerDorsal(inscripcion.getIdInscripcion(), inscripcion.getTarjeta());
        });*/

        /*
        // NOTA : Este test simula el paso del tiempo real
        // el test podría fallar si SIMULATION_TEST_TIME es muy pequeño
        assertThrows(CarreraYaCelebradaException.class , () -> {
            // Obtenemos una Carrera que se celebrara en un futuro ( con margen SIMULATION_TEST_TIME )
            Carrera carrera = getValidCarrera();
            LocalDateTime fechaCelebracion = LocalDateTime.now().plusNanos(1000000*SIMULATION_TEST_TIME);
            carrera.setFechaCelebracion(fechaCelebracion);

            // Nos inscribimos
            Inscripcion inscripcion = runFicService.addInscripcion(getValidEmail(),getValidTarjeta(),
                    carrera.getIdCarrera());

            // Esperamos 2 veces SIMULATION_TEST_TIME
            Thread.sleep(2*SIMULATION_TEST_TIME);

            // Recogemos dorsal ( debería saltar excepción ya que la carrera debería de haber empezado )
            runFicService.recogerDorsal(inscripcion.getIdInscripcion(),inscripcion.getTarjeta());
        });
        */

    }




    //**************************************************************************************************
    //****************************************** Brais *************************************************
    //**************************************************************************************************

    @Test
    public void testAddInscripcion()  {
        Inscripcion i=null;
        Carrera carrera = null;
        try {
            carrera = createCarrera(getValidCarrera("Negreira"));
            Inscripcion inscripcion=new Inscripcion(carrera.getIdCarrera(),carrera.getIdCarrera(),carrera.getPlazasOcupadas()+1,"1234567812345678",
                    "b@gmail.com",null,false);

            LocalDateTime antes= LocalDateTime.now().withNano(0);
            i = runFicService.addInscripcion("b@gmail.com","1234567812345678", carrera.getIdCarrera());
            LocalDateTime despois= LocalDateTime.now().withNano(0);

            assertTrue(antes.isBefore(i.getFechaInscripcion())|antes.isEqual(i.getFechaInscripcion()));
            assertTrue(despois.isAfter(i.getFechaInscripcion())|despois.isEqual(i.getFechaInscripcion()));

            inscripcion.setIdInscripcion(i.getIdInscripcion());
            inscripcion.setFechaInscripcion(i.getFechaInscripcion());

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

    @Test
    public void testAddInscripcion_CarreraInexistente() {

            assertThrows(InstanceNotFoundException.class,() -> runFicService.findCarrera(1L));
            assertThrows(InputValidationException.class,() -> runFicService.addInscripcion("b@gmail.com","1234567812345678", 1L));

    }

    @Test
    public void testAddInscripcion_DatosErroneos()  {

        Carrera carrera = null;
        try {
            carrera = createCarrera(getValidCarrera("Santiago"));
            final Long id=carrera.getIdCarrera();
            assertThrows(InputValidationException.class,()->runFicService.addInscripcion("b@gmail.com","123456781234567", id));
            assertThrows(InputValidationException.class,()->runFicService.addInscripcion("hanbdb","1234567812345678",id));
        } finally {
            if (carrera!=null) removeCarrera(carrera);
        }

    }

    @Test
    public void testFindInscripcion()  {
        Inscripcion i=null;
        Carrera carrera = null;

        assertTrue(runFicService.findInscripcion("b@gmail.com").isEmpty());

        try {


            carrera = createCarrera(getValidCarrera());
            LocalDateTime antes= LocalDateTime.now().withNano(0);
            i = runFicService.addInscripcion("b@gmail.com","1234567812345678", carrera.getIdCarrera());
            LocalDateTime despois= LocalDateTime.now().withNano(0);

            assertTrue(antes.isBefore(i.getFechaInscripcion())|antes.isEqual(i.getFechaInscripcion()));
            assertTrue(despois.isAfter(i.getFechaInscripcion())|despois.isEqual(i.getFechaInscripcion()));

            List<Inscripcion> l= new ArrayList<>();
            i.setFechaInscripcion(i.getFechaInscripcion());
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
