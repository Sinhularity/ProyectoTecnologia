-- Solo la primera vez que se genere la BD
-- DROP DATABASE IF EXISTS lyrics;
-- WITH (FORCE) -> En caso de que los usuarios estén conectados a la BD
--
-- CREATE DATABASE lyrics;

\c lyrics; -- Conexión a la base de datos
SET search_path TO lyrics; -- Cambio de esquema y para ejecutar consultas

DROP SCHEMA IF EXISTS lyrics CASCADE;

CREATE SCHEMA lyrics;

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
    , ultima_fecha_acceso TIMESTAMP NOT NULL DEFAULT NOW()
    , fk_id_usuario INT NOT NULL
    , PRIMARY KEY (id_acceso)
);

DROP TABLE IF EXISTS AlbumCanion CASCADE;

CREATE TABLE AlbumCancion (
    id_album_cancion SERIAL NOT NULL
    , nombre_album_cancion VARCHAR(50) NOT NULL
    , PRIMARY KEY (id_album_cancion)
);

DROP TABLE IF EXISTS GeneroMusical CASCADE;

CREATE TABLE GeneroMusical (
    id_genero_musical SERIAL NOT NULL
    , nombre_genero_musical VARCHAR(20) NOT NULL UNIQUE
    , PRIMARY KEY (id_genero_musical)
);

DROP TABLE IF EXISTS Artista CASCADE;

CREATE TABLE Artista (
    id_artista SERIAL NOT NULL
    , nombre_artista VARCHAR(50) NOT NULL UNIQUE
    , PRIMARY KEY (id_artista)
);

DROP TABLE IF EXISTS Cancion CASCADE;

CREATE TABLE Cancion (
  id_cancion SERIAL NOT NULL
  , titulo_cancion VARCHAR(50) NOT NULL
  , album_cancion VARCHAR(50) DEFAULT 'Desconocido' NOT NULL
  , fecha_publicacion_cancion DATE NOT NULL
  , enlace_cancion TEXT NOT NULL
  , fk_id_album_cancion INT NOT NULL
  , fk_id_genero_musical INT NOT NULL
  , PRIMARY KEY (id_cancion)
);

DROP TABLE IF EXISTS InterpretacionDetalle;

CREATE TABLE InterpretacionDetalle (
    id_interpretacion_detalle SERIAL NOT NULL
    , fk_id_cancion INT NOT NULL
    , fk_id_artista INT NOT NULL
    , PRIMARY KEY (id_interpretacion_detalle)
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

ALTER TABLE Cancion
    ADD CONSTRAINT fk_id_almbum_cancion FOREIGN KEY (fk_id_album_cancion) REFERENCES AlbumCancion (id_album_cancion)
    , ADD CONSTRAINT fk_id_genero_musical FOREIGN KEY (fk_id_genero_musical) REFERENCES GeneroMusical (id_genero_musical);

ALTER TABLE InterpretacionDetalle
    ADD CONSTRAINT fk_id_cancion FOREIGN KEY (fk_id_cancion) REFERENCES Cancion (id_cancion)
    , ADD CONSTRAINT fk_id_artista FOREIGN KEY (fk_id_artista) REFERENCES Artista (id_artista);

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
                NOW()
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
                  NOW()
                  ,CONCAT('Fecha de acceso: ', NEW.ultima_fecha_acceso, 'para el usuario: '
                      , OLD.correo_electronico_acceso, '.')
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

CREATE PROCEDURE sp_addCancion (
    IN in_titulo_cancion VARCHAR(50)
    , IN in_album_cancion VARCHAR(50)
    , IN in_fecha_publicacion_cancion DATE
    , IN in_enlace_cancion TEXT
    , IN in_fk_id_album_cancion INT
    , IN in_fk_id_genero_musical INT
) AS $$
    BEGIN
        INSERT INTO Cancion (
            titulo_cancion
            , album_cancion
            , fecha_publicacion_cancion
            , enlace_cancion
            , fk_id_album_cancion
            , fk_id_genero_musical
        ) VALUES (
            in_titulo_cancion
            , in_album_cancion
            , in_fecha_publicacion_cancion
            , in_enlace_cancion
            , in_fk_id_album_cancion
            , in_fk_id_genero_musical
        );
    END;
$$ LANGUAGE plpgsql;

CREATE PROCEDURE sp_getSongsByALbum (
    IN in_album_cancion VARCHAR(50)
) AS $$
    BEGIN
        SELECT
            c.titulo_cancion
        FROM
            cancion c
        INNER JOIN albumcancion ac
            on c.id_cancion = ac.id_album_cancion
        WHERE
            ac.nombre_album_cancion = in_album_cancion;
    END;
$$ LANGUAGE plpgsql;

CREATE PROCEDURE sp_getSongsByArtist (
    IN in_nombre_artista VARCHAR(50)
) AS $$
    BEGIN
        SELECT
            c.album_cancion
            , c.titulo_cancion
        FROM
            artista art
        INNER JOIN interpretaciondetalle i
        ON art.id_artista = i.fk_id_artista
        INNER JOIN cancion c
        on i.fk_id_cancion = c.id_cancion
        WHERE
            nombre_artista = in_nombre_artista;
    END;
$$ LANGUAGE plpgsql;

/* >-------------------- Insertar datos ----------------------------<*/

COPY Usuario(
    apellido_paterno_usuario
    , apellido_materno_usuario
    , nombre_usuario
    , correo_electronico_usuario
    , telefono_usuario)
    FROM 'D:\Git Bash\ProyectoTecnologia\Usuario.txt'
    WITH
    (FORMAT CSV, DELIMITER '|');

COPY Acceso (
     correo_electronico_acceso
    , contrasena_acceso
    , ultima_fecha_acceso
    , fk_id_usuario
    ) FROM 'D:\Git Bash\ProyectoTecnologia\Acceso.txt'
    WITH
    (FORMAT CSV, DELIMITER '|');

\c quit; -- Desconexión de la base de datos