-- Script para crear la base de datos del sistema de reservas
-- Fran Brau - 1DAW-S - Proyecto Intermodular UT11

DROP DATABASE IF EXISTS sistema_reservas;
CREATE DATABASE sistema_reservas DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE sistema_reservas;

-- tabla usuario (superclase)
CREATE TABLE usuario (
    correo_electronico VARCHAR(100) PRIMARY KEY,
    contrasena         VARCHAR(255) NOT NULL,
    nombre             VARCHAR(100) NOT NULL,
    fecha_nacimiento   DATE,
    tipo_usuario       ENUM('administrador', 'normal') NOT NULL
) ENGINE=InnoDB;

-- tabla administrador (hereda de usuario)
CREATE TABLE administrador (
    correo_electronico VARCHAR(100) PRIMARY KEY,
    telefono_guardia   VARCHAR(20),
    FOREIGN KEY (correo_electronico) REFERENCES usuario(correo_electronico)
        ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

-- tabla usuario normal (hereda de usuario)
CREATE TABLE usuarionormal (
    correo_electronico VARCHAR(100) PRIMARY KEY,
    direccion          VARCHAR(255),
    telefono_movil     VARCHAR(20),
    fotografia         VARCHAR(255),
    FOREIGN KEY (correo_electronico) REFERENCES usuario(correo_electronico)
        ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

-- tabla recurso
CREATE TABLE recurso (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    nombre      VARCHAR(100) NOT NULL,
    descripcion TEXT,
    ubicacion   VARCHAR(200),
    capacidad   INT DEFAULT 1
) ENGINE=InnoDB;

-- tabla horario
CREATE TABLE horario (
    id            INT AUTO_INCREMENT PRIMARY KEY,
    dia_semana    ENUM('Lunes','Martes','Miercoles','Jueves','Viernes','Sabado','Domingo') NOT NULL,
    hora_inicio   TIME NOT NULL,
    hora_fin      TIME NOT NULL
) ENGINE=InnoDB;

-- tabla disponibleen (relacion N:M entre recurso y horario)
CREATE TABLE disponibleen (
    recurso_id INT NOT NULL,
    horario_id INT NOT NULL,
    PRIMARY KEY (recurso_id, horario_id),
    FOREIGN KEY (recurso_id) REFERENCES recurso(id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (horario_id) REFERENCES horario(id)
        ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

-- tabla reserva
CREATE TABLE reserva (
    id                  INT AUTO_INCREMENT PRIMARY KEY,
    fecha               DATE NOT NULL,
    hora_inicio         TIME NOT NULL,
    hora_fin            TIME NOT NULL,
    numero_plazas       INT DEFAULT 1,
    motivo              VARCHAR(255),
    observaciones       TEXT,
    usuario_correo      VARCHAR(100) NOT NULL,
    recurso_id          INT NOT NULL,
    FOREIGN KEY (usuario_correo) REFERENCES usuarionormal(correo_electronico)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (recurso_id) REFERENCES recurso(id)
        ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

-- datos de prueba

-- admins
INSERT INTO usuario (correo_electronico, contrasena, nombre, fecha_nacimiento, tipo_usuario)
VALUES
    ('admin@reservas.com', 'admin123', 'Carlos Garcia Lopez', '1985-03-15', 'administrador'),
    ('superadmin@reservas.com', 'super456', 'Maria Rodriguez Perez', '1990-07-22', 'administrador');

INSERT INTO administrador (correo_electronico, telefono_guardia)
VALUES
    ('admin@reservas.com', '600111222'),
    ('superadmin@reservas.com', '600333444');

-- usuarios normales
INSERT INTO usuario (correo_electronico, contrasena, nombre, fecha_nacimiento, tipo_usuario)
VALUES
    ('juan@email.com', 'juan123', 'Juan Martinez Ruiz', '1998-01-10', 'normal'),
    ('ana@email.com', 'ana456', 'Ana Lopez Sanchez', '2000-05-20', 'normal'),
    ('pedro@email.com', 'pedro789', 'Pedro Fernandez Gil', '1995-11-30', 'normal'),
    ('laura@email.com', 'laura321', 'Laura Gomez Torres', '1999-08-14', 'normal');

INSERT INTO usuarionormal (correo_electronico, direccion, telefono_movil, fotografia)
VALUES
    ('juan@email.com', 'Calle Mayor 10, Valencia', '611222333', 'juan.jpg'),
    ('ana@email.com', 'Av. del Puerto 25, Valencia', '622333444', 'ana.jpg'),
    ('pedro@email.com', 'Plaza Espana 5, Alicante', '633444555', 'pedro.jpg'),
    ('laura@email.com', 'Calle Luna 8, Castellon', '644555666', 'laura.jpg');

-- recursos
INSERT INTO recurso (nombre, descripcion, ubicacion, capacidad)
VALUES
    ('Sala de Reuniones A', 'Sala con proyector y pizarra', 'Edificio Principal - Planta 1', 10),
    ('Sala de Reuniones B', 'Sala pequena para reuniones', 'Edificio Principal - Planta 2', 5),
    ('Laboratorio Informatica', 'Laboratorio con 30 ordenadores', 'Edificio Anexo - Planta 0', 30),
    ('Aula Magna', 'Auditorio para conferencias', 'Edificio Central', 200),
    ('Pista Deportiva', 'Pista polideportiva cubierta', 'Pabellon Deportivo', 50);

-- horarios
INSERT INTO horario (dia_semana, hora_inicio, hora_fin)
VALUES
    ('Lunes', '08:00:00', '10:00:00'),
    ('Lunes', '10:00:00', '12:00:00'),
    ('Lunes', '12:00:00', '14:00:00'),
    ('Martes', '08:00:00', '10:00:00'),
    ('Martes', '10:00:00', '12:00:00'),
    ('Miercoles', '08:00:00', '10:00:00'),
    ('Miercoles', '16:00:00', '18:00:00'),
    ('Jueves', '08:00:00', '10:00:00'),
    ('Jueves', '10:00:00', '12:00:00'),
    ('Viernes', '08:00:00', '10:00:00'),
    ('Viernes', '12:00:00', '14:00:00'),
    ('Viernes', '16:00:00', '18:00:00');

-- disponibilidad
INSERT INTO disponibleen (recurso_id, horario_id)
VALUES
    (1, 1), (1, 2), (1, 3),
    (1, 4), (1, 5),
    (2, 1), (2, 6),
    (3, 1), (3, 2), (3, 4), (3, 5), (3, 8), (3, 9),
    (4, 7), (4, 10),
    (5, 11), (5, 12);

-- reservas
INSERT INTO reserva (fecha, hora_inicio, hora_fin, numero_plazas, motivo, observaciones, usuario_correo, recurso_id)
VALUES
    ('2026-04-28', '08:00:00', '10:00:00', 5, 'Reunion de equipo', 'Necesitamos proyector', 'juan@email.com', 1),
    ('2026-04-28', '10:00:00', '12:00:00', 2, 'Tutoria', NULL, 'ana@email.com', 2),
    ('2026-04-29', '08:00:00', '10:00:00', 25, 'Clase de programacion', 'Instalar IDE Java', 'pedro@email.com', 3),
    ('2026-04-30', '16:00:00', '18:00:00', 100, 'Conferencia IA', 'Evento abierto', 'laura@email.com', 4),
    ('2026-05-02', '12:00:00', '14:00:00', 20, 'Torneo futbol sala', 'Traer equipacion', 'juan@email.com', 5);
