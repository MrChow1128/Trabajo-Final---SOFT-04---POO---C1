# Sistema de Subastas – SOFT-04

Proyecto desarrollado para el curso **Programación Orientada a Objetos (SOFT-04)**  
Universidad CENFOTEC – C1-2026  

**Autores**
- Daniel Ortega Ochoa
- Yen Lip Lee Espinoza

## Descripción

Este proyecto consiste en el desarrollo de un **Sistema de Subastas** implementado en Java, como parte del curso de **Programación Orientada a Objetos**.

El sistema permite la gestión de usuarios, subastas, objetos, ofertas y órdenes, incluyendo persistencia en base de datos MySQL mediante el patrón DAO.

---

## Objetivo

Aplicar los conceptos fundamentales de Programación Orientada a Objetos:

* Abstracción
* Encapsulamiento
* Herencia
* Polimorfismo (sin uso de override según restricción del curso)
* Manejo de capas (UI, Lógica, DAO, Data Layer)

---

## Arquitectura del Sistema

El proyecto está estructurado en capas:

```
src/
└── cr.ac.ucenfotec.subastas
    ├── ui        → Interfaz de usuario (Menú, consola)
    ├── tl        → Controladores
    ├── bl
    │   ├── logic → Lógica de negocio
    │   └── dao   → Acceso a datos (DAO)
    ├── dl        → Conexión a base de datos
    ├── model     → Clases del dominio
    └── utils     → Utilidades (configuración BD)
```

---

## Tecnologías utilizadas

* Java
* MySQL
* JDBC (MySQL Connector)
* IntelliJ IDEA
* Git & GitHub

---

## Base de Datos

El sistema utiliza MySQL como motor de base de datos.

## Configuración

El archivo `bd.properties` contiene:

```
driver=jdbc:mysql:
server=localhost:3306
database=bd_subastas
user=root
password=*****
```

---

## Script de creación

Ejecutar el archivo:

```
script_tablas.sql
```

en MySQL antes de correr el proyecto.

---

## Ejecución del Proyecto

1. Clonar el repositorio:

```
git clone https://github.com/MrChow1128/Trabajo-Final---SOFT-04---POO---C1.git
```

2. Abrir en IntelliJ IDEA

3. Configurar:

   * MySQL activo
   * Usuario y contraseña en `bd.properties`
   * Driver JDBC agregado

4. Ejecutar:

```
Main.java
```

---

## Funcionalidades principales

* Registro de usuarios (coleccionistas y moderadores)
* Creación de subastas
* Publicación de objetos
* Registro de ofertas
* Generación de órdenes
* Persistencia en base de datos
* Manejo de intereses y objetos personales

---

## Consideraciones

* No se utilizó `@Override` ni `hashCode()` según restricciones del curso
* Se implementó persistencia mediante patrón DAO
* Separación clara de responsabilidades por capas

---

## Fecha
2026

---

## Estado del proyecto
Finalizado

Funcional con base de datos

Listo para evaluación
