package es.udc.isd060.runfic.model.RunFicService;

import static es.udc.isd060.runfic.model.util.ModelConstants.RUNFIC_DATA_SOURCE;

import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.sql.DataSourceLocator;

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

}
