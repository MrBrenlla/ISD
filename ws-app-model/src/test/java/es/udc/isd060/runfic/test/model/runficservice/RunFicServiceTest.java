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
    public void testRemoveInscripcion() throws InputValidationException {

        Carrera carrera = createCarrera(getValidCarrera());
        Inscripcion i = runFicService.addInscripcion("b@gmail.com","1234567812345678", carrera.getIdCarrera());

        removeInscripcion(i);

        assertTrue(runFicService.findInscripcion(i.getEmail()).isEmpty());

    }

    @Test
    public void testRemoveNonExistentInscripcion() {
        assertThrows(InstanceNotFoundException.class, () -> runFicService.removeCarrera(NON_EXISTENT_CARRERA_ID));
    }




    //**************************************************************************************************
    //******************************************** Carlos **********************************************
    //**************************************************************************************************

    // Buscar carrera y crear carrera se prueban al mismo tiempo , es decir para probar uno el otro se supone
    // que funciona

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Tests del método ALTERNATIVO de AddInscripcion

    @Test
    public void testAddInscripcionAlt(){
        int SEED = 1;
        Carrera carrera = getValidCarrera(SEED);
        Inscripcion inscripcion= null;
        try {
            carrera = runFicService.addCarrera(carrera);
            String tarjeta = getValidTarjeta(SEED);
            String email = getValidEmail(SEED);
            inscripcion =runFicService.addInscripcion(email,tarjeta,carrera);

        }catch (PlazasNoDisponiblesException | InputValidationException |
                InstanceNotFoundException | PlazoDeInscripcionYaTerminadoException |
                UsuarioYaRegistradoException e) {
            e.printStackTrace();
        }finally {
            // Clean Database
            cleanDB(carrera,inscripcion);
        }

    }

    // TODO solucionar problemas de limpieza
    @Test
    public void testAddInscripcionAltDatosInvalidos(){

        // Check inscripcion email invalido
        assertThrows(InputValidationException.class, () -> {
            // Get data
            int seed = 1;
            Carrera carrera = getValidCarrera(seed);
            String mail = getInvalidEmail();
            String tarjeta = getValidTarjeta(seed);

            // Add Carrera
            carrera = runFicService.addCarrera(carrera);

            // Add Inscripcion ( should throw exception )
            Inscripcion inscripcion = runFicService.addInscripcion(mail,tarjeta,carrera);

            // Clean Database
            cleanDB(carrera,inscripcion);
        });

        // Check inscripcion tarjeta invalida
        assertThrows(InputValidationException.class, () -> {
            // Get data
            int seed = 2;
            Carrera carrera = getValidCarrera(seed);
            String mail = getValidEmail(seed);
            String tarjeta = getInvalidTarjeta();

            // Add Carrera
            carrera = runFicService.addCarrera(carrera);

            // Add Inscripcion ( should throw exception )
            Inscripcion inscripcion = runFicService.addInscripcion(mail,tarjeta,carrera);

            // Clean Database
            cleanDB(carrera,inscripcion);
        });

        // TODO Carrera Inexistente
        /*
        // Check inscripcion Carrera inexistente
        assertThrows(InstanceNotFoundException.class, () -> {
            // Get data
            int seed = 3;
            Carrera carrera = getValidCarrera(seed);
            Carrera carreraInexistente = getValidCarrera(seed+1);
            String mail = getValidEmail(seed);
            String tarjeta = getValidTarjeta(seed);

            // Add Carrera
            carrera = runFicService.addCarrera(carrera);

            // Add Inscripcion ( should throw exception )
            Inscripcion inscripcion = runFicService.addInscripcion(mail,tarjeta,carreraInexistente);

            // Clean Database
            cleanDB(carrera,null);
        });
        */

        // Check inscripcion fuera de plazo
        assertThrows(PlazoDeInscripcionYaTerminadoException.class, () -> {
            // Get data
            int seed = 4;
            String mail = getValidEmail(seed);
            String tarjeta = getValidTarjeta(seed);
            // Get Carrera que se celebra en 10 horas ( ya no se puede registrarse en ella)
            Carrera carrera = getValidCarrera(seed,10);

            // Add Carrera
            Carrera addedCarrera = runFicService.addCarrera(carrera);

            // Add Inscripcion ( should throw exception )
            Inscripcion inscripcion = runFicService.addInscripcion(mail,tarjeta,addedCarrera);

            // Clean Database
            cleanDB(carrera,inscripcion);
        });

        // Check inscripcion 2 veces
        assertThrows(UsuarioYaRegistradoException.class, () -> {
            // Get data
            int seed = 5;
            Carrera carrera = getValidCarrera(seed);
            String mail = getValidEmail(seed);
            String tarjeta = getValidTarjeta(seed);

            // Add Carrera
            carrera = runFicService.addCarrera(carrera);

            // Add Inscripcion ( should throw exception 2nd time )
            Inscripcion inscripcion = runFicService.addInscripcion(mail,tarjeta,carrera);
            inscripcion = runFicService.addInscripcion(mail,tarjeta,carrera);

            // Clean Database
            cleanDB(carrera,inscripcion);
        });



    }



///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   /*
    @Test
    public void testRecogerDorsalDatosValidos(){
        int SEED = 1;
        Carrera carrera = getValidCarrera(SEED);
        Inscripcion inscripcion= null;
        Inscripcion inscripcionRecogida;
        try {
            // Añadimos la carrera y la inscripcion a la BBDD y recogemos dorsal
            carrera = runFicService.addCarrera(carrera);
            String email = getValidEmail(SEED);
            String tarjeta = getValidTarjeta(SEED);
            inscripcion = runFicService.addInscripcion(email,tarjeta, carrera);
            inscripcionRecogida = runFicService.recogerDorsal(inscripcion.getIdInscripcion(),tarjeta);

            // Comprobamos que la inscripcion haya sido recogida
            assertTrue(inscripcionRecogida.isRecogido());

            // Comprobamos que la inscripcion "sea la misma" que la del dorsal recogido (ver método Inscripcion.same() )
            assertTrue(inscripcion.same(inscripcionRecogida));

        }catch (InputValidationException | InstanceNotFoundException | CarreraYaCelebradaException |
                NumTarjetaIncorrectoException | DorsalHaSidoRecogidoException |
                PlazoDeInscripcionYaTerminadoException | PlazasNoDisponiblesException |
                UsuarioYaRegistradoException e) {
            e.printStackTrace();
        } finally {
            cleanDB(carrera,inscripcion);
        }
    }


    // TODO mejorar sintaxis
    @Test
    public void testRecogerDorsalDatosInvalidos(){

        // Test idInscripcion incorrecto
        assertThrows(InstanceNotFoundException.class , () -> {
            int SEED = 1;
            // Obtenemos una Carrera
            Carrera carrera = getValidCarrera(SEED);
            // Obtenemos una Inscripcion
            String email = getValidEmail(SEED);
            String tarjeta = getValidTarjeta(SEED);
            Inscripcion inscripcion = runFicService.addInscripcion(email,tarjeta,carrera);

            // Recogemos dorsal idInscripcion incorrecto
            //runFicService.recogerDorsal(inscripcion.getIdInscripcion()+1,tarjeta);

            //if (inscripcion!=null) removeInscripcion(inscripcion); // Con CASCADE no hace falta
            //if (carrera!=null) removeCarrera(carrera);
        });

        // Test numTarjeta incorrecto

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
