package dao;

import model.Administrador;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** Operaciones sobre admins, usa transacciones porque toca 2 tablas */
public class AdministradorDAO {

    /** Inserta un admin usando una transaccion. Primero inserta en usuario, luego en administrador. Si falla algo, hace rollback. */
    public boolean insertar(Administrador admin) {
        String sqlUsuario = "INSERT INTO usuario (correo_electronico, contrasena, nombre, fecha_nacimiento, tipo_usuario) VALUES (?, ?, ?, ?, ?)";
        String sqlAdmin = "INSERT INTO administrador (correo_electronico, telefono_guardia) VALUES (?, ?)";

        Connection conn = null;
        boolean autocommitOriginal = true;

        try {
            conn = DBConnection.getConnection();
            autocommitOriginal = conn.getAutoCommit();
            // Desactivamos autocommit para manejar la transaccion
            conn.setAutoCommit(false);

            // Primer INSERT - tabla usuario
            try (PreparedStatement ps1 = conn.prepareStatement(sqlUsuario)) {
                ps1.setString(1, admin.getCorreoElectronico());
                ps1.setString(2, admin.getContrasena());
                ps1.setString(3, admin.getNombre());
                ps1.setDate(4, admin.getFechaNacimiento() != null ? Date.valueOf(admin.getFechaNacimiento()) : null);
                ps1.setString(5, "administrador");
                ps1.executeUpdate();
            }

            // Segundo INSERT - tabla administrador
            try (PreparedStatement ps2 = conn.prepareStatement(sqlAdmin)) {
                ps2.setString(1, admin.getCorreoElectronico());
                ps2.setString(2, admin.getTelefonoGuardia());
                ps2.executeUpdate();
            }

            // Todo OK -> confirmamos
            conn.commit();
            return true;

        } catch (SQLException e) {
            System.err.println("[ERROR] Error insertando admin: " + e.getMessage());
            // Algo fallo -> deshacemos todo
            try {
                if (conn != null) {
                    conn.rollback();
                    System.out.println("[INFO] Rollback realizado.");
                }
            } catch (SQLException ex) {
                System.err.println("[ERROR] Error en rollback: " + ex.getMessage());
            }
            return false;
        } finally {
            try {
                if (conn != null) conn.setAutoCommit(autocommitOriginal);
            } catch (SQLException e) { /* ignorar */ }
        }
    }

    /** Modifica un admin (tambien con transaccion) */
    public boolean modificar(Administrador admin) {
        String sqlUsuario = "UPDATE usuario SET contrasena = ?, nombre = ?, fecha_nacimiento = ? WHERE correo_electronico = ?";
        String sqlAdmin = "UPDATE administrador SET telefono_guardia = ? WHERE correo_electronico = ?";

        Connection conn = null;
        boolean autocommitOriginal = true;

        try {
            conn = DBConnection.getConnection();
            autocommitOriginal = conn.getAutoCommit();
            conn.setAutoCommit(false);

            try (PreparedStatement ps1 = conn.prepareStatement(sqlUsuario)) {
                ps1.setString(1, admin.getContrasena());
                ps1.setString(2, admin.getNombre());
                ps1.setDate(3, admin.getFechaNacimiento() != null ? Date.valueOf(admin.getFechaNacimiento()) : null);
                ps1.setString(4, admin.getCorreoElectronico());
                ps1.executeUpdate();
            }

            try (PreparedStatement ps2 = conn.prepareStatement(sqlAdmin)) {
                ps2.setString(1, admin.getTelefonoGuardia());
                ps2.setString(2, admin.getCorreoElectronico());
                ps2.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            System.err.println("[ERROR] Error modificando admin: " + e.getMessage());
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) { }
            return false;
        } finally {
            try { if (conn != null) conn.setAutoCommit(autocommitOriginal); } catch (SQLException e) { }
        }
    }

    /** Elimina un admin (el CASCADE borra de las dos tablas) */
    public boolean eliminar(String correo) {
        String sql = "DELETE FROM usuario WHERE correo_electronico = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, correo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[ERROR] Error eliminando: " + e.getMessage());
            return false;
        }
    }

    /** Lista todos los administradores */
    public List<Administrador> obtenerTodos() {
        List<Administrador> lista = new ArrayList<>();
        String sql = "SELECT u.*, a.telefono_guardia FROM usuario u " +
                     "INNER JOIN administrador a ON u.correo_electronico = a.correo_electronico ORDER BY u.nombre";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("[ERROR] " + e.getMessage());
        }
        return lista;
    }

    /** Busca admin por correo */
    public Administrador buscarPorCorreo(String correo) {
        String sql = "SELECT u.*, a.telefono_guardia FROM usuario u " +
                     "INNER JOIN administrador a ON u.correo_electronico = a.correo_electronico " +
                     "WHERE u.correo_electronico = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, correo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapear(rs);
        } catch (SQLException e) {
            System.err.println("[ERROR] " + e.getMessage());
        }
        return null;
    }

    private Administrador mapear(ResultSet rs) throws SQLException {
        Administrador a = new Administrador();
        a.setCorreoElectronico(rs.getString("correo_electronico"));
        a.setContrasena(rs.getString("contrasena"));
        a.setNombre(rs.getString("nombre"));
        Date fecha = rs.getDate("fecha_nacimiento");
        if (fecha != null) a.setFechaNacimiento(fecha.toLocalDate());
        a.setTipoUsuario(rs.getString("tipo_usuario"));
        a.setTelefonoGuardia(rs.getString("telefono_guardia"));
        return a;
    }
}
