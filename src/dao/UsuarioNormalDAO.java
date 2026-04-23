package dao;

import model.UsuarioNormal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** Operaciones sobre usuarios normales, usa transacciones */
public class UsuarioNormalDAO {

    /** Inserta un usuario normal con transaccion */
    public boolean insertar(UsuarioNormal u) {
        String sqlUsuario = "INSERT INTO usuario (correo_electronico, contrasena, nombre, fecha_nacimiento, tipo_usuario) VALUES (?, ?, ?, ?, ?)";
        String sqlNormal = "INSERT INTO usuarionormal (correo_electronico, direccion, telefono_movil, fotografia) VALUES (?, ?, ?, ?)";

        Connection conn = null;
        boolean autocommitOriginal = true;

        try {
            conn = DBConnection.getConnection();
            autocommitOriginal = conn.getAutoCommit();
            conn.setAutoCommit(false);

            // INSERT en usuario
            try (PreparedStatement ps1 = conn.prepareStatement(sqlUsuario)) {
                ps1.setString(1, u.getCorreoElectronico());
                ps1.setString(2, u.getContrasena());
                ps1.setString(3, u.getNombre());
                ps1.setDate(4, u.getFechaNacimiento() != null ? Date.valueOf(u.getFechaNacimiento()) : null);
                ps1.setString(5, "normal");
                ps1.executeUpdate();
            }

            // INSERT en usuarionormal
            try (PreparedStatement ps2 = conn.prepareStatement(sqlNormal)) {
                ps2.setString(1, u.getCorreoElectronico());
                ps2.setString(2, u.getDireccion());
                ps2.setString(3, u.getTelefonoMovil());
                ps2.setString(4, u.getFotografia());
                ps2.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            System.err.println("[ERROR] Error insertando usuario normal: " + e.getMessage());
            try { if (conn != null) { conn.rollback(); System.out.println("[INFO] Rollback realizado."); } }
            catch (SQLException ex) { }
            return false;
        } finally {
            try { if (conn != null) conn.setAutoCommit(autocommitOriginal); } catch (SQLException e) { }
        }
    }

    /** Modifica un usuario normal con transaccion */
    public boolean modificar(UsuarioNormal u) {
        String sqlUsuario = "UPDATE usuario SET contrasena = ?, nombre = ?, fecha_nacimiento = ? WHERE correo_electronico = ?";
        String sqlNormal = "UPDATE usuarionormal SET direccion = ?, telefono_movil = ?, fotografia = ? WHERE correo_electronico = ?";

        Connection conn = null;
        boolean autocommitOriginal = true;

        try {
            conn = DBConnection.getConnection();
            autocommitOriginal = conn.getAutoCommit();
            conn.setAutoCommit(false);

            try (PreparedStatement ps1 = conn.prepareStatement(sqlUsuario)) {
                ps1.setString(1, u.getContrasena());
                ps1.setString(2, u.getNombre());
                ps1.setDate(3, u.getFechaNacimiento() != null ? Date.valueOf(u.getFechaNacimiento()) : null);
                ps1.setString(4, u.getCorreoElectronico());
                ps1.executeUpdate();
            }

            try (PreparedStatement ps2 = conn.prepareStatement(sqlNormal)) {
                ps2.setString(1, u.getDireccion());
                ps2.setString(2, u.getTelefonoMovil());
                ps2.setString(3, u.getFotografia());
                ps2.setString(4, u.getCorreoElectronico());
                ps2.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            System.err.println("[ERROR] Error modificando: " + e.getMessage());
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) { }
            return false;
        } finally {
            try { if (conn != null) conn.setAutoCommit(autocommitOriginal); } catch (SQLException e) { }
        }
    }

    /** Elimina usuario normal */
    public boolean eliminar(String correo) {
        String sql = "DELETE FROM usuario WHERE correo_electronico = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, correo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[ERROR] " + e.getMessage());
            return false;
        }
    }

    /** Lista todos los usuarios normales */
    public List<UsuarioNormal> obtenerTodos() {
        List<UsuarioNormal> lista = new ArrayList<>();
        String sql = "SELECT u.*, un.direccion, un.telefono_movil, un.fotografia FROM usuario u " +
                     "INNER JOIN usuarionormal un ON u.correo_electronico = un.correo_electronico ORDER BY u.nombre";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("[ERROR] " + e.getMessage());
        }
        return lista;
    }

    /** Busca por correo */
    public UsuarioNormal buscarPorCorreo(String correo) {
        String sql = "SELECT u.*, un.direccion, un.telefono_movil, un.fotografia FROM usuario u " +
                     "INNER JOIN usuarionormal un ON u.correo_electronico = un.correo_electronico WHERE u.correo_electronico = ?";
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

    /** Busca por nombre */
    public List<UsuarioNormal> buscarPorNombre(String nombre) {
        List<UsuarioNormal> lista = new ArrayList<>();
        String sql = "SELECT u.*, un.direccion, un.telefono_movil, un.fotografia FROM usuario u " +
                     "INNER JOIN usuarionormal un ON u.correo_electronico = un.correo_electronico WHERE u.nombre LIKE ? ORDER BY u.nombre";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + nombre + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("[ERROR] " + e.getMessage());
        }
        return lista;
    }

    private UsuarioNormal mapear(ResultSet rs) throws SQLException {
        UsuarioNormal u = new UsuarioNormal();
        u.setCorreoElectronico(rs.getString("correo_electronico"));
        u.setContrasena(rs.getString("contrasena"));
        u.setNombre(rs.getString("nombre"));
        Date fecha = rs.getDate("fecha_nacimiento");
        if (fecha != null) u.setFechaNacimiento(fecha.toLocalDate());
        u.setTipoUsuario(rs.getString("tipo_usuario"));
        u.setDireccion(rs.getString("direccion"));
        u.setTelefonoMovil(rs.getString("telefono_movil"));
        u.setFotografia(rs.getString("fotografia"));
        return u;
    }
}
