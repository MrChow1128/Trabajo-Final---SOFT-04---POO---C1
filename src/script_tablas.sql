CREATE DATABASE subastas_db;
USE subastas_db;

CREATE TABLE usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre_completo VARCHAR(100),
    identificacion VARCHAR(50),
    fecha_nacimiento DATE,
    contrasena VARCHAR(100),
    correo_electronico VARCHAR(100),
    tipo_usuario VARCHAR(20),
    direccion VARCHAR(150),
    puntuacion DOUBLE
);

CREATE TABLE intereses (
    id_interes INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT,
    interes VARCHAR(100),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
);

CREATE TABLE objetos (
    id_objeto INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    descripcion VARCHAR(200),
    estado VARCHAR(50),
    fecha_compra DATE,
    id_propietario INT,
    FOREIGN KEY (id_propietario) REFERENCES usuarios(id_usuario)
);

CREATE TABLE subastas (
    id_subasta INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(100),
    id_creador INT,
    activa BOOLEAN,
    FOREIGN KEY (id_creador) REFERENCES usuarios(id_usuario)
);

CREATE TABLE subasta_objetos (
    id_subasta INT,
    id_objeto INT,
    PRIMARY KEY (id_subasta, id_objeto),
    FOREIGN KEY (id_subasta) REFERENCES subastas(id_subasta),
    FOREIGN KEY (id_objeto) REFERENCES objetos(id_objeto)
);

CREATE TABLE ofertas (
     id_oferta INT AUTO_INCREMENT PRIMARY KEY,
     id_subasta INT,
     id_oferente INT,
     monto DOUBLE,
     fecha DATETIME,
     FOREIGN KEY (id_subasta) REFERENCES subastas(id_subasta),
     FOREIGN KEY (id_oferente) REFERENCES usuarios(id_usuario)
);

CREATE TABLE ordenes (
     id_orden INT AUTO_INCREMENT PRIMARY KEY,
     id_comprador INT,
     precio_total DOUBLE,
     fecha DATETIME,
     FOREIGN KEY (id_comprador) REFERENCES usuarios(id_usuario)
);

CREATE TABLE orden_objetos (
   id_orden INT,
   id_objeto INT,
   PRIMARY KEY (id_orden, id_objeto),
   FOREIGN KEY (id_orden) REFERENCES ordenes(id_orden),
   FOREIGN KEY (id_objeto) REFERENCES objetos(id_objeto)
);