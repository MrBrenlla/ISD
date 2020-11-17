package es.udc.isd060.runfic.model.RunFicService;

import static es.udc.isd060.runfic.model.util.ModelConstants.RUNFIC_DATA_SOURCE;

import es.udc.isd060.runfic.model.RunFicService.exceptions.DorsalHaSidoRecogidoException;
import es.udc.isd060.runfic.model.RunFicService.exceptions.NumTarjetaIncorrectoException;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.sql.DataSourceLocator;

import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import javax.sql.DataSource;

import es.udc.isd060.runfic.model.carrera.*;
import es.udc.isd060.runfic.model.inscripcion.*;
import es.udc.ws.util.validation.PropertyValidator;

public class RunFicServiceImpl implements RunFicService {

    private final DataSource dataSource;
    private SqlCarreraDao carreraDao = null;
    private SqlInscripcionDao inscripcionDao = null;

    public RunFicServiceImpl() {
        dataSource = DataSourceLocator.getDataSource(RUNFIC_DATA_SOURCE);
        carreraDao = SqlCarreraDaoFactory.getDao();
        inscripcionDao = SqlInscripcionDaoFactory.getDao();
    }

    private void validateInscripcion(@org.jetbrains.annotations.NotNull Inscripcion i) throws InputValidationException {

        if (i.getTarjeta().length()!=16) throw new InputValidationException("Tarxeta erronea");
        PropertyValidator.validateMandatoryString("email", i.getEmail());
        if (! i.getEmail().contains("@")) throw new InputValidationException("non é un email valido");
    }

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

    public Inscripcion addInscripcion(String email,String tarjeta, long carrera) throws InputValidationException {
        LocalDateTime hoxe= LocalDateTime.now();

        Inscripcion i =new Inscripcion(null,carrera,null,tarjeta,email,hoxe,false);

        validateInscripcion(i);

        try (Connection connection = dataSource.getConnection()) {

            try {

                /* Prepare connection. */
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);

                /* Do work. */

                Carrera c = carreraDao.find(connection,carrera);

                if(c.getFechaCelebracion().minusDays(1).isBefore(hoxe)) throw new InputValidationException("Data de escripcion sobrepasada");

                if(! carreraDao.update(connection,carrera)) throw new RuntimeException();

                i.setDorsal(c.getPlazasOcupadas()+1);

                i= inscripcionDao.create(connection,i);

                /* Commit. */
                connection.commit();

                return i;

            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(e);
            } catch (RuntimeException | Error e) {
                connection.rollback();
                throw e;
            } catch (InstanceNotFoundException e) {
                connection.rollback();
                throw e;
            }

        } catch (SQLException | InstanceNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Inscripcion> findInscripcion(String email){

        try (Connection connection = dataSource.getConnection()) {
            return inscripcionDao.find(connection,email);
        } catch (SQLException e) {
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

        try (Connection connection = dataSource.getConnection()) {
            return findCarrera(fechaCelebracion,null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Carrera> findCarrera(LocalDateTime fechaCelebracion, String ciudad) {

        try (Connection connection = dataSource.getConnection()) {
            return carreraDao.find(connection, fechaCelebracion, ciudad);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeCarrera(Long idCarrera) throws InstanceNotFoundException {

        try (Connection connection = dataSource.getConnection()) {

            try {

                /* Prepare connection. */
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);

                /* Do work. */
                carreraDao.remove(connection, idCarrera);

                /* Commit. */
                connection.commit();

            } catch (InstanceNotFoundException e) {
                connection.commit();
                throw e;
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
    //****************************************** Carlos *************************************************
    //**************************************************************************************************


    public Carrera findCarrera ( Long idCarrera ) throws InstanceNotFoundException{
        try (Connection connection = dataSource.getConnection()) {
            return carreraDao.find(connection, idCarrera);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public Inscripcion recogerDorsal (Long idInscripcion,String numTarjeta) throws InstanceNotFoundException ,
            DorsalHaSidoRecogidoException , NumTarjetaIncorrectoException {
        try (Connection connection = dataSource.getConnection()) {
            try {
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);

                // Buscamos la inscripcion correspondiente al id proporcionado
                Inscripcion inscripcion = inscripcionDao.find(connection, idInscripcion);
                // Comprobamos que la carrera no haya empezado NOTA : Operacion pesada
                Long idCarrera = inscripcion.getIdCarrera();
                Inscripcion inscripcion = carreraDao.find(connection, );
                if (inscripcion.isRecogido()) {
                    throw new DorsalHaSidoRecogidoException();
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
                inscripcionDao.update(connection,inscripcion);
                connection.commit();
                return inscripcion;
            } catch (InstanceNotFoundException e) {
                connection.rollback();
                throw e;
            } catch (DorsalHaSidoRecogidoException e) {
                connection.rollback();
                throw e;
            } catch (NumTarjetaIncorrectoException e) {
                connection.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }
}