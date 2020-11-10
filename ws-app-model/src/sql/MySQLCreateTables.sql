-- ----------------------------------------------------------------------------
-- Model
-------------------------------------------------------------------------------

DROP TABLE Carrera;
DROP TABLE Inscripcion;

-- -------------------------------- Carrera -----------------------------------
CREATE TABLE Carrera (

 ) ENGINE = InnoDB;

-- ------------------------------ Inscripcion ---------------------------------

CREATE TABLE Inscripcion (
    idInscripcion BIGINT NOT NULL AUTO_INCREMENT,
    idCarrera BIGINT NOT NULL,
    dorsal NUMERIC NOT NULL,
    numTarjrta VARCHAR(16) NOT NULL,
    fechaInscripción DATETIME NOT NULL,
    email VARCHAR(40),
    recogido BOOLEAN NOT NULL,
    CONSTRAINT InscripcionPK PRIMARY KEY(idInscripcion),
    CONSTRAINT InscripcionFK FOREIGN KEY(idCarrera)
        REFERENCES Carrera(idCarrera) ON DELETE CASCADE ) ENGINE = InnoDB;
