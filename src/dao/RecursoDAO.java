package dao;

import model.Recurso;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** Operaciones sobre la tabla recurso */
public class RecursoDAO {

    /** Inserta un recurso */
    public boolean insertar(Recurso r) {
        String sql = "INSERT INTO recurso (nombre, descripcion, ubicacion, capacidad) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, r.getNombre());
            ps.setString(2, r.getDescripcion());
            ps.setString(3, r.getUbicacion());
            ps.setInt(4, r.getCapacidad());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[ERROR] Error insertando recurso: " + e.getMessage());
            return false;
        }
    }

    /** Modifica un recurso */
    public boolean modificar(Recurso r) {
        String sql = "UPDATE recurso SET nombre=?, descripcion=?, ubicacion=?, capacidad=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, r.getNombre());
            ps.setString(2, r.getDescripcion());
            ps.setString(3, r.getUbicacion());
            ps.setInt(4, r.getCapacidad());
            ps.setInt(5, r.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[ERROR] Error modificando recurso: " + e.getMessage());
            return false;
        }
    }

    /** Elimina un recurso por id */
    public boolean eliminar(int id) {
        String sql = "DELETE FROM recurso WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[ERROR] Error eliminando recurso: " + e.getMessage());
            return false;
        }
    }

    /** Devuelve todos los recursos */
    public List<Recurso> obtenerTodos() {
        List<Recurso> lista = new ArrayList<>();
        String sql = "SELECT * FROM recurso ORDER BY nombre";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("[ERROR] " + e.getMessage());
        }
        return lista;
    }

    /** Busca recurso por id */
    public Recurso buscarPorId(int id) {
        String sql = "SELECT * FROM recurso WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapear(rs);
        } catch (SQLException e) {
            System.err.println("[ERROR] " + e.getMessage());
        }
        return null;
    }

    /** Busca recursos por nombre */
    public List<Recurso> buscarPorNombre(String nombre) {
        List<Recurso> lista = new ArrayList<>();
        String sql = "SELECT * FROM recurso WHERE nombre LIKE ? ORDER BY nombre";
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

    private Recurso mapear(ResultSet rs) throws SQLException {
        Recurso r = new Recurso();
        r.setId(rs.getInt("id"));
        r.setNombre(rs.getString("nombre"));
        r.setDescripcion(rs.getString("descripcion"));
        r.setUbicacion(rs.getString("ubicacion"));
        r.setCapacidad(rs.getInt("capacidad"));
        return r;
    }
}
