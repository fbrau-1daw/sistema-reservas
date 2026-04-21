package model;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

/** Sirve para guardar los datos de un usuario */
public class Usuario {

    private String correoElectronico;
    private String contrasena;
    private String nombre;
    private LocalDate fechaNacimiento;
    private String tipoUsuario;

    /** Constructor vacio */
    public Usuario() {
    }

    /** Constructor con parametros */
    public Usuario(String correoElectronico, String contrasena, String nombre,
                   LocalDate fechaNacimiento, String tipoUsuario) {
        this.correoElectronico = correoElectronico;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.tipoUsuario = tipoUsuario;
    }

    // Getters y setters

    public String getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getTipoUsuario() { return tipoUsuario; }
    public void setTipoUsuario(String tipoUsuario) { this.tipoUsuario = tipoUsuario; }

    /** Calcula la edad */
    public int getEdad() {
        if (fechaNacimiento == null) return 0;
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }

    @Override
    public String toString() {
        return String.format("| %-30s | %-25s | %-15s | %-4d | %-15s |",
                correoElectronico, nombre,
                fechaNacimiento != null ? fechaNacimiento.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) : "N/A",
                getEdad(), tipoUsuario);
    }
}
