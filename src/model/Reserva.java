package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/** Guarda los datos de una reserva */
public class Reserva {

    private int id;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private int numeroPlazas;
    private String motivo;
    private String observaciones;
    private String usuarioCorreo;
    private int recursoId;

    // Para mostrar en listados
    private String nombreUsuario;
    private String nombreRecurso;

    public Reserva() { }

    /** Getters y setters */
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }

    public LocalTime getHoraFin() { return horaFin; }
    public void setHoraFin(LocalTime horaFin) { this.horaFin = horaFin; }

    public int getNumeroPlazas() { return numeroPlazas; }
    public void setNumeroPlazas(int numeroPlazas) { this.numeroPlazas = numeroPlazas; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public String getUsuarioCorreo() { return usuarioCorreo; }
    public void setUsuarioCorreo(String usuarioCorreo) { this.usuarioCorreo = usuarioCorreo; }

    public int getRecursoId() { return recursoId; }
    public void setRecursoId(int recursoId) { this.recursoId = recursoId; }

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    public String getNombreRecurso() { return nombreRecurso; }
    public void setNombreRecurso(String nombreRecurso) { this.nombreRecurso = nombreRecurso; }

    @Override
    public String toString() {
        return String.format("| %-4d | %-20s | %-15s | %-10s | %-5s | %-5s | %-3d | %-20s |",
                id,
                nombreUsuario != null ? nombreUsuario : usuarioCorreo,
                nombreRecurso != null ? nombreRecurso : String.valueOf(recursoId),
                fecha != null ? fecha.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) : "",
                horaInicio != null ? horaInicio.toString() : "",
                horaFin != null ? horaFin.toString() : "",
                numeroPlazas,
                motivo != null ? motivo : "");
    }
}
