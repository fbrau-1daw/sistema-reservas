package model;

/** Usuario normal, hereda de usuario */
public class UsuarioNormal extends Usuario {

    private String direccion;
    private String telefonoMovil;
    private String fotografia;

    public UsuarioNormal() { }

    /** Getters y setters */
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getTelefonoMovil() { return telefonoMovil; }
    public void setTelefonoMovil(String telefonoMovil) { this.telefonoMovil = telefonoMovil; }

    public String getFotografia() { return fotografia; }
    public void setFotografia(String fotografia) { this.fotografia = fotografia; }

    @Override
    public String toString() {
        return super.toString() + String.format(" %-20s | %-12s |",
                direccion != null ? direccion : "N/A",
                telefonoMovil != null ? telefonoMovil : "N/A");
    }
}
