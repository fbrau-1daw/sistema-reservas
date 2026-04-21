package model;

/** Un recurso que se puede reservar */
public class Recurso {

    private int id;
    private String nombre;
    private String descripcion;
    private String ubicacion;
    private int capacidad;

    public Recurso() { }

    /** Getters y setters */
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    public int getCapacidad() { return capacidad; }
    public void setCapacidad(int capacidad) { this.capacidad = capacidad; }

    @Override
    public String toString() {
        return String.format("| %-4d | %-20s | %-30s | %-15s | %-4d |",
                id, nombre,
                descripcion != null ? descripcion : "",
                ubicacion != null ? ubicacion : "",
                capacidad);
    }
}
