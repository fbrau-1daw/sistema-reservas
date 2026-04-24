package view;

import model.Reserva;
import java.util.List;

/** Vista de reservas */
public class ReservaView {

    /** Menu de reservas */
    public void mostrarMenu() {
        System.out.println();
        System.out.println("--- GESTION DE RESERVAS ---");
        System.out.println("1. Alta reserva");
        System.out.println("2. Baja reserva");
        System.out.println("3. Modificar reserva");
        System.out.println("4. Listar todas las reservas");
        System.out.println("5. Buscar reservas por usuario");
        System.out.println("6. Buscar reservas por recurso");
        System.out.println("0. Volver");
        System.out.println();
        System.out.print("Seleccione una opcion: ");
    }

    /** Muestra una lista de reservas */
    public void mostrarListaReservas(List<Reserva> reservas) {
        if (reservas.isEmpty()) {
            System.out.println("[INFO] No se encontraron reservas.");
            return;
        }
        System.out.println();
        System.out.printf("| %-4s | %-20s | %-15s | %-10s | %-5s | %-5s | %-3s | %-20s |%n",
                "ID", "USUARIO", "RECURSO", "FECHA", "INIC", "FIN", "PLZ", "MOTIVO");
        System.out.println("--------------------------------------------------");
        for (Reserva r : reservas) {
            System.out.println(r.toString());
        }
        System.out.println("Total: " + reservas.size() + " reserva(s)");
    }

    /** Muestra detalle de una reserva */
    public void mostrarDetalle(Reserva r) {
        if (r == null) {
            System.out.println("[INFO] Reserva no encontrada.");
            return;
        }
        System.out.println();
        System.out.println("--- DETALLE DE RESERVA ---");
        System.out.println("ID:            " + r.getId());
        System.out.println("Fecha:         " + r.getFecha());
        System.out.println("Hora inicio:   " + r.getHoraInicio());
        System.out.println("Hora fin:      " + r.getHoraFin());
        System.out.println("Plazas:        " + r.getNumeroPlazas());
        System.out.println("Motivo:        " + (r.getMotivo() != null ? r.getMotivo() : "N/A"));
        System.out.println("Observaciones: " + (r.getObservaciones() != null ? r.getObservaciones() : "N/A"));
        System.out.println("Usuario:       " + r.getUsuarioCorreo());
        System.out.println("Recurso:       " + (r.getNombreRecurso() != null ? r.getNombreRecurso() : "ID " + r.getRecursoId()));
        System.out.println("--------------------------");
    }
}
