-- Solo la primera vez que se genere la BD
-- WITH (FORCE) -> En caso de que los usuarios estén conectados a la BD
--
-- DROP DATABASE IF EXISTS lyrics;
-- CREATE DATABASE lyrics;

\c lyrics; -- Conexión a la base de datos
-- Cambio de esquema y para ejecutar consultas

DROP SCHEMA IF EXISTS lyrics CASCADE;

CREATE SCHEMA lyrics;

SET search_path TO lyrics;

/* >-------------------- Tablas ----------------------------<*/

DROP TABLE IF EXISTS Usuario CASCADE;

CREATE TABLE Usuario (
    id_usuario SERIAL NOT NULL
    , apellido_paterno_usuario VARCHAR(12) NOT NULL
    , apellido_materno_usuario VARCHAR(12) NOT NULL
    , nombre_usuario VARCHAR(20) NOT NULL
    , correo_electronico_usuario VARCHAR(50) NOT NULL UNIQUE
    , telefono_usuario VARCHAR(10) NOT NULL UNIQUE
    , PRIMARY KEY (id_usuario)
);

DROP TABLE IF EXISTS Acceso;

CREATE TABLE Acceso (
    id_acceso SERIAL NOT NULL
    , correo_electronico_acceso VARCHAR(50) NOT NULL UNIQUE
    , contrasena_acceso TEXT NOT NULL
    , ultima_fecha_acceso TIMESTAMP NOT NULL DEFAULT (CURRENT_TIMESTAMP AT TIME ZONE 'CST')
    , fk_id_usuario INT NOT NULL
    , PRIMARY KEY (id_acceso)
);

DROP TABLE IF EXISTS Cancion CASCADE;

CREATE TABLE Cancion (
  id_cancion SERIAL NOT NULL
  , titulo_cancion VARCHAR(50) NOT NULL
  , videoURL_cancion TEXT NOT NULL
  , PRIMARY KEY (id_cancion)
);

DROP TABLE IF EXISTS Biblioteca;

CREATE TABLE Biblioteca (
    id_biblioteca SERIAL NOT NULL
    , fk_id_usuario INT NOT NULL
    , fk_id_cancion INT NOT NULL
    , PRIMARY KEY (id_biblioteca)
);

DROP TABLE IF EXISTS Log;

CREATE TABLE Log (
    id_log SERIAL NOT NULL
    , fecha_log TIMESTAMP NOT NULL
    , descripcion_log VARCHAR(100) NOT NULL
    , PRIMARY KEY (id_log)
);

/* >-------------------- Llaves foráneas ----------------------------<*/
ALTER TABLE Acceso
    ADD CONSTRAINT fk_id_usuario FOREIGN KEY (fk_id_usuario) REFERENCES Usuario (id_usuario);

ALTER TABLE Biblioteca
    ADD CONSTRAINT fk_id_usuario FOREIGN KEY (fk_id_usuario) REFERENCES Usuario (id_usuario)
    , ADD CONSTRAINT fk_id_cancion FOREIGN KEY (fk_id_cancion) REFERENCES Cancion (id_cancion);

/* >-------------------- Funciones Triggers ----------------------------<*/

DROP FUNCTION IF EXISTS add_usuario CASCADE;

CREATE FUNCTION add_usuario() RETURNS TRIGGER AS $$
    BEGIN
        INSERT INTO Log (
            fecha_log
            , descripcion_log)
        VALUES (
                NOW() AT TIME ZONE 'CST'
               , 'Se ha registrado un nuevo usuario: '||NEW.nombre_usuario||'.'
        );
        RETURN NEW;
    END;
$$ LANGUAGE plpgsql;

DROP FUNCTION IF EXISTS log_acceso CASCADE;

CREATE FUNCTION log_acceso() RETURNS TRIGGER AS $$
    BEGIN
        INSERT INTO Log(
                        fecha_log
                       , descripcion_log
        ) VALUES (
                  NOW() AT TIME ZONE 'CST'
                  ,CONCAT('Fecha de acceso: ', NEW.ultima_fecha_acceso, ' para el usuario: '
                      , NEW.correo_electronico_acceso, '.')
                );
        RETURN NEW;
    END;
$$ LANGUAGE plpgsql;

DROP FUNCTION IF EXISTS hash_password CASCADE;

DROP EXTENSION IF EXISTS pgcrypto;

CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE FUNCTION hash_password() RETURNS TRIGGER AS $$
    BEGIN
        NEW.contrasena_acceso = crypt(NEW.contrasena_acceso, gen_salt('bf'));
        RETURN NEW;
    END;
$$ LANGUAGE plpgsql;

/* >-------------------- Triggers ----------------------------<*/

CREATE TRIGGER log_acceso AFTER INSERT OR UPDATE ON Acceso  -- Actualizar solo fecha de acceso
        FOR EACH ROW
        EXECUTE FUNCTION log_acceso();

CREATE TRIGGER add_usuario AFTER INSERT ON Usuario
        FOR EACH ROW
        EXECUTE FUNCTION add_usuario();

CREATE TRIGGER hash_password BEFORE INSERT OR UPDATE ON Acceso
    FOR EACH ROW
    EXECUTE FUNCTION hash_password();

/* >-------------------- Procedimientos ----------------------------<*/

DROP PROCEDURE IF EXISTS sp_updateLastLogin CASCADE;

CREATE PROCEDURE sp_updateLastLogin (IN in_correo_electronico_acceso VARCHAR(50)) AS $$
    BEGIN
        UPDATE acceso
            SET ultima_fecha_acceso = NOW() AT TIME ZONE 'CST'
        WHERE
            correo_electronico_acceso = in_correo_electronico_acceso;
    END;
$$ LANGUAGE plpgsql;

DROP PROCEDURE IF EXISTS sp_verifyHashPassword CASCADE;

CREATE PROCEDURE sp_verifyHashPassword (IN in_correo_electronico_acceso VARCHAR(50), IN in_contrasena_acceso TEXT) AS $$
    DECLARE
        is_valid BOOLEAN;
    BEGIN
        SELECT EXISTS (
            SELECT
                1
            FROM
            acceso ac
            WHERE
            ac.correo_electronico_acceso = in_correo_electronico_acceso
            AND ac.contrasena_acceso = crypt(in_contrasena_acceso, contrasena_acceso)
        ) INTO is_valid;

        IF is_valid THEN
            RAISE NOTICE 'Contraseña correcta';
        ELSE
            RAISE EXCEPTION 'Contraseña incorrecta';
        END IF;
    END;
$$ LANGUAGE plpgsql;

\c quit; -- Desconexión de la base de datos