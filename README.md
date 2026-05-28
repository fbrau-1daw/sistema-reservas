# 🗓️ Sistema de Gestión de Reservas

> Proyecto Intermodular — 1º DAW Semipresencial · IES Severo Ochoa · Curso 2025/2026

Aplicación de consola en **Java** para gestionar reservas de recursos (salas, laboratorios, pistas deportivas...). Desarrollada con arquitectura **MVC**, conexión **JDBC** a **MariaDB** y diseño orientado a objetos con herencia.

---

## 🏗️ Arquitectura

```
┌─────────────┐     ┌──────────────────┐     ┌─────────────┐     ┌──────────┐
│    Vista     │────▶│   Controlador    │────▶│     DAO     │────▶│ MariaDB  │
│ (ConsolaView)│◀────│ (AppController)  │◀────│ (ReservaDAO)│◀────│  (JDBC)  │
└─────────────┘     └──────────────────┘     └─────────────┘     └──────────┘
```

## ⚙️ Tecnologías

| Componente | Tecnología |
|---|---|
| Lenguaje | Java 17+ |
| Base de datos | MariaDB / MySQL |
| Conector | MySQL Connector/J 9.1.0 |
| Patrón | MVC (Model-View-Controller) |
| IDE | IntelliJ IDEA |

## 🚀 Cómo ejecutar

### Requisitos previos
- Java JDK 17 o superior
- MariaDB o MySQL instalado y corriendo
- IntelliJ IDEA (o cualquier IDE Java)

### Pasos

1. **Crear la base de datos:**
   ```sql
   -- Importar el script SQL en tu gestor de BD
   source sql/sistema_reservas.sql;
   ```
   O importar `sql/sistema_reservas.sql` desde phpMyAdmin.

2. **Configurar la conexión** en `src/dao/DBConnection.java`:
   ```java
   private static final String URL  = "jdbc:mysql://localhost:3306/sistema_reservas";
   private static final String USER = "root";
   private static final String PASSWORD = "";
   ```

3. **Añadir el conector JDBC**: Agregar `lib/mysql-connector-j-9.1.0.jar` como librería del proyecto en IntelliJ.

4. **Ejecutar**: Lanzar `src/app/Main.java`.

## 🗃️ Estructura del proyecto

```
SistemaReservas/
├── src/
│   ├── app/
│   │   └── Main.java              # Punto de entrada
│   ├── model/
│   │   ├── Usuario.java           # Clase base (herencia)
│   │   ├── Administrador.java     # Extiende Usuario
│   │   ├── UsuarioNormal.java     # Extiende Usuario
│   │   ├── Recurso.java           # Salas, laboratorios...
│   │   └── Reserva.java           # Reserva de un recurso
│   ├── dao/
│   │   ├── DBConnection.java      # Singleton de conexión
│   │   ├── UsuarioDAO.java        # CRUD usuarios
│   │   ├── AdministradorDAO.java  # CRUD administradores
│   │   ├── UsuarioNormalDAO.java  # CRUD usuarios normales
│   │   ├── RecursoDAO.java        # CRUD recursos
│   │   └── ReservaDAO.java        # CRUD reservas
│   ├── controller/
│   │   ├── AppController.java     # Controlador principal
│   │   ├── UsuarioController.java # Gestión de usuarios
│   │   ├── RecursoController.java # Gestión de recursos
│   │   └── ReservaController.java # Gestión de reservas
│   └── view/
│       ├── ConsolaView.java       # Menús y entrada de datos
│       ├── UsuarioView.java       # Visualización de usuarios
│       ├── RecursoView.java       # Visualización de recursos
│       └── ReservaView.java       # Visualización de reservas
├── sql/
│   └── sistema_reservas.sql       # Script de creación de BD
├── lib/
│   └── mysql-connector-j-9.1.0.jar
└── README.md
```

## 🗄️ Base de datos

La BD contiene 7 tablas con relaciones de herencia y N:M:

- `usuario` — tabla base con tipo discriminador
- `administrador` — hereda de usuario (teléfono de guardia)
- `usuarionormal` — hereda de usuario (dirección, teléfono, foto)
- `recurso` — salas, laboratorios, pistas...
- `horario` — franjas horarias disponibles
- `disponibleen` — relación N:M entre recurso y horario
- `reserva` — vincula usuario + recurso + fecha/hora

## ✅ Funcionalidades

- CRUD completo de usuarios (administradores y normales)
- CRUD completo de recursos
- CRUD completo de reservas
- Búsqueda por nombre, correo, recurso
- Consultas cruzadas (reservas por usuario, por recurso)
- Resumen general del sistema
- Herencia de clases Java (Usuario → Administrador / UsuarioNormal)
- Patrón Singleton para la conexión a BD

## 📂 Repositorios relacionados

| Repositorio | Descripción |
|---|---|
| [sistema-reservas](https://github.com/fbrau-1daw/sistema-reservas) | 👈 Este repositorio — App Java |
| [nexcode-web](https://github.com/fbrau-1daw/nexcode-web) | Web corporativa NexCode Solutions (HTML/CSS) |

---

**Autor:** Fran Brau · 1DAW-S · IES Severo Ochoa · 2025/2026
