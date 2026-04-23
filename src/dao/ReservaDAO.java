package dao;

import model.Reserva;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** Operaciones sobre reservas, usa JOINs para sacar nombres */
public class ReservaDAO {

    /** Inserta una reserva */
    public boolean insertar(Reserva r) {
        String sql = "INSERT INTO reserva (fecha, hora_inicio, hora_fin, numero_plazas, motivo, observaciones, usuario_correo, recurso_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(r.getFecha()));
            ps.setTime(2, Time.valueOf(r.getHoraInicio()));
            ps.setTime(3, Time.valueOf(r.getHoraFin()));
            ps.setInt(4, r.getNumeroPlazas());
            ps.setString(5, r.getMotivo());
            ps.setString(6, r.getObservaciones());
            ps.setString(7, r.getUsuarioCorreo());
            ps.setInt(8, r.getRecursoId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[ERROR] Error insertando reserva: " + e.getMessage());
            return false;
        }
    }

    /** Modifica una reserva */
    public boolean modificar(Reserva r) {
        String sql = "UPDATE reserva SET fecha=?, hora_inicio=?, hora_fin=?, numero_plazas=?, motivo=?, observaciones=?, usuario_correo=?, recurso_id=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(r.getFecha()));
            ps.setTime(2, Time.valueOf(r.getHoraInicio()));
            ps.setTime(3, Time.valueOf(r.getHoraFin()));
            ps.setInt(4, r.getNumeroPlazas());
            ps.setString(5, r.getMotivo());
            ps.setString(6, r.getObservaciones());
            ps.setString(7, r.getUsuarioCorreo());
            ps.setInt(8, r.getRecursoId());
            ps.setInt(9, r.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[ERROR] Error modificando reserva: " + e.getMessage());
            return false;
        }
    }

    /** Elimina reserva por id */
    public boolean eliminar(int id) {
        String sql = "DELETE FROM reserva WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[ERROR] " + e.getMessage());
            return false;
        }
    }

    /** Lista todas las reservas con JOIN para nombres */
    public List<Reserva> obtenerTodos() {
        List<Reserva> lista = new ArrayList<>();
        String sql = "SELECT rv.*, u.nombre AS nombre_usuario, rc.nombre AS nombre_recurso " +
                "FROM reserva rv JOIN usuario u ON rv.usuario_correo = u.correo_electronico " +
                "JOIN recurso rc ON rv.recurso_id = rc.id ORDER BY rv.fecha, rv.hora_inicio";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("[ERROR] " + e.getMessage());
        }
        return lista;
    }

    /** Busca reservas de un usuario */
    public List<Reserva> buscarPorUsuario(String correo) {
        List<Reserva> lista = new ArrayList<>();
        String sql = "SELECT rv.*, u.nombre AS nombre_usuario, rc.nombre AS nombre_recurso " +
                "FROM reserva rv JOIN usuario u ON rv.usuario_correo = u.correo_electronico " +
                "JOIN recurso rc ON rv.recurso_id = rc.id WHERE rv.usuario_correo = ? ORDER BY rv.fecha";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, correo);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("[ERROR] " + e.getMessage());
        }
        return lista;
    }

    /** Busca reservas de un recurso */
    public List<Reserva> buscarPorRecurso(int recursoId) {
        List<Reserva> lista = new ArrayList<>();
        String sql = "SELECT rv.*, u.nombre AS nombre_usuario, rc.nombre AS nombre_recurso " +
                "FROM reserva rv JOIN usuario u ON rv.usuario_correo = u.correo_electronico " +
                "JOIN recurso rc ON rv.recurso_id = rc.id WHERE rv.recurso_id = ? ORDER BY rv.fecha";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, recursoId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("[ERROR] " + e.getMessage());
        }
        return lista;
    }

    /** Busca una reserva por su id */
    public Reserva buscarPorId(int id) {
        String sql = "SELECT rv.*, u.nombre AS nombre_usuario, rc.nombre AS nombre_recurso " +
                "FROM reserva rv JOIN usuario u ON rv.usuario_correo = u.correo_electronico " +
                "JOIN recurso rc ON rv.recurso_id = rc.id WHERE rv.id = ?";
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

    private Reserva mapear(ResultSet rs) throws SQLException {
        Reserva r = new Reserva();
        r.setId(rs.getInt("id"));
        Date f = rs.getDate("fecha");
        if (f != null) r.setFecha(f.toLocalDate());
        Time hi = rs.getTime("hora_inicio");
        Time hf = rs.getTime("hora_fin");
        if (hi != null) r.setHoraInicio(hi.toLocalTime());
        if (hf != null) r.setHoraFin(hf.toLocalTime());
        r.setNumeroPlazas(rs.getInt("numero_plazas"));
        r.setMotivo(rs.getString("motivo"));
        r.setObservaciones(rs.getString("observaciones"));
        r.setUsuarioCorreo(rs.getString("usuario_correo"));
        r.setRecursoId(rs.getInt("recurso_id"));
        // Nombres del JOIN
        try { r.setNombreUsuario(rs.getString("nombre_usuario")); } catch (SQLException ignored) {}
        try { r.setNombreRecurso(rs.getString("nombre_recurso")); } catch (SQLException ignored) {}
        return r;
    }
}
