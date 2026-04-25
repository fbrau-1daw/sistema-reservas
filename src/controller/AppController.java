package controller;

import dao.*;
import view.ConsolaView;
import view.UsuarioView;
import view.ReservaView;

/** Controlador principal, llama a los demas */
public class AppController {

    private final ConsolaView consola = new ConsolaView();
    private final UsuarioController usuarioController = new UsuarioController();
    private final RecursoController recursoController = new RecursoController();
    private final ReservaController reservaController = new ReservaController();

    // DAOs y vistas para consultas
    private final AdministradorDAO adminDAO = new AdministradorDAO();
    private final UsuarioNormalDAO normalDAO = new UsuarioNormalDAO();
    private final ReservaDAO reservaDAO = new ReservaDAO();
    private final UsuarioView usuarioView = new UsuarioView();
    private final ReservaView reservaView = new ReservaView();

    /** Inicia la aplicacion y muestra el menu principal. */
    public void iniciar() {
        System.out.println("===== SISTEMA DE GESTION DE RESERVAS =====");
        System.out.println("Proyecto Intermodular - UT11");
        System.out.println("Conexion a Bases de Datos con JDBC");
        System.out.println();

        int opcion;
        do {
            consola.mostrarMenuPrincipal();
            opcion = consola.leerOpcion();
            switch (opcion) {
                case 1:
                    usuarioController.gestionar();
                    break;
                case 2:
                    recursoController.gestionar();
                    break;
                case 3:
                    reservaController.gestionar();
                    break;
                case 4:
                    gestionarConsultas();
                    break;
                case 0:
                    System.out.println("\nCerrando conexion...");
                    DBConnection.closeConnection();
                    System.out.println("Hasta luego!");
                    break;
                default:
                    consola.mostrarError("Opcion no valida.");
            }
        } while (opcion != 0);
    }

    // Gestiona el submenu de consultas generales.
    private void gestionarConsultas() {
        int opcion;
        do {
            consola.mostrarMenuConsultas();
            opcion = consola.leerOpcion();
            switch (opcion) {
                case 1:
                    usuarioView.mostrarListaAdministradores(adminDAO.obtenerTodos());
                    break;
                case 2:
                    usuarioView.mostrarListaUsuariosNormales(normalDAO.obtenerTodos());
                    break;
                case 3:
                    String correo = consola.leerTexto("Correo del usuario: ");
                    reservaView.mostrarListaReservas(reservaDAO.buscarPorUsuario(correo));
                    break;
                case 4:
                    int recursoId = consola.leerEntero("ID del recurso: ");
                    reservaView.mostrarListaReservas(reservaDAO.buscarPorRecurso(recursoId));
                    break;
                case 5:
                    mostrarResumenGeneral();
                    break;
                case 0: break;
                default: consola.mostrarError("Opcion no valida.");
            }
        } while (opcion != 0);
    }

    // Muestra un resumen general del sistema.
    private void mostrarResumenGeneral() {
        System.out.println("\n--- RESUMEN GENERAL ---");
        System.out.println("Administradores:    " + adminDAO.obtenerTodos().size());
        System.out.println("Usuarios normales:  " + normalDAO.obtenerTodos().size());
        System.out.println("Recursos:           " + new RecursoDAO().obtenerTodos().size());
        System.out.println("Reservas:           " + reservaDAO.obtenerTodos().size());
        System.out.println("-----------------------");
    }
}
