package view;

import model.Administrador;
import model.Usuario;
import model.UsuarioNormal;
import java.util.List;

/** Vista de usuarios */
public class UsuarioView {

    /** Muestra lista de administradores */
    public void mostrarListaAdministradores(List<Administrador> admins) {
        if (admins.isEmpty()) {
            System.out.println("[INFO] No hay administradores.");
            return;
        }
        System.out.println();
        System.out.printf("| %-30s | %-25s | %-15s | %-4s | %-15s | %-15s |%n",
                "CORREO", "NOMBRE", "NACIMIENTO", "EDAD", "TIPO", "TEL. GUARDIA");
        System.out.println("--------------------------------------------------");
        for (Administrador a : admins) {
            System.out.println(a.toString());
        }
        System.out.println("Total: " + admins.size());
    }

    /** Muestra lista de usuarios normales */
    public void mostrarListaUsuariosNormales(List<UsuarioNormal> usuarios) {
        if (usuarios.isEmpty()) {
            System.out.println("[INFO] No hay usuarios normales.");
            return;
        }
        System.out.println();
        System.out.printf("| %-30s | %-25s | %-15s | %-4s | %-15s | %-20s | %-12s |%n",
                "CORREO", "NOMBRE", "NACIMIENTO", "EDAD", "TIPO", "DIRECCION", "TELEFONO");
        System.out.println("--------------------------------------------------");
        for (UsuarioNormal u : usuarios) {
            System.out.println(u.toString());
        }
        System.out.println("Total: " + usuarios.size());
    }

    /** Muestra lista de todos los usuarios */
    public void mostrarListaUsuarios(List<Usuario> usuarios) {
        if (usuarios.isEmpty()) {
            System.out.println("[INFO] No hay usuarios.");
            return;
        }
        System.out.println();
        System.out.printf("| %-30s | %-25s | %-15s | %-4s | %-15s |%n",
                "CORREO", "NOMBRE", "NACIMIENTO", "EDAD", "TIPO");
        System.out.println("--------------------------------------------------");
        for (Usuario u : usuarios) {
            System.out.println(u.toString());
        }
        System.out.println("Total: " + usuarios.size());
    }
}
