package controller;

import dao.ReservaDAO;
import model.Reserva;
import view.ConsolaView;
import view.ReservaView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

/** Controlador de reservas */
public class ReservaController {

    private final ReservaDAO reservaDAO = new ReservaDAO();
    private final ConsolaView consola = new ConsolaView();
    private final ReservaView vista = new ReservaView();

    public void gestionar() {
        int opcion;
        do {
            vista.mostrarMenu();
            opcion = consola.leerOpcion();
            switch (opcion) {
                case 1: altaReserva(); break;
                case 2: bajaReserva(); break;
                case 3: modificarReserva(); break;
                case 4: listarTodas(); break;
                case 5: buscarPorUsuario(); break;
                case 6: buscarPorRecurso(); break;
                case 0: break;
                default: consola.mostrarError("Opcion no valida.");
            }
        } while (opcion != 0);
    }

    private void altaReserva() {
        System.out.println("\n-- ALTA RESERVA --");
        String correo = consola.leerTexto("Correo del usuario: ");
        int recursoId = consola.leerEntero("ID del recurso: ");
        if (recursoId < 0) return;

        LocalDate fecha = leerFecha("Fecha (DD-MM-AAAA): ");
        LocalTime horaInicio = leerHora("Hora inicio (HH:MM): ");
        LocalTime horaFin = leerHora("Hora fin (HH:MM): ");
        if (fecha == null || horaInicio == null || horaFin == null) return;

        int plazas = consola.leerEntero("Numero de plazas: ");
        String motivo = consola.leerTexto("Motivo: ");
        String obs = consola.leerTexto("Observaciones (opcional): ");

        Reserva r = new Reserva();
        r.setFecha(fecha);
        r.setHoraInicio(horaInicio);
        r.setHoraFin(horaFin);
        r.setNumeroPlazas(plazas);
        r.setMotivo(motivo);
        r.setObservaciones(obs.isEmpty() ? null : obs);
        r.setUsuarioCorreo(correo);
        r.setRecursoId(recursoId);

        if (reservaDAO.insertar(r)) {
            consola.mostrarExito("Reserva creada.");
        } else {
            consola.mostrarError("No se pudo crear la reserva.");
        }
    }

    private void bajaReserva() {
        System.out.println("\n-- BAJA RESERVA --");
        listarTodas();
        int id = consola.leerEntero("ID de la reserva a eliminar: ");
        if (id < 0) return;
        String confirmar = consola.leerTexto("Seguro? (s/n): ");
        if (confirmar.equalsIgnoreCase("s")) {
            if (reservaDAO.eliminar(id)) {
                consola.mostrarExito("Reserva eliminada.");
            } else {
                consola.mostrarError("No se encontro la reserva.");
            }
        }
    }

    private void modificarReserva() {
        System.out.println("\n-- MODIFICAR RESERVA --");
        int id = consola.leerEntero("ID de la reserva: ");
        Reserva r = reservaDAO.buscarPorId(id);
        if (r == null) {
            consola.mostrarError("Reserva no encontrada.");
            return;
        }
        vista.mostrarDetalle(r);
        System.out.println("Dejar vacio para mantener el valor actual.");

        String fechaStr = consola.leerTexto("Fecha DD-MM-AAAA [" + r.getFecha() + "]: ");
        if (!fechaStr.isEmpty()) {
            try {
                String[] p = fechaStr.split("-");
                r.setFecha(LocalDate.of(Integer.parseInt(p[2]), Integer.parseInt(p[1]), Integer.parseInt(p[0])));
            } catch (Exception e) { consola.mostrarError("Fecha no valida."); }
        }
        String hiStr = consola.leerTexto("Hora inicio [" + r.getHoraInicio() + "]: ");
        if (!hiStr.isEmpty()) {
            try { r.setHoraInicio(LocalTime.parse(hiStr)); }
            catch (DateTimeParseException e) { consola.mostrarError("Hora no valida."); }
        }
        String hfStr = consola.leerTexto("Hora fin [" + r.getHoraFin() + "]: ");
        if (!hfStr.isEmpty()) {
            try { r.setHoraFin(LocalTime.parse(hfStr)); }
            catch (DateTimeParseException e) { consola.mostrarError("Hora no valida."); }
        }
        String plazasStr = consola.leerTexto("Plazas [" + r.getNumeroPlazas() + "]: ");
        if (!plazasStr.isEmpty()) {
            try { r.setNumeroPlazas(Integer.parseInt(plazasStr)); }
            catch (NumberFormatException e) { consola.mostrarError("Numero no valido."); }
        }
        String motivo = consola.leerTexto("Motivo [" + r.getMotivo() + "]: ");
        if (!motivo.isEmpty()) r.setMotivo(motivo);

        if (reservaDAO.modificar(r)) {
            consola.mostrarExito("Reserva modificada.");
        } else {
            consola.mostrarError("No se pudo modificar.");
        }
    }

    private void listarTodas() { vista.mostrarListaReservas(reservaDAO.obtenerTodos()); }
    private void buscarPorUsuario() {
        String correo = consola.leerTexto("Correo del usuario: ");
        vista.mostrarListaReservas(reservaDAO.buscarPorUsuario(correo));
    }
    private void buscarPorRecurso() {
        int id = consola.leerEntero("ID del recurso: ");
        vista.mostrarListaReservas(reservaDAO.buscarPorRecurso(id));
    }

    private LocalDate leerFecha(String msg) {
        String s = consola.leerTexto(msg);
        try {
            String[] p = s.split("-");
            return LocalDate.of(Integer.parseInt(p[2]), Integer.parseInt(p[1]), Integer.parseInt(p[0]));
        } catch (Exception e) { consola.mostrarError("Formato incorrecto."); return null; }
    }

    private LocalTime leerHora(String msg) {
        String s = consola.leerTexto(msg);
        try { return LocalTime.parse(s); }
        catch (DateTimeParseException e) { consola.mostrarError("Formato incorrecto."); return null; }
    }
}
