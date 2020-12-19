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
import es.udc.isd060.runfic.model.util.ErrorConstants;
import es.udc.isd060.runfic.model.util.ModelPropertyValidator;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.sql.DataSourceLocator;
import es.udc.ws.util.sql.SimpleDataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static es.udc.isd060.runfic.model.util.ModelConstants.MAX_PRICE;
import static es.udc.isd060.runfic.model.util.ModelConstants.RUNFIC_DATA_SOURCE;
import static org.junit.jupiter.api.Assertions.*;

public class RunFicServiceTest {
    private static DataSource dataSource = null;
    private static RunFicService runFicService = null;
    private static SqlCarreraDao carreraDao = null;
    private static SqlInscripcionDao inscripcionDao = null;



    //private static final long SIMULATION_TEST_TIME = 1000;
    //Carlos


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


    //**************************************************************************************************
    //****************************************** Carlos *************************************************
    //**************************************************************************************************


    private static  final  int SEED_SIZE = 10;
    private static final int DIFF_TEST_DAYS = 10;

    // Carlos
    private String getValidTarjeta( int seed ){
        List<String> tarjetas = new ArrayList<>(SEED_SIZE);

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
        List<String> mails = new ArrayList<>(SEED_SIZE);

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
    @Test
    public void  testGetValidTarjeta () {
        List<String> validTarjetas= new ArrayList<>(10);

        // Añadimos "todas" las tarjetas generadas por la seed
        for(int i=0; i<SEED_SIZE;i++){
            validTarjetas.add(getValidTarjeta(i));
        }

        for(int i=0; i<SEED_SIZE;i++){
            try {
                ModelPropertyValidator.validateNumTarjeta(validTarjetas.get(i));
            } catch ( InputValidationException  | IndexOutOfBoundsException e ){
                throw  new RuntimeException(e);
            }
        }
    }


    // Carlos
    @Test
    public void  testGetValidEmail () {
        List<String> validMails= new ArrayList<>(10);

        // Añadimos "todas" las tarjetas generadas por la seed
        for(int i=0; i<SEED_SIZE;i++){
            validMails.add(getValidEmail(i));
        }

        for(int i=0; i<SEED_SIZE;i++){
            try {
                ModelPropertyValidator.validateEmail(validMails.get(i));
            } catch ( InputValidationException | IndexOutOfBoundsException e) {
                throw  new RuntimeException(e);
            }
        }
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
        return new Carrera("VALID","seed="+seed,(seed%MAX_PRICE)*0.001f,fechaCelebracion, (int) (seed%MAX_PRICE));
    }

    // Carlos
    private Carrera getValidCarrera(int seed , int hoursToCelebrate) {
        Carrera carrera = getValidCarrera(seed);
        carrera.setFechaCelebracion(LocalDateTime.now().plusHours(hoursToCelebrate));
        return carrera;
    }


    // Carlos
    @Test
    public void  testGetValidCarrera () {
        List<Carrera> validCarreras= new ArrayList<>(10);

        // Añadimos "todas" las tarjetas generadas por la seed
        for(int i=0; i<SEED_SIZE;i++){
            validCarreras.add(getValidCarrera(i));
        }

        boolean isValid = true;

        // Probamos con  "todas" las tarjetas generadas por la seed en la funcion con solo seed
        for(int i=0; (i<SEED_SIZE);i++){
            try {
                isValid = isValid | (ModelPropertyValidator.validateCarrera(validCarreras.get(i)));
            } catch ( InputValidationException e){
                throw new RuntimeException(e);
            }
        }

        // Probamos  las tarjetas generadas por la seed en la funcion con  seed y hoursToCelebrate en valores límite
        try {
            isValid = isValid | (ModelPropertyValidator.validateCarrera(getValidCarrera(0, Integer.MAX_VALUE)));
            isValid = isValid | (ModelPropertyValidator.validateCarrera(getValidCarrera(SEED_SIZE - 1, Integer.MAX_VALUE)));
            isValid = isValid | (ModelPropertyValidator.validateCarrera(getValidCarrera(0, Integer.MIN_VALUE)));
            isValid = isValid | (ModelPropertyValidator.validateCarrera(getValidCarrera(SEED_SIZE - 1, Integer.MIN_VALUE)));
        } catch ( InputValidationException e) {
            throw new RuntimeException(e);
        }
        if (isValid == false ) {
            throw new RuntimeException("Invalid Carrera");
        }

    }


    // TODO CORREGIR
/*
    // Carlos
    @Test
    public void testAltRemoveCarrera() {

        // TESTS DEPENDIENTES
        try {
            // getValid
            testGetValidCarrera();
            // addCarrera
            testAddCarreraAndCheckValues();
            testAddInvalidMovie();
            // findCarrera
            testFindCarrera();

        } catch ( Exception  e) {
            throw new RuntimeException(e);
        }


        Carrera carrera = null;

        carrera = getValidCarrera();

        try {
            carrera=runFicService.addCarrera(carrera);
        } catch ( Exception e){
            throw  new RuntimeException(e);
        }

        try {
            this.removeCarrera(carrera);
        } catch ( Exception e){
            throw new RuntimeException(e);
        }

        try {
            runFicService.findCarrera(carrera.getIdCarrera());
        } catch ( Exception e){
            throw new RuntimeException(e);
        }

        // Clean DB
        cleanDB(carrera);

    }
*/


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

    // Carlos
    private void cleanDB(Carrera carrera) {
        if (carrera!=null) removeCarrera(carrera);
    }

    private void cleanDB(List <Carrera >carreras) {
        for ( int i=0 ; i<carreras.size();i++) {
            if (carreras.get(i)!=null) removeCarrera(carreras.get(i));
        }
    }

    // Carlos
    private void cleanDB(Inscripcion inscripcion ) {
        if (inscripcion!=null) removeInscripcion(inscripcion);
    }

    /*

    public Carrera findCarrera(Long idCarrera) throws InstanceNotFoundException {
        try (Connection connection = dataSource.getConnection()) {
            return carreraDao.find(connection, idCarrera);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
     */

    // TODO CORREGIR
    // Carlos
    // Realmente no es un test al uso sino que sirve para , con ayuda de un gestor de BD , comprobar el ultimo idCarrera
    // PARA PROBAR : 1-> Correr todos los tests y anotar id más alto 2-> correr sólo ese test y comprobar que el valor
    // anotado coincide con el mostrado por pantalla
    @Test
    public void testGetLastIdCarrera(){
        try {
            System.out.println(getLastIdCarrera());
        } catch ( RuntimeException e) {
            System.out.println("NO SE HA PODIDO CALCULAR EL LAST IDCARRERA , VUELVA  A INTENTARLO");
        }
    }

    // Carlos
    public Long getLastIdCarrera(){
        dataSource = new SimpleDataSource();

        // Add "dataSource" to "DataSourceLocator".
        DataSourceLocator.addDataSource(RUNFIC_DATA_SOURCE, dataSource);
        try {
            // Buscamos conexion
            try (Connection connection = dataSource.getConnection()) {

                // Generamos PreparedStatement
                try (PreparedStatement preparedStatement =
                             connection.prepareStatement("SELECT idCarrera FROM Carrera ORDER BY idCarrera")) {
                    // Ejecutamos consulta
                    ResultSet resultSet = preparedStatement.executeQuery();

                    if (!resultSet.next()) {
                        throw new RuntimeException("No available Carreras");
                    }

                    while (!resultSet.next()) {
                        resultSet.next();
                    }
                    return resultSet.getLong(1);


                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch ( Exception e) {
            throw new RuntimeException(e);
        }

    }




    //**************************************************************************************************
    //**************************************************************************************************
    //**************************************************************************************************




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

    private Carrera getValidCarrera_byDate (String ciudadCelebracion,LocalDateTime date) {
        return new Carrera(null,ciudadCelebracion,"Descripcion", 5.5f,LocalDateTime.now(),date,100,0);
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

        try {
            runFicService.removeInscripcion(inscripcion);
        } catch (InstanceNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


    //**************************************************************************************************
    //********************************************* Yago ***********************************************
    //**************************************************************************************************

    @Test
    public void testAddCarreraAndCheckValues() throws InputValidationException, InstanceNotFoundException {


        Carrera carrera = getValidCarrera("Barcelona");
        Carrera addedCarrera = null;

        try {
            LocalDateTime antes= LocalDateTime.now().withNano(0);
            addedCarrera = runFicService.addCarrera(carrera);
            LocalDateTime despues= LocalDateTime.now().withNano(0);

            assertTrue(antes.isBefore(addedCarrera.getFechaAlta())|antes.isEqual(addedCarrera.getFechaAlta()));
            assertTrue(despues.isAfter(addedCarrera.getFechaAlta())|despues.isEqual(addedCarrera.getFechaAlta()));
            assertTrue(antes.isBefore(addedCarrera.getFechaCelebracion())|antes.isEqual(addedCarrera.getFechaCelebracion()));

            // Find Movie
            antes= LocalDateTime.now().withNano(0);
            Carrera foundCarrera = runFicService.findCarrera(addedCarrera.getIdCarrera());
            despues= LocalDateTime.now().withNano(0);

            assertEquals(addedCarrera.getIdCarrera(), foundCarrera.getIdCarrera());
            assertEquals(foundCarrera.getCiudadCelebracion(),carrera.getCiudadCelebracion());
            assertEquals(foundCarrera.getDescripcion(),carrera.getDescripcion());
            assertEquals(foundCarrera.getPrecioInscripcion(),carrera.getPrecioInscripcion());
            assertEquals(foundCarrera.getPlazasDisponibles(),carrera.getPlazasDisponibles());
            assertEquals(foundCarrera.getPlazasOcupadas(),carrera.getPlazasOcupadas());

            assertTrue(antes.isBefore(foundCarrera.getFechaAlta())|antes.isEqual(foundCarrera.getFechaAlta()));
            assertTrue(despues.isAfter(foundCarrera.getFechaAlta())|despues.isEqual(foundCarrera.getFechaAlta()));
            assertTrue(antes.isBefore(foundCarrera.getFechaCelebracion())|antes.isEqual(foundCarrera.getFechaCelebracion()));

            assertTrue(addedCarrera.getFechaCelebracion().isEqual(foundCarrera.getFechaCelebracion()));

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

        //Add carreras
        List<Carrera> carreras = new LinkedList<Carrera>();
        LocalDateTime fecha = LocalDateTime.now().plusDays(45); //Var. for date search
        LocalDateTime fecha_2 = LocalDateTime.now().plusDays(10);
        LocalDateTime fecha_3 = LocalDateTime.now().minusDays(5);
        LocalDateTime fecha_4 = LocalDateTime.now().plusDays(1);

        Carrera carrera1 = createCarrera(getValidCarrera_byDate("Barcelona",fecha_2));
        carreras.add(carrera1);
        Carrera carrera2 = createCarrera(getValidCarrera_byDate("Palma de Mallorca",fecha_2));
        carreras.add(carrera2);
        Carrera carrera_additional = createCarrera(getValidCarrera_byDate("León",fecha_3));
        carreras.add(carrera_additional);

        try {

            List<Carrera> foundCarreras = runFicService.findCarrera(fecha);
            assertEquals(carreras.size()-1, foundCarreras.size());

            //Carreras Out Date - (Ya celebradas)
            foundCarreras = runFicService.findCarrera(fecha_3);
            assertEquals(0, foundCarreras.size());

            //Find only by: Date
            Carrera carrera3 = createCarrera(getValidCarrera_byDate("Tenerife",fecha_4));
            carreras.add(carrera3);

            foundCarreras = runFicService.findCarrera(fecha_4.plusDays(1));

            assertEquals(1, foundCarreras.size());

            //Find by: Date and City
            Carrera carrera4 = createCarrera(getValidCarrera_byDate("Gran Canaria",fecha_4));
            carreras.add(carrera4);

            foundCarreras = runFicService.findCarrera(fecha_4.plusDays(1),"Gran Canaria");

            assertEquals(1, foundCarreras.size());

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
    public void testRemoveInscripcion() throws InputValidationException, CarreraInexistente,UsuarioInscrito,FueraDePlazo,SinPlazas {

        Carrera carrera = createCarrera(getValidCarrera());

        assertTrue(runFicService.findInscripcion("holabuneas@gmail.com").isEmpty());
        Inscripcion i = runFicService.addInscripcion("holabuneas@gmail.com","1234567812345678", carrera.getIdCarrera());
        assertTrue(!runFicService.findInscripcion("holabuneas@gmail.com").isEmpty());
        removeInscripcion(i);
        assertTrue(runFicService.findInscripcion("holabuneas@gmail.com").isEmpty());

    }

    @Test
    public void testRemoveNonExistentCarrera()  {
        Carrera nonExistent_Carrera = new Carrera(-1L,null,null, 0.0f,LocalDateTime.now(),LocalDateTime.now(),0,0);
        assertThrows(RuntimeException.class, () -> removeCarrera(nonExistent_Carrera));
    }

    @Test
    public void testRemoveNonExistentInscripcion() {
        //inscripcion
        Inscripcion inscripcion = new Inscripcion(-5L,null,2,"0","pepito@gmail.com",LocalDateTime.now(),false);
        //assertThrows(InstanceNotFoundException.class, () -> runFicService.removeInscripcion(inscripcion)); - InstanceNotFoundException throwables!
    }


    //**************************************************************************************************
    //******************************************** Carlos **********************************************
    //**************************************************************************************************


    @Test
    public void testFindCarrera(){

        // TESTS DEPENDIENTES
        try {
            // getValid
            testGetValidCarrera();

            // AddCarrera
            testAddCarreraAndCheckValues();
        } catch ( Exception e ) {
            throw  new RuntimeException(e);
        }

        Carrera carrera = null;

        try {
            int SEED = 100;
            carrera = getValidCarrera(100);
            carrera=runFicService.addCarrera(carrera);
            Carrera carreraOriginal = Carrera.copy(carrera);
            carrera=runFicService.findCarrera(carrera.getIdCarrera());
        } catch ( InputValidationException  | InstanceNotFoundException e){
            e.printStackTrace();
        } catch ( Exception e ){
           throw  new RuntimeException(e);
        } finally {
            cleanDB(carrera);
        }

    }

    /*
    @Test
    public void testFindInvalidCarrera(){

        // TESTS DEPENDIENTES
        try {
            // getValid
            testGetValidCarrera();

            // AddCarrera
            testAddCarreraAndCheckValues();
        } catch ( Exception e ) {
            throw  new RuntimeException(e);
        }

        Carrera carrera = this.getInvalidCarrera();
        Carrera carreraOriginal = null;

        // Añadimos carrera invalida a BBDD

        carreraOriginal=Carrera.copy(carrera);
        carrera = this.addUncheckedCarrera(carrera);
        assertTrue(carrera.same(carrera));
        cleanDB(carrera);



        // Test idCarrera incorecto
        // Lista de id Invalidos a probar

        List<Long> invalidIdsToTest = new ArrayList<>();
        invalidIdsToTest.add((long) -1);
        invalidIdsToTest.add((long) );

        assertThrows(InstanceNotFoundException.class , () -> {


        }



    }

*/


    @Test
    public void testRecogerDorsalDatosValidos(){
        int seed = 10;
        Carrera carrera = null;
        Inscripcion inscripcion= null;
        Inscripcion inscripcionOriginal;
        try {

            // OJO : VALIDACION SOLO FUNCIONA SI SE USA EN TODOS LOS TESTS ( Y NO CONTIENE ERRORES )
            // TESTS DEPENDIENTES
            try {
                // getValid
                testGetValidTarjeta();
                testGetValidEmail();
                testGetValidCarrera();

                // addInscripcion
                testAddInscripcion();

                // addCarrera
                testAddCarreraAndCheckValues();
            } catch ( Exception e ) {
                throw  new RuntimeException(e);
            }


            // Añadimos carrera valida a BBDD
            carrera = getValidCarrera(seed);
            carrera = runFicService.addCarrera(carrera);

            // Añadimos inscripcion valida a BBDD
            String email = getValidEmail(seed);
            String tarjeta = getValidTarjeta(seed);


            // addInscripcion original
            inscripcion = runFicService.addInscripcion(email,tarjeta, carrera.getIdCarrera());


            // Creamos una copia de la inscripción
            inscripcionOriginal = Inscripcion.copy(inscripcion);

            // Recogemos dorsal
            inscripcion = runFicService.recogerDorsal(inscripcion.getIdInscripcion(),tarjeta);


            // Comprobamos que el dorsal de la inscripcion haya sido recogido
            assertTrue(inscripcion.isRecogido());

            // Comprobamos que la inscripcion "sea la misma" que la del dorsal recogido (ver método Inscripcion.same() )
            assertTrue(inscripcion.same(inscripcionOriginal));


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


        boolean debug = false;

        // OJO : VALIDACION SOLO FUNCIONA SI SE USA EN TODOS LOS TESTS ( Y NO CONTIENE ERRORES )
        // TESTS DEPENDIENTES
        try {
            // getValid
            testGetValidTarjeta();
            testGetValidEmail();
            testGetValidCarrera();

            //addInscripcion
            testAddInscripcion();
            testAddInscripcion_DatosErroneos();
            testAddInscripcion_CarreraInexistente();
            testAddInscripcion_FueraDePlazo();
            testAddInscripcion_SinPlazas();
            testAddInscripcion_YaInscrito();

            //addCarrera
            testAddCarreraAndCheckValues();
            testAddInvalidMovie();

            //recogerDorsal
            testRecogerDorsalDatosValidos();


        } catch ( Exception  e) {
            throw new RuntimeException(e);
        }


        // Test idInscripcion incorrecto
        assertThrows(InstanceNotFoundException.class , () -> {
            int SEED = 20;
            // Obtenemos una Carrera válida
            Carrera carrera = getValidCarrera(SEED);
            ModelPropertyValidator.validateCarrera(carrera);


            Inscripcion inscripcion = null;
            String email = getValidEmail(SEED);
            ModelPropertyValidator.validateEmail(email);
            String tarjeta = getValidTarjeta(SEED);
            ModelPropertyValidator.validateNumTarjeta(tarjeta);

            try {
                // Añadimos la Carrera
                carrera = runFicService.addCarrera(carrera);
            } catch ( Exception e ){
                System.out.println("Error "+ ErrorConstants.ERR_ADDCARRERA_CARRERA);
                throw new RuntimeException(e);
            }

            try {
                // Nos inscribimos en la carrera
                 inscripcion = runFicService.addInscripcion(email,tarjeta,carrera.getIdCarrera());
            } catch ( Exception e){
                System.out.println("Error "+ErrorConstants.ERR_ADDINSCRIPCION_EMAILTARJETAIDCARRERA);
                throw new RuntimeException(e);
            }


            try {
                // Recogemos dorsal
                System.out.println("Error "+ErrorConstants.ERR_RECOGERDORSAL_IDINSCRIPCION_NUMTARJETA);
                runFicService.recogerDorsal(inscripcion.getIdInscripcion(), tarjeta);
            } catch ( Exception e ){
                cleanDB(carrera,inscripcion);
                throw  new RuntimeException(e);
            }

        });


        // Test email incorrecto
        assertThrows(InputValidationException.class , () -> {
            int SEED = 21;
            // Obtenemos una Carrera válida
            Carrera carrera = getValidCarrera(SEED);

            Inscripcion inscripcion = null;
            String email = getInvalidEmail();
            String tarjeta = getValidTarjeta(SEED);

            try {
                // Añadimos la Carrera
                carrera = runFicService.addCarrera(carrera);
            } catch ( Exception e ){
                System.out.println("Error "+ ErrorConstants.ERR_ADDCARRERA_CARRERA);
                throw new RuntimeException(e);
            }

            try {
                // Nos inscribimos en la carrera
                inscripcion = runFicService.addInscripcion(email,tarjeta,carrera.getIdCarrera());
            } catch ( Exception e){
                System.out.println("Error "+ErrorConstants.ERR_ADDINSCRIPCION_EMAILTARJETAIDCARRERA);
                throw new RuntimeException(e);
            }

            try {
                // Recogemos dorsal
                System.out.println("Error "+ErrorConstants.ERR_RECOGERDORSAL_IDINSCRIPCION_NUMTARJETA);
                runFicService.recogerDorsal(inscripcion.getIdInscripcion(), tarjeta);
            } catch ( Exception e ){
                cleanDB(carrera,inscripcion);
                throw  new RuntimeException(e);
            }

        });


        // Test numTarjeta incorrecto
        assertThrows(InputValidationException.class , () -> {
            int SEED = 22;
            // Obtenemos una Carrera válida
            Carrera carrera = getValidCarrera(SEED);
            Inscripcion inscripcion = null;
            // Obtenemos una Inscripcion
            String email = getValidEmail(SEED);
            String tarjeta = getInvalidTarjeta();

            try {
                // Añadimos la Carrera
                carrera = runFicService.addCarrera(carrera);
            } catch ( Exception e ){
                System.out.println("Error "+ ErrorConstants.ERR_ADDCARRERA_CARRERA);
                throw new RuntimeException(e);
            }

            try {
                // Nos inscribimos en la carrera
                inscripcion = runFicService.addInscripcion(email,tarjeta,carrera.getIdCarrera());
            } catch ( Exception e){
                System.out.println("Error "+ErrorConstants.ERR_ADDINSCRIPCION_EMAILTARJETAIDCARRERA);
                throw new RuntimeException(e);
            }
            try {
                // Recogemos dorsal
                System.out.println("Error "+ErrorConstants.ERR_RECOGERDORSAL_IDINSCRIPCION_NUMTARJETA);
                runFicService.recogerDorsal(inscripcion.getIdInscripcion(), tarjeta);
            } catch ( Exception e ){
                cleanDB(carrera,inscripcion);
                throw  new RuntimeException(e);
            }

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

            try {
                // Recogemos dorsal
                System.out.println("Error "+ErrorConstants.ERR_RECOGERDORSAL_IDINSCRIPCION_NUMTARJETA);
                runFicService.recogerDorsal(inscripcion.getIdInscripcion(), tarjeta);
            } catch ( Exception e ){
                cleanDB(carrera,inscripcion);
                throw  new RuntimeException(e);
            }

            try {
                // Recogemos dorsal 2 vez
                runFicService.recogerDorsal(inscripcion.getIdInscripcion(), tarjeta);
            } catch ( Exception e ){
                cleanDB(carrera,inscripcion);
                throw  e;
            }
        });


        // Test carrera ya celebrada

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


            try {
                // Recogemos dorsal ( debe saltar excepcion)
                runFicService.recogerDorsal(inscripcion.getIdInscripcion(), inscripcion.getTarjeta());
            } catch ( Exception e ){
                cleanDB(carrera,inscripcion);
                throw  e;
            }

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
