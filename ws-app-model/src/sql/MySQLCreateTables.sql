-- ----------------------------------------------------------------------------
-- Model
-------------------------------------------------------------------------------

DROP TABLE Carrera;
DROP TABLE Inscripcion;

-- -------------------------------- Carrera -----------------------------------
CREATE TABLE Carrera (
	idCarrera BIGINT NOT NULL AUTO_INCREMENT,
    ciudadCelebracion VARCHAR(60) NOT NULL,
    descripcion VARCHAR (200) COLLATE latin1_bin NOT NULL,
    precioInscripcion FLOAT,
    fechaAlta DATETIME NOT NULL,
    fechaCelebracion DATETIME NOT NULL,
    plazasDisponibles SMALLINT,
    plazasOcupadas SMALLINT,
    CONSTRAINT CarreraPK PRIMARY KEY (idCarrera)
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
