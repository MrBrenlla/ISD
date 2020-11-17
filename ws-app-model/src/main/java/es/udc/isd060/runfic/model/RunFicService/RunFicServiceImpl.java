package es.udc.isd060.runfic.model.RunFicService;

import static es.udc.isd060.runfic.model.util.ModelConstants.RUNFIC_DATA_SOURCE;

import es.udc.isd060.runfic.model.RunFicService.exceptions.DorsalHaSidoRecogidoException;
import es.udc.isd060.runfic.model.RunFicService.exceptions.NumTarjetaIncorrectoException;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.sql.DataSourceLocator;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;

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

        if (i.getTarjeta().length()<>16) throw new InputValidationException();
        PropertyValidator.validateMandatoryString("email", i.getEmail());
        if (! i.getEmail().contains("@")) throw new InputValidationException();
    }

    //**************************************************************************************************
    //****************************************** Brais *************************************************
    //**************************************************************************************************

    //**************************************************************************************************
    //****************************************** Yago *************************************************
    //**************************************************************************************************

    @Override
    public Carrera addCarrera(Carrera carrera) {
        return null;
    }

    @Override
    public <List> Carrera findCarrera(LocalDateTime fechaCelebracion) {
        return null;
    }

    @Override
    public <List> Carrera findCarrera(LocalDateTime fechaCelebracion, String ciudad) {
        return null;
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
                // Comprobamos que el dorsal no ha sido recogido
                if (inscripcion.isRecogido()) {
                    throw new DorsalHaSidoRecogidoException();
                }
                // Comprobamos que el numTarjeta proporcionado corresponde al de la inscripcion
                if (!inscripcion.getTarjeta().equals(numTarjeta)) {
                    throw new NumTarjetaIncorrectoException();
                }
                // Todo ok
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