DROP TABLE IF EXISTS alumnos;

CREATE TABLE IF NOT EXISTS usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    boleta VARCHAR(20) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL
    );

CREATE TABLE IF NOT EXISTS estudiantes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    matricula VARCHAR(20) UNIQUE NOT NULL,
    carrera VARCHAR(50) NOT NULL
    );

CREATE TABLE IF NOT EXISTS cursos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(10) UNIQUE NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    creditos TINYINT NOT NULL
    );

CREATE TABLE IF NOT EXISTS estudiantes_cursos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    estudiante_id BIGINT NOT NULL,
    curso_id BIGINT NOT NULL,
    semestre VARCHAR(10)NOT NULL,
    calificacion DECIMAL(3,1),
    UNIQUE(estudiante_id, curso_id, semestre),
    FOREIGN KEY (estudiante_id) REFERENCES estudiantes(id) ON DELETE CASCADE,
    FOREIGN KEY (curso_id) REFERENCES cursos(id) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS clientes (
    id_cliente BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_completo VARCHAR(100),
    telefono VARCHAR(15),
    correo VARCHAR(100)
    );

CREATE TABLE IF NOT EXISTS reservas (
    id_reserva BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha_entrada DATE,
    fecha_salida DATE,
    habitacion INT,
    id_cliente BIGINT,
    FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente) ON DELETE CASCADE
    );