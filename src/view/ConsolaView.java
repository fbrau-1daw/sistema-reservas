package view;

import java.util.Scanner;

/** Menus y entrada de datos por consola */
public class ConsolaView {

    private static final Scanner scanner = new Scanner(System.in);

    /** Menu principal */
    public void mostrarMenuPrincipal() {
        System.out.println();
        System.out.println("===== SISTEMA DE RESERVAS =====");
        System.out.println();
        System.out.println("1. Gestionar usuarios");
        System.out.println("2. Gestionar recursos");
        System.out.println("3. Gestionar reservas");
        System.out.println("4. Consultas y listados");
        System.out.println("0. Salir");
        System.out.println();
        System.out.print("Seleccione una opcion: ");
    }

    /** Submenu de usuarios */
    public void mostrarMenuUsuarios() {
        System.out.println();
        System.out.println("--- GESTION DE USUARIOS ---");
        System.out.println("1. Alta administrador");
        System.out.println("2. Alta usuario normal");
        System.out.println("3. Baja usuario");
        System.out.println("4. Modificar administrador");
        System.out.println("5. Modificar usuario normal");
        System.out.println("6. Listar todos los usuarios");
        System.out.println("7. Listar administradores");
        System.out.println("8. Listar usuarios normales");
        System.out.println("9. Buscar usuario por nombre");
        System.out.println("10. Buscar usuario por correo");
        System.out.println("0. Volver");
        System.out.println();
        System.out.print("Seleccione una opcion: ");
    }

    /** Submenu de consultas */
    public void mostrarMenuConsultas() {
        System.out.println();
        System.out.println("--- CONSULTAS Y LISTADOS ---");
        System.out.println("1. Listar administradores");
        System.out.println("2. Listar usuarios normales");
        System.out.println("3. Listar reservas de un usuario");
        System.out.println("4. Listar reservas de un recurso");
        System.out.println("5. Resumen general");
        System.out.println("0. Volver");
        System.out.println();
        System.out.print("Seleccione una opcion: ");
    }

    /** Lee un numero del teclado */
    public int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /** Lee un texto del teclado */
    public String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    /** Lee un numero entero */
    public int leerEntero(String mensaje) {
        System.out.print(mensaje);
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("[AVISO] Entrada no valida. Se esperaba un numero.");
            return -1;
        }
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public void mostrarExito(String mensaje) {
        System.out.println("[OK] " + mensaje);
    }

    public void mostrarError(String mensaje) {
        System.out.println("[ERROR] " + mensaje);
    }
}
