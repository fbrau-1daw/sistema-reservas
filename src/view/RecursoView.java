package view;

import model.Recurso;
import java.util.List;

/** Vista de recursos */
public class RecursoView {

    /** Menu de recursos */
    public void mostrarMenu() {
        System.out.println();
        System.out.println("--- GESTION DE RECURSOS ---");
        System.out.println("1. Alta recurso");
        System.out.println("2. Baja recurso");
        System.out.println("3. Modificar recurso");
        System.out.println("4. Listar recursos");
        System.out.println("5. Buscar recurso por nombre");
        System.out.println("0. Volver");
        System.out.println();
        System.out.print("Seleccione una opcion: ");
    }

    /** Muestra una lista de recursos */
    public void mostrarListaRecursos(List<Recurso> recursos) {
        if (recursos.isEmpty()) {
            System.out.println("[INFO] No se encontraron recursos.");
            return;
        }
        System.out.println();
        System.out.printf("| %-4s | %-20s | %-30s | %-15s | %-4s |%n",
                "ID", "NOMBRE", "DESCRIPCION", "UBICACION", "CAP");
        System.out.println("--------------------------------------------------");
        for (Recurso r : recursos) {
            System.out.println(r.toString());
        }
        System.out.println("Total: " + recursos.size() + " recurso(s)");
    }
}
