package dao;

import model.Usuario;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/** Operaciones sobre la tabla usuario */
public class UsuarioDAO {

    /** Inserta un usuario en la BD */
    public boolean insertar(Usuario u) {
        String sql = "INSERT INTO usuario (correo_electronico, contrasena, nombre, fecha_nacimiento, tipo_usuario) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, u.getCorreoElectronico());
            ps.setString(2, u.getContrasena());
            ps.setString(3, u.getNombre());
            ps.setDate(4, u.getFechaNacimiento() != null ? Date.valueOf(u.getFechaNacimiento()) : null);
            ps.setString(5, u.getTipoUsuario());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[ERROR] Error insertando usuario: " + e.getMessage());
            return false;
        }
    }

    /** Modifica un usuario existente */
    public boolean modificar(Usuario u) {
        String sql = "UPDATE usuario SET contrasena = ?, nombre = ?, fecha_nacimiento = ? WHERE correo_electronico = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, u.getContrasena());
            ps.setString(2, u.getNombre());
            ps.setDate(3, u.getFechaNacimiento() != null ? Date.valueOf(u.getFechaNacimiento()) : null);
            ps.setString(4, u.getCorreoElectronico());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[ERROR] Error modificando usuario: " + e.getMessage());
            return false;
        }
    }

    /** Elimina un usuario por correo */
    public boolean eliminar(String correo) {
        String sql = "DELETE FROM usuario WHERE correo_electronico = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, correo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[ERROR] Error eliminando usuario: " + e.getMessage());
            return false;
        }
    }

    /** Devuelve todos los usuarios */
    public List<Usuario> obtenerTodos() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuario ORDER BY nombre";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] Error obteniendo usuarios: " + e.getMessage());
        }
        return lista;
    }

    /** Busca usuarios por nombre */
    public List<Usuario> buscarPorNombre(String nombre) {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuario WHERE nombre LIKE ? ORDER BY nombre";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + nombre + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("[ERROR] Error buscando: " + e.getMessage());
        }
        return lista;
    }

    /** Busca un usuario por correo */
    public Usuario buscarPorCorreo(String correo) {
        String sql = "SELECT * FROM usuario WHERE correo_electronico = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, correo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapear(rs);
        } catch (SQLException e) {
            System.err.println("[ERROR] Error buscando: " + e.getMessage());
        }
        return null;
    }

    // Mapea un ResultSet a un objeto Usuario
    private Usuario mapear(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setCorreoElectronico(rs.getString("correo_electronico"));
        u.setContrasena(rs.getString("contrasena"));
        u.setNombre(rs.getString("nombre"));
        Date fecha = rs.getDate("fecha_nacimiento");
        if (fecha != null) u.setFechaNacimiento(fecha.toLocalDate());
        u.setTipoUsuario(rs.getString("tipo_usuario"));
        return u;
    }
}
