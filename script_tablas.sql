DROP DATABASE IF EXISTS bd_subastas;
CREATE DATABASE bd_subastas;
USE bd_subastas;

CREATE TABLE t_usuarios (
                            nombre_completo VARCHAR(120) NOT NULL,
                            identificacion VARCHAR(30) NOT NULL,
                            fecha_nacimiento DATE NOT NULL,
                            contrasena VARCHAR(60) NOT NULL,
                            correo_electronico VARCHAR(120) NOT NULL,
                            tipo_usuario VARCHAR(20) NOT NULL,
                            direccion VARCHAR(200),
                            puntuacion DOUBLE,
                            CONSTRAINT pk_t_usuarios PRIMARY KEY (identificacion),
                            CONSTRAINT uq_t_usuarios_correo UNIQUE (correo_electronico)
);

CREATE TABLE t_subastas (
                            id INT NOT NULL AUTO_INCREMENT,
                            titulo VARCHAR(120) NOT NULL,
                            creador_id VARCHAR(30) NOT NULL,
                            fecha_creacion DATETIME NOT NULL,
                            activa BOOLEAN NOT NULL,
                            CONSTRAINT pk_t_subastas PRIMARY KEY (id),
                            CONSTRAINT fk_t_subastas_usuario FOREIGN KEY (creador_id)
                                REFERENCES t_usuarios(identificacion)
);

CREATE TABLE t_objetos_subasta (
                                   id INT NOT NULL AUTO_INCREMENT,
                                   id_subasta INT NOT NULL,
                                   nombre VARCHAR(120) NOT NULL,
                                   descripcion VARCHAR(300) NOT NULL,
                                   estado VARCHAR(80) NOT NULL,
                                   fecha_compra DATE NOT NULL,
                                   CONSTRAINT pk_t_objetos_subasta PRIMARY KEY (id),
                                   CONSTRAINT fk_t_objetos_subasta FOREIGN KEY (id_subasta)
                                       REFERENCES t_subastas(id)
                                       ON DELETE CASCADE
);

CREATE TABLE t_ofertas (
                           id INT NOT NULL AUTO_INCREMENT,
                           id_subasta INT NOT NULL,
                           oferente_id VARCHAR(30) NOT NULL,
                           monto DOUBLE NOT NULL,
                           fecha DATETIME NOT NULL,
                           CONSTRAINT pk_t_ofertas PRIMARY KEY (id),
                           CONSTRAINT fk_t_ofertas_subasta FOREIGN KEY (id_subasta)
                               REFERENCES t_subastas(id)
                               ON DELETE CASCADE,
                           CONSTRAINT fk_t_ofertas_usuario FOREIGN KEY (oferente_id)
                               REFERENCES t_usuarios(identificacion)
);

CREATE TABLE t_ordenes (
                           id INT NOT NULL AUTO_INCREMENT,
                           comprador_id VARCHAR(30) NOT NULL,
                           vendedor_id VARCHAR(30) NOT NULL,
                           precio_total DOUBLE NOT NULL,
                           fecha DATETIME NOT NULL,
                           CONSTRAINT pk_t_ordenes PRIMARY KEY (id),
                           CONSTRAINT fk_t_ordenes_comprador FOREIGN KEY (comprador_id)
                               REFERENCES t_usuarios(identificacion),
                           CONSTRAINT fk_t_ordenes_vendedor FOREIGN KEY (vendedor_id)
                               REFERENCES t_usuarios(identificacion)
);

CREATE TABLE t_intereses (
                             id INT NOT NULL AUTO_INCREMENT,
                             usuario_id VARCHAR(30) NOT NULL,
                             interes VARCHAR(100) NOT NULL,
                             CONSTRAINT pk_t_intereses PRIMARY KEY (id),
                             CONSTRAINT fk_t_intereses_usuario FOREIGN KEY (usuario_id)
                                 REFERENCES t_usuarios(identificacion)
                                 ON DELETE CASCADE
);

CREATE TABLE t_objetos_usuario (
                                   id INT NOT NULL AUTO_INCREMENT,
                                   usuario_id VARCHAR(30) NOT NULL,
                                   nombre VARCHAR(120) NOT NULL,
                                   descripcion VARCHAR(300) NOT NULL,
                                   estado VARCHAR(80) NOT NULL,
                                   fecha_compra DATE NOT NULL,
                                   CONSTRAINT pk_t_objetos_usuario PRIMARY KEY (id),
                                   CONSTRAINT fk_t_objetos_usuario FOREIGN KEY (usuario_id)
                                       REFERENCES t_usuarios(identificacion)
                                       ON DELETE CASCADE
);