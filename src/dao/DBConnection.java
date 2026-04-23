package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** Conexion a la BD */
public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/sistema_reservas";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static Connection connection;

    /** Devuelve la conexion, si no hay la crea */
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("[INFO] Conexion establecida con la base de datos.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("[ERROR] No se pudo conectar: " + e.getMessage());
        }
        return connection;
    }

    /** Cierra la conexion */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] Error al cerrar conexion: " + e.getMessage());
        }
    }
}
