# 🎓 EduTech System - Examen Final

**EduTech** es una aplicación de escritorio desarrollada en **Java** enfocada en la gestión integral de plataformas educativas y entornos de aprendizaje virtuales. El sistema simula un entorno académico real interactuando de forma directa con una base de datos relacional para dar soporte a múltiples roles de usuario dentro de una institución.

---

## 👥 Integrantes del Equipo
* **Lucca Foglia**
* **Selena Cuadra**
* **Gabriel Diaz**
* **Victor Ascencio**

---

## 🛠️ Tecnologías Utilizadas
* **Lenguaje Principal:** Java (JDK 21)
* **Interfaz de Usuario:** Java Swing (`JOptionPane` para diálogos interactivos y modales)
* **Base de Datos:** MySQL (Gestionado a través de XAMPP / phpMyAdmin)
* **Controlador de BD:** JDBC (`mysql-connector-java`)
* **Seguridad:** Cifrado de contraseñas con `jBCrypt`
* **Control de Versiones:** Git y GitHub

---

## 📐 Arquitectura del Proyecto (Patrón en Capas)
El sistema se diseñó siguiendo una arquitectura limpia y ordenada dividida en paquetes específicos para asegurar el desacoplamiento de código:

* **`GUI` (Capa de Presentación):** Contiene el flujo principal (`Main.java`) y maneja todas las ventanas y diálogos interactivos con el usuario.
* **`BLL` (Capa de Lógica de Negocio):** Define los modelos de datos (`Usuario`, `Profesor`, `Alumno`, `Administrador`, `Contenidista`) aplicando conceptos de Programación Orientada a Objetos (POO) como **Herencia** y **Polimorfismo**.
* **`DLL` (Capa de Persistencia / Acceso a Datos):** Controladores encargados de abrir la conexión mediante el patrón *Singleton* y ejecutar consultas SQL preparadas (`PreparedStatement`) para evitar inyecciones.
* **`repository` & `utils`:** Interfaces de abstracción (como `UsuarioRepository`), utilidades de validación de cadenas y algoritmos de Hashing.

---

## ⚙️ Características y Roles del Sistema

El sistema valida el acceso mediante un módulo de login seguro que verifica credenciales cifradas y estados de cuenta (`PENDIENTE` / `ACTIVO`). Una vez dentro, adapta el menú según el rol:

1. **Administrador:** Gestión total de la plataforma, aprobación de nuevos registros, auditoría y control de usuarios (CRUD completo).
2. **Profesor:** Gestión académica focalizada. Permite revisar los cursos asignados, listar los alumnos inscriptos en sus comisiones correspondientes y auditar las entregas de exámenes o trabajos prácticos pendientes de calificación.
3. **Alumno:** Inscripción a cursos y comisiones disponibles, visualización de trayectos educativos y envío de archivos/entregas de evaluaciones.
4. **Guionista / Contenidista:** Creación y estructuración de los mapas de contenidos (`Roadmaps`), módulos temáticos y esquemas de aprendizaje.

---

## 🚀 Cómo Ejecutar el Proyecto

1. **Base de Datos:** Importar el archivo `edutech.sql` en tu servidor MySQL local mediante phpMyAdmin.
2. **Conexión:** Asegurarse de que el servicio de MySQL en XAMPP esté corriendo en el puerto por defecto (`3306`).
3. **Librerías:** Verificar que los archivos `.jar` de `jbcrypt` y `mysql-connector` estén correctamente añadidos al *Build Path* del proyecto.
4. **Ejecución:** Correr el archivo `Main.java` ubicado dentro del paquete `GUI`.
