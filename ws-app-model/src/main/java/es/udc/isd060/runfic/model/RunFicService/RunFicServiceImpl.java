package es.udc.isd060.runfic.model.RunFicService;

import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.sql.DataSourceLocator;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import es.udc.isd060.runfic.model.carrera.*;
import es.udc.isd060.runfic.model.inscripcion.*;
import es.udc.ws.util.validation.PropertyValidator;


public class RunFicServiceImpl implements RunFicService {

    private final DataSource dataSource;
    private SqlCarreraDao carreraDao = null;
    private SqlInscripcionDao inscripcionDao = null;

    public MovieServiceImpl() {
        dataSource = DataSourceLocator.getDataSource(MOVIE_DATA_SOURCE);
        carreraDao = SqlCarreraDaoFactory.getDao();
        inscripcionDao = SqlInscripcionDaoFactory.getDao();
    }

    private void validateInscripcion(@org.jetbrains.annotations.NotNull Inscripcion i) throws InputValidationException {

        if (i.getTarjeta().length()<>16) throw new InputValidationException();
        PropertyValidator.validateMandatoryString("email", i.getEmail());
        if (! i.getEmail().contains("@")) throw new InputValidationException();
    }

}
