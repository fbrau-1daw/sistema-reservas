package controller;

import dao.RecursoDAO;
import model.Recurso;
import view.ConsolaView;
import view.RecursoView;

/** Controlador de recursos */
public class RecursoController {

    private final RecursoDAO recursoDAO = new RecursoDAO();
    private final ConsolaView consola = new ConsolaView();
    private final RecursoView vista = new RecursoView();

    public void gestionar() {
        int opcion;
        do {
            vista.mostrarMenu();
            opcion = consola.leerOpcion();
            switch (opcion) {
                case 1: altaRecurso(); break;
                case 2: bajaRecurso(); break;
                case 3: modificarRecurso(); break;
                case 4: listarTodos(); break;
                case 5: buscarPorNombre(); break;
                case 0: break;
                default: consola.mostrarError("Opcion no valida.");
            }
        } while (opcion != 0);
    }

    private void altaRecurso() {
        System.out.println("\n-- ALTA RECURSO --");
        String nombre = consola.leerTexto("Nombre: ");
        String descripcion = consola.leerTexto("Descripcion: ");
        String ubicacion = consola.leerTexto("Ubicacion: ");
        int capacidad = consola.leerEntero("Capacidad: ");
        if (capacidad < 0) return;

        Recurso r = new Recurso();
        r.setNombre(nombre);
        r.setDescripcion(descripcion);
        r.setUbicacion(ubicacion);
        r.setCapacidad(capacidad);

        if (recursoDAO.insertar(r)) {
            consola.mostrarExito("Recurso creado.");
        } else {
            consola.mostrarError("No se pudo crear el recurso.");
        }
    }

    private void bajaRecurso() {
        System.out.println("\n-- BAJA RECURSO --");
        listarTodos();
        int id = consola.leerEntero("ID del recurso a eliminar: ");
        if (id < 0) return;
        String confirmar = consola.leerTexto("Seguro? (s/n): ");
        if (confirmar.equalsIgnoreCase("s")) {
            if (recursoDAO.eliminar(id)) {
                consola.mostrarExito("Recurso eliminado.");
            } else {
                consola.mostrarError("No se encontro el recurso.");
            }
        }
    }

    private void modificarRecurso() {
        System.out.println("\n-- MODIFICAR RECURSO --");
        int id = consola.leerEntero("ID del recurso: ");
        Recurso r = recursoDAO.buscarPorId(id);
        if (r == null) {
            consola.mostrarError("Recurso no encontrado.");
            return;
        }
        System.out.println("Recurso: " + r.getNombre() + " - " + r.getUbicacion());
        System.out.println("Dejar vacio para mantener valor actual.");

        String nombre = consola.leerTexto("Nombre [" + r.getNombre() + "]: ");
        if (!nombre.isEmpty()) r.setNombre(nombre);
        String desc = consola.leerTexto("Descripcion [" + r.getDescripcion() + "]: ");
        if (!desc.isEmpty()) r.setDescripcion(desc);
        String ubic = consola.leerTexto("Ubicacion [" + r.getUbicacion() + "]: ");
        if (!ubic.isEmpty()) r.setUbicacion(ubic);
        String capStr = consola.leerTexto("Capacidad [" + r.getCapacidad() + "]: ");
        if (!capStr.isEmpty()) {
            try { r.setCapacidad(Integer.parseInt(capStr)); }
            catch (NumberFormatException e) { consola.mostrarError("Numero no valido."); }
        }

        if (recursoDAO.modificar(r)) {
            consola.mostrarExito("Recurso modificado.");
        } else {
            consola.mostrarError("No se pudo modificar.");
        }
    }

    private void listarTodos() { vista.mostrarListaRecursos(recursoDAO.obtenerTodos()); }

    private void buscarPorNombre() {
        String nombre = consola.leerTexto("Nombre a buscar: ");
        vista.mostrarListaRecursos(recursoDAO.buscarPorNombre(nombre));
    }
}
