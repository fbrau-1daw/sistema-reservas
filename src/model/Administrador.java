package model;

/** Admin, hereda de usuario */
public class Administrador extends Usuario {

    private String telefonoGuardia;

    public Administrador() { }

    /** Getter y setter */
    public String getTelefonoGuardia() { return telefonoGuardia; }
    public void setTelefonoGuardia(String telefonoGuardia) { this.telefonoGuardia = telefonoGuardia; }

    @Override
    public String toString() {
        return super.toString() + String.format(" %-15s |", telefonoGuardia != null ? telefonoGuardia : "N/A");
    }
}
