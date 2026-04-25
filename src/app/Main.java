package app;

import controller.AppController;

/** Clase principal, punto de entrada */
public class Main {

    public static void main(String[] args) {
        System.out.println("Iniciando Sistema de Reservas...");
        System.out.println();

        try {
            AppController appController = new AppController();
            appController.iniciar();
        } catch (Exception e) {
            System.err.println("[ERROR] " + e.getMessage());
            e.printStackTrace();
        }
    }
}
