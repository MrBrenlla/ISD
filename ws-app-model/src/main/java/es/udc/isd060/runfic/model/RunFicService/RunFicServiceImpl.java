package es.udc.isd060.runfic.model.RunFicService;

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
import es.udc.ws.util.validation.PropertyValidator;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static es.udc.isd060.runfic.model.util.ModelConstants.RUNFIC_DATA_SOURCE;

public class RunFicServiceImpl implements RunFicService {

    private final DataSource dataSource;
    private final SqlCarreraDao carreraDao;
    private final SqlInscripcionDao inscripcionDao;

    public RunFicServiceImpl() {
        dataSource = DataSourceLocator.getDataSource(RUNFIC_DATA_SOURCE);
        carreraDao = SqlCarreraDaoFactory.getDao();
        inscripcionDao = SqlInscripcionDaoFactory.getDao();
    }

    private void validateNumTarjeta (String numTarjeta) throws InputValidationException {
        if (numTarjeta.length() != 16) throw new InputValidationException("Tarxeta erronea");
    }

    private void validateEmail (String email) throws InputValidationException{
        PropertyValidator.validateMandatoryString("email", email);
        if (!email.contains("@")) throw new InputValidationException("Non é un email valido");
    }
    private void validateInscripcion(@org.jetbrains.annotations.NotNull Inscripcion i) throws InputValidationException {
        validateEmail(i.getEmail());
        validateNumTarjeta(i.getTarjeta());
    }



/*
    private void validateInscripcion(@org.jetbrains.annotations.NotNull Inscripcion i) throws InputValidationException {

        if (i.getTarjeta().length() != 16) throw new InputValidationException("Tarxeta erronea");
        PropertyValidator.validateMandatoryString("email", i.getEmail());
        if (!i.getEmail().contains("@")) throw new InputValidationException("non é un email valido");
    }
*/

    private void validateCarrera(Carrera carrera) throws InputValidationException {
        if(carrera.getPrecioInscripcion() < 0.0)
        {
            throw new InputValidationException("PrecioInscripcion is not valid is not valid");
        }

        if(carrera.getCiudadCelebracion() == null || carrera.getCiudadCelebracion().length() == 0)
        {
            throw new InputValidationException("CiudadCelebracion is not valid is not valid");
        }

        if(carrera.getDescripcion() == null || carrera.getDescripcion().length() == 0)
        {
            throw new InputValidationException("Descripcion is not valid is not valid");
        }

        if(carrera.getPlazasDisponibles() < 0)
        {
            throw new InputValidationException("PlazasDisponibles is not valid is not valid");
        }

        if(carrera.getPlazasOcupadas() < 0)
        {
            throw new InputValidationException("PlazasOcupadas is not valid is not valid");
        }


    }

    //**************************************************************************************************
    //****************************************** Brais *************************************************
    //**************************************************************************************************

    public Inscripcion addInscripcion(String email, String tarjeta, long carrera) throws InputValidationException, CarreraInexistente,UsuarioInscrito,FueraDePlazo,SinPlazas {
        LocalDateTime hoxe = LocalDateTime.now().withNano(0);

        Inscripcion i = new Inscripcion(null, carrera, null, tarjeta, email, hoxe, false);

        validateInscripcion(i);

        try (Connection connection = dataSource.getConnection()) {

            try {

                /* Prepare connection. */
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);

                /* Do work. */
                Carrera c;
                try {
                    c = findCarrera(carrera);
                }catch (InstanceNotFoundException e){
                    connection.commit();
                    throw new CarreraInexistente();
                }

                if (c.getPlazasOcupadas() >= c.getPlazasDisponibles()){
                    connection.commit();
                    throw new SinPlazas();
                }

                if (c.getFechaCelebracion().minusDays(1).isBefore(hoxe)) {
                    connection.commit();
                    throw new FueraDePlazo();
                }

                if (inscripcionDao.find(connection,email,carrera)){
                    connection.commit();
                    throw new UsuarioInscrito();
                }

                try{
                    carreraDao.update(connection, carrera);
                }
                catch (InstanceNotFoundException e){
                    connection.rollback();
                    throw new CarreraInexistente();
                }

                i.setDorsal(c.getPlazasOcupadas() + 1);

                i = inscripcionDao.create(connection, i);

                /* Commit. */
                connection.commit();

                return i;

            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(e);
            } catch (RuntimeException | Error e) {
                connection.rollback();
                throw e;
            }

        } catch (SQLException | InstanceNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Inscripcion> findInscripcion(String email) {

        try (Connection connection = dataSource.getConnection()) {
            return inscripcionDao.find(connection, email);
        } catch (SQLException | InstanceNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    //**************************************************************************************************
    //****************************************** Yago *************************************************
    //**************************************************************************************************

    @Override
    public Carrera addCarrera(Carrera carrera) throws InputValidationException {

        validateCarrera(carrera);
        carrera.setFechaAlta(LocalDateTime.now());

        try (Connection connection = dataSource.getConnection()) {

            try {

                /* Prepare connection. */
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);

                /* Do work. */
                Carrera createdCarrera = carreraDao.create(connection, carrera);

                /* Commit. */
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

    @Override
    public List<Carrera> findCarrera (LocalDateTime fechaCelebracion) {
        return findCarrera(fechaCelebracion,null);
    }

    @Override
    public List<Carrera> findCarrera(LocalDateTime fechaCelebracion, String ciudad) {

        try (Connection connection = dataSource.getConnection()) {
            return carreraDao.find(connection, fechaCelebracion, ciudad);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //**************************************************************************************************
    //****************************************** Carlos *************************************************
    //**************************************************************************************************


    public Carrera findCarrera(Long idCarrera) throws InstanceNotFoundException {
        try (Connection connection = dataSource.getConnection()) {
            return carreraDao.find(connection, idCarrera);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public Inscripcion recogerDorsal(Long idInscripcion, String numTarjeta) throws InstanceNotFoundException,
            DorsalHaSidoRecogidoException, NumTarjetaIncorrectoException, CarreraYaCelebradaException, InputValidationException {

        PropertyValidator.validateCreditCard(numTarjeta);
        try (Connection connection = dataSource.getConnection()) {
            try {
                /* Prepare connection. */
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);

                // Buscamos la inscripcion correspondiente al id proporcionado
                Inscripcion inscripcion = inscripcionDao.find(connection, idInscripcion);
                // OPCIONAL Comprobamos que la carrera no haya empezado Nota : Requiere consulta extra
                Carrera carrera = carreraDao.find(connection,inscripcion.getIdCarrera());
                if (carrera.getFechaCelebracion().isBefore(LocalDateTime.now())) {
                    throw new CarreraYaCelebradaException();
                }
                // Comprobamos que el dorsal no ha sido recogido
                if (inscripcion.isRecogido()) {
                    throw new DorsalHaSidoRecogidoException();
                }
                // Comprobamos que el numTarjeta proporcionado corresponde al de la inscripcion
                if (!inscripcion.getTarjeta().equals(numTarjeta)) {
                    throw new NumTarjetaIncorrectoException();
                }
                // Todo ok
                inscripcion.setRecogido(true);
                inscripcionDao.update(connection, inscripcion);
                connection.commit();
                return inscripcion;
            } catch (InstanceNotFoundException | DorsalHaSidoRecogidoException | NumTarjetaIncorrectoException e) {
                connection.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }
}