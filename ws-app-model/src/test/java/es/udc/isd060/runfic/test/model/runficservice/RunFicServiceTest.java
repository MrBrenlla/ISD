package es.udc.isd060.runfic.test.model.runficservice;

import es.udc.isd060.runfic.model.RunFicService.RunFicService;
import es.udc.isd060.runfic.model.RunFicService.RunFicServiceFactory;
import es.udc.isd060.runfic.model.RunFicService.exceptions.*;
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
import java.util.List;

import static es.udc.isd060.runfic.model.util.ModelConstants.RUNFIC_DATA_SOURCE;
import static org.junit.jupiter.api.Assertions.*;

public class RunFicServiceTest {
    private static DataSource dataSource = null;
    private static RunFicService runFicService = null;
    private static SqlCarreraDao carreraDao = null;
    private static SqlInscripcionDao inscripcionDao = null;



    //private static final long SIMULATION_TEST_TIME = 1000;
    //Carlos
    private static final int DIFF_TEST_DAYS = 10;

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


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Carlos
    private String getValidTarjeta( int seed ){
        List<String> tarjetas = new ArrayList<>(10);

        tarjetas.add("0000000000000000");
        tarjetas.add("0000000000000001");
        tarjetas.add("0000000000000002");
        tarjetas.add("0000000000000003");
        tarjetas.add("0000000000000004");
        tarjetas.add("0000000000000005");
        tarjetas.add("0000000000000006");
        tarjetas.add("0000000000000007");
        tarjetas.add("0000000000000008");
        tarjetas.add("0000000000000009");

        return tarjetas.get(seed%tarjetas.size());
    }

    // Carlos
    private String getValidEmail( int seed ){
        List<String> mails = new ArrayList<>(10);

        mails.add("0@test");
        mails.add("1@test");
        mails.add("2@test");
        mails.add("3@test");
        mails.add("4@test");
        mails.add("5@test");
        mails.add("6@test");
        mails.add("7@test");
        mails.add("8@test");
        mails.add("9@test");

        return mails.get(seed%mails.size());

    }

    // Carlos
    private String getInvalidTarjeta(){
        return "INVALID CARD";
    }

    // Carlos
    private String getInvalidEmail(){
        return "INVALID MAIL";
    }


    // Carlos
    private Carrera getValidCarrera(int seed) {
        LocalDateTime fechaCelebracion = LocalDateTime.now().plusDays(DIFF_TEST_DAYS);
        return new Carrera("VALID","seed="+seed,(seed%100)*0.1f,fechaCelebracion,seed%100);
    }

    private Carrera getValidCarrera(int seed , int hoursToCelebrate) {
        Carrera carrera = getValidCarrera(seed);
        carrera.setFechaCelebracion(LocalDateTime.now().plusHours(hoursToCelebrate));
        return carrera;
    }

    // Carlos
    private Carrera getInvalidCarrera() {
        return new Carrera("INVALID","Invalid Carrera",0.1f,LocalDateTime.now().minusDays(DIFF_TEST_DAYS),-1);
    }

    // Carlos
    private Inscripcion getValidInscripcion( Carrera carrera , int seed ){
        return new Inscripcion(carrera.getIdCarrera(),getValidTarjeta(seed),getValidEmail(seed));
    }

    // Carlos
    private void cleanDB(Carrera carrera , Inscripcion inscripcion ) {
        if (inscripcion!=null) removeInscripcion(inscripcion); // Con CASCADE no hace falta
        if (carrera!=null) removeCarrera(carrera);
    }




////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////




    private String getValidTarjeta(){
        return "1234567812345678";
    }

    private String getValidEmail(){
        return "test@udc.es";
    }

    private Carrera getValidCarrera(String ciudadCelebracion) {
        LocalDateTime date1 = LocalDateTime.now().plusDays(45);
        return new Carrera(null,ciudadCelebracion,"Descripcion", 5.5f,LocalDateTime.now(),date1,100,0);
    }

    private Carrera getValidCarrera_2() {
        LocalDateTime date1 = LocalDateTime.of(2022, 5, 11, 19, 30);
        return new Carrera(null,"Mallorca","Descripcion", 2.5f,LocalDateTime.now(),date1,120,0);
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
        try (Connection connection = dataSource.getConnection()) {

            carreraDao.remove(connection, carrera.getIdCarrera());

        } catch (SQLException | InstanceNotFoundException throwables) {

            throwables.printStackTrace();
        }
    }



    private Inscripcion getValidInscripcion(Long idCarrera) {
        LocalDateTime date1 = LocalDateTime.of(2021, 2, 13, 15, 30);
        return new Inscripcion(idCarrera,2,"0","pepito@gmail.com",date1,false);
    }


    private void removeInscripcion(Inscripcion inscripcion) {
        try (Connection connection = dataSource.getConnection()) {

            inscripcionDao.remove(connection, inscripcion.getIdInscripcion());
            /* Commit. */
            //connection.commit();


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
    public void testFindByDateAndCity() throws InputValidationException {
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
    public void testRemoveInscripcion() throws InputValidationException, CarreraInexistente,UsuarioInscrito,FueraDePlazo,SinPlazas {

        Carrera carrera = createCarrera(getValidCarrera());
        assertTrue(runFicService.findInscripcion("y@gmail.com").isEmpty());
        Inscripcion i = runFicService.addInscripcion("y@gmail.com","1234567812345678", carrera.getIdCarrera());

        removeInscripcion(i);

        assertTrue(runFicService.findInscripcion(i.getEmail()).isEmpty());

    }





    //**************************************************************************************************
    //******************************************** Carlos **********************************************
    //**************************************************************************************************

    // Buscar carrera y crear carrera se prueban al mismo tiempo , es decir para probar uno el otro se supone
    // que funciona




///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testRecogerDorsalDatosValidos(){
        int seed = 10;
        Carrera carrera = null;
        Inscripcion inscripcion= null;
        Inscripcion inscripcionOriginal;
        try {
            // Añadimos carrera valida a BBDD
            carrera = getValidCarrera(seed);
            carrera = runFicService.addCarrera(carrera);

            // Añadimos inscripcion valida a BBDD
            String email = getValidEmail(seed);
            String tarjeta = getValidTarjeta(seed);
            // addInscripcion original
            inscripcion = runFicService.addInscripcion(email,tarjeta, carrera.getIdCarrera());
            // addInscripcion alternativa
            //inscripcion = runFicService.addInscripcion(email,tarjeta, carrera);

            inscripcionOriginal = Inscripcion.copy(inscripcion);

            // Recogemos dorsal
            inscripcion = runFicService.recogerDorsal(inscripcion.getIdInscripcion(),tarjeta);

            // Comprobamos que la inscripcion haya sido recogida
            assertTrue(inscripcion.isRecogido());

            // Comprobamos que la inscripcion "sea la misma" que la del dorsal recogido (ver método Inscripcion.same() )
            // TODO same
            //assertTrue(inscripcion.same(inscripcionOriginal));


        }catch (InputValidationException | InstanceNotFoundException |
                CarreraYaCelebradaException | NumTarjetaIncorrectoException |
                DorsalHaSidoRecogidoException | UsuarioInscrito | CarreraInexistente | SinPlazas | FueraDePlazo e) {
            e.printStackTrace();
        }
        finally {
            cleanDB(carrera,inscripcion);
        }

    }


    // TODO mejorar sintaxis
    @Test
    public void testRecogerDorsalDatosInvalidos(){

        // Test idInscripcion incorrecto
        assertThrows(InstanceNotFoundException.class , () -> {
            int SEED = 20;
            // Obtenemos una Carrera
            Carrera carrera = getValidCarrera(SEED);
            // Obtenemos una Inscripcion
            String email = getValidEmail(SEED);
            String tarjeta = getValidTarjeta(SEED);
            carrera=runFicService.addCarrera(carrera);
            Inscripcion inscripcion = runFicService.addInscripcion(email,tarjeta,carrera.getIdCarrera());

            // Recogemos dorsal idInscripcion incorrecto
            runFicService.recogerDorsal(inscripcion.getIdInscripcion()+1,tarjeta);

            // TODO clean
            cleanDB(carrera,inscripcion);
        });

        // Test email incorrecto
        assertThrows(InputValidationException.class , () -> {
            int SEED = 21;
            // Obtenemos una Carrera
            Carrera carrera = getValidCarrera(SEED);
            // Obtenemos una Inscripcion
            String email = getInvalidEmail();
            String tarjeta = getValidTarjeta(SEED);
            carrera=runFicService.addCarrera(carrera);
            Inscripcion inscripcion = runFicService.addInscripcion(email,tarjeta,carrera.getIdCarrera());

            // Recogemos dorsal
            runFicService.recogerDorsal(inscripcion.getIdInscripcion(),tarjeta);

            // TODO clean
            cleanDB(carrera,inscripcion);
        });


        // Test numTarjeta incorrecto
        assertThrows(InputValidationException.class , () -> {
            int SEED = 22;
            // Obtenemos una Carrera
            Carrera carrera = getValidCarrera(SEED);
            // Obtenemos una Inscripcion
            String email = getValidEmail(SEED);
            String tarjeta = getInvalidTarjeta();
            carrera=runFicService.addCarrera(carrera);
            Inscripcion inscripcion = runFicService.addInscripcion(email,tarjeta,carrera.getIdCarrera());

            // Recogemos dorsal
            runFicService.recogerDorsal(inscripcion.getIdInscripcion(),tarjeta);

            // TODO clean
            cleanDB(carrera,inscripcion);
        });

        // Test dorsal ha sido recogido
        assertThrows(DorsalHaSidoRecogidoException.class , () -> {
            int SEED = 23;
            // Obtenemos una Carrera
            Carrera carrera = getValidCarrera(SEED);
            // Obtenemos una Inscripcion
            String email = getValidEmail(SEED);
            String tarjeta = getValidTarjeta(SEED);
            carrera=runFicService.addCarrera(carrera);
            Inscripcion inscripcion = runFicService.addInscripcion(email,tarjeta,carrera.getIdCarrera());

            // Recogemos dorsal 2 veces ( la segunda debe de dar excepcion)
            runFicService.recogerDorsal(inscripcion.getIdInscripcion(),tarjeta);
            runFicService.recogerDorsal(inscripcion.getIdInscripcion(),tarjeta);

            // TODO clean
            cleanDB(carrera,inscripcion);
        });

        testRecogerDorsalCarreraYaEmpezada(false);

    }



///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void testRecogerDorsalCarreraYaEmpezada( boolean debug ) {
        assertThrows(CarreraYaCelebradaException.class , () -> {

            // Sout inicial DEBUG
            if (debug){
                System.out.println("DEBUG testRecogerDorsalCarreraYaEmpezada :\n");
            }


            int SEED = 24;
            // Creamos una Carrera UNCHECKED que se celebro hace 2 horas
            Carrera carrera = getValidCarrera(SEED,-2);
            carrera = this.addUncheckedCarrera(carrera);

            // Sout de la carrera UNCHECKED
            if (debug) {
                System.out.println("Carrera UNCHECKED :\n"+carrera.toString()+"\n");
            }

            // Obtenemos una Inscripcion UNCHECKED de hace 1 hora
            Inscripcion inscripcion = getValidInscripcion(carrera,SEED);
            inscripcion = this.addUncheckedInscripcion(inscripcion.getEmail(),inscripcion.getTarjeta(),
                    carrera,-1);

            // Sout de la inscripcion UNCHECKED
            if (debug) {
                System.out.println("Inscripcion UNCHECKED :\n"+inscripcion.toString()+"\n");
            }


            // Recogemos dorsal ( debe saltar excepcion)
            runFicService.recogerDorsal(inscripcion.getIdInscripcion(),inscripcion.getTarjeta());

            // TODO clean
            cleanDB(carrera,inscripcion);

        });
    }

    // Metodo usado en el test de inscripcion carrera ya celebrada
    private Inscripcion addUncheckedInscripcion(String email, String numTarjeta, Carrera carrera , int hoursToInscribe ) {


        Inscripcion inscripcionCreada = null;

        try (Connection connection = dataSource.getConnection()) {

            try {
                // Prepare connection.
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);

                // Buscamos la carrera correspondiente

                Long idCarrera =  carrera.getIdCarrera();
                carrera = carreraDao.find(connection,idCarrera);

                // si OK
                Inscripcion inscripcion = new Inscripcion(carrera.getIdCarrera(),numTarjeta,email);
                inscripcion.setFechaInscripcion(inscripcion.getFechaInscripcion().plusHours(hoursToInscribe));
                inscripcionCreada = inscripcionDao.create(connection,inscripcion);

                // Commit.
                connection.commit();

            } catch (InstanceNotFoundException  e) {
                connection.rollback();
                throw new RuntimeException(e);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            return inscripcionCreada;
        } catch (RuntimeException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    // Metodo usado en el test de inscripcion carrera ya celebrada
    private Carrera addUncheckedCarrera(Carrera carrera)  {

        carrera.setFechaAlta(LocalDateTime.now());

        try (Connection connection = dataSource.getConnection()) {

            try {

                // Prepare connection.
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);

                // Do work.
                Carrera createdCarrera = carreraDao.create(connection, carrera);


                // Commit.
                connection.commit();

                return createdCarrera;

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




////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////




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
        } catch (InputValidationException | CarreraInexistente | UsuarioInscrito | FueraDePlazo | SinPlazas | InstanceNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (carrera!=null) removeCarrera(carrera);
            if (i!=null) removeInscripcion(i);
        }
    }

    @Test
    public void testAddInscripcion_CarreraInexistente(){

            assertThrows(InstanceNotFoundException.class,() -> runFicService.findCarrera(1L));
            assertThrows(CarreraInexistente.class,() -> runFicService.addInscripcion("b@gmail.com","1234567812345678", 1L));

    }

    @Test
    public void testAddInscripcion_DatosErroneos() {

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
    public void testAddInscripcion_YaInscrito() {

        Carrera carrera = null;
        Inscripcion i = null;
        try {
            carrera = createCarrera(getValidCarrera("Santiago"));
            final Long id = carrera.getIdCarrera();
            i = runFicService.addInscripcion("b@gmail.com","1234567812345678", id);
            assertTrue(i.getEmail().equals("b@gmail.com") && i.getIdCarrera().equals(id));
            assertThrows(UsuarioInscrito.class,()->runFicService.addInscripcion("b@gmail.com","1234567812345679", id));
        } catch ( CarreraInexistente | UsuarioInscrito | FueraDePlazo | SinPlazas | InputValidationException e) {
            e.printStackTrace();
        } finally {
            if (carrera!=null) removeCarrera(carrera);
            if (i!=null) removeInscripcion(i);
        }
    }

    @Test
    public void testAddInscripcion_SinPlazas() {

        Carrera carrera = null;
        try {
            carrera =getValidCarrera("Vigo");
            carrera.setPlazasDisponibles(0);
            carrera=createCarrera(carrera);
            final Long id=carrera.getIdCarrera();
            assertThrows(SinPlazas.class,()->runFicService.addInscripcion("b@gmail.com","1234567812345678", id));
        } finally {
            if (carrera!=null) removeCarrera(carrera);
        }

    }

    @Test
    public void testAddInscripcion_FueraDePlazo() {

        Carrera carrera = null;
        try {
            carrera =getValidCarrera("Lugo");
            carrera.setFechaCelebracion(LocalDateTime.now().plusHours(2));
            carrera=createCarrera(carrera);
            assertTrue(runFicService.findInscripcion("b@gmail.com").isEmpty());
            final Long id=carrera.getIdCarrera();
            assertThrows(FueraDePlazo.class,()->runFicService.addInscripcion("b@gmail.com","1234567812345678", id));
        } finally {
            if (carrera!=null) removeCarrera(carrera);
        }

    }

    @Test
    public void testFindInscripcion() {
        Inscripcion i=null;
        Carrera carrera = null;

        assertTrue(runFicService.findInscripcion("b@gmail.com").isEmpty());

        try {


            carrera = createCarrera(getValidCarrera("Ourense"));
            LocalDateTime antes= LocalDateTime.now().withNano(0);
            i = runFicService.addInscripcion("b@gmail.com","1234567812345678", carrera.getIdCarrera());
            LocalDateTime despois= LocalDateTime.now().withNano(0);

            assertTrue(antes.isBefore(i.getFechaInscripcion())|antes.isEqual(i.getFechaInscripcion()));
            assertTrue(despois.isAfter(i.getFechaInscripcion())|despois.isEqual(i.getFechaInscripcion()));

            List<Inscripcion> l= new ArrayList<>();
            i.setFechaInscripcion(i.getFechaInscripcion());
            l.add(i);
            assertEquals(l,runFicService.findInscripcion(i.getEmail()));

        } catch (InputValidationException | CarreraInexistente | UsuarioInscrito | FueraDePlazo | SinPlazas e) {
            e.printStackTrace();
        } finally {
            if (carrera!=null) removeCarrera(carrera);
            if (i!=null) removeInscripcion(i);
        }
    }

}
