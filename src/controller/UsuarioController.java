package controller;

import dao.UsuarioDAO;
import dao.AdministradorDAO;
import dao.UsuarioNormalDAO;
import model.Administrador;
import model.Usuario;
import model.UsuarioNormal;
import view.ConsolaView;
import view.UsuarioView;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

/** Controlador de usuarios */
public class UsuarioController {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final AdministradorDAO adminDAO = new AdministradorDAO();
    private final UsuarioNormalDAO normalDAO = new UsuarioNormalDAO();
    private final ConsolaView consola = new ConsolaView();
    private final UsuarioView vista = new UsuarioView();

    public void gestionar() {
        int opcion;
        do {
            consola.mostrarMenuUsuarios();
            opcion = consola.leerOpcion();
            switch (opcion) {
                case 1: altaAdministrador(); break;
                case 2: altaUsuarioNormal(); break;
                case 3: bajaUsuario(); break;
                case 4: modificarAdministrador(); break;
                case 5: modificarUsuarioNormal(); break;
                case 6: listarTodos(); break;
                case 7: listarAdministradores(); break;
                case 8: listarUsuariosNormales(); break;
                case 9: buscarPorNombre(); break;
                case 10: buscarPorCorreo(); break;
                case 0: break;
                default: consola.mostrarError("Opcion no valida.");
            }
        } while (opcion != 0);
    }

    private void altaAdministrador() {
        System.out.println("\n-- ALTA ADMINISTRADOR --");
        String correo = consola.leerTexto("Correo electronico: ");
        String contrasena = consola.leerTexto("Contrasena: ");
        String nombre = consola.leerTexto("Nombre completo: ");
        LocalDate fecha = leerFecha();
        String telefono = consola.leerTexto("Telefono de guardia: ");

        Administrador admin = new Administrador();
        admin.setCorreoElectronico(correo);
        admin.setContrasena(contrasena);
        admin.setNombre(nombre);
        admin.setFechaNacimiento(fecha);
        admin.setTelefonoGuardia(telefono);

        if (adminDAO.insertar(admin)) {
            consola.mostrarExito("Administrador creado.");
        } else {
            consola.mostrarError("No se pudo crear el administrador.");
        }
    }

    private void altaUsuarioNormal() {
        System.out.println("\n-- ALTA USUARIO NORMAL --");
        String correo = consola.leerTexto("Correo electronico: ");
        String contrasena = consola.leerTexto("Contrasena: ");
        String nombre = consola.leerTexto("Nombre completo: ");
        LocalDate fecha = leerFecha();
        String direccion = consola.leerTexto("Direccion: ");
        String telefono = consola.leerTexto("Telefono movil: ");
        String foto = consola.leerTexto("Fotografia (archivo): ");

        UsuarioNormal u = new UsuarioNormal();
        u.setCorreoElectronico(correo);
        u.setContrasena(contrasena);
        u.setNombre(nombre);
        u.setFechaNacimiento(fecha);
        u.setDireccion(direccion);
        u.setTelefonoMovil(telefono);
        u.setFotografia(foto.isEmpty() ? null : foto);

        if (normalDAO.insertar(u)) {
            consola.mostrarExito("Usuario normal creado.");
        } else {
            consola.mostrarError("No se pudo crear el usuario.");
        }
    }

    private void bajaUsuario() {
        System.out.println("\n-- BAJA USUARIO --");
        String correo = consola.leerTexto("Correo del usuario a eliminar: ");
        String confirmar = consola.leerTexto("Seguro? (s/n): ");
        if (confirmar.equalsIgnoreCase("s")) {
            if (usuarioDAO.eliminar(correo)) {
                consola.mostrarExito("Usuario eliminado.");
            } else {
                consola.mostrarError("No se encontro el usuario.");
            }
        } else {
            System.out.println("Operacion cancelada.");
        }
    }

    private void modificarAdministrador() {
        System.out.println("\n-- MODIFICAR ADMINISTRADOR --");
        String correo = consola.leerTexto("Correo del administrador: ");
        Administrador admin = adminDAO.buscarPorCorreo(correo);
        if (admin == null) {
            consola.mostrarError("Administrador no encontrado.");
            return;
        }
        System.out.println("Dejar vacio para mantener valor actual.");
        String nombre = consola.leerTexto("Nombre [" + admin.getNombre() + "]: ");
        if (!nombre.isEmpty()) admin.setNombre(nombre);
        String contrasena = consola.leerTexto("Contrasena: ");
        if (!contrasena.isEmpty()) admin.setContrasena(contrasena);
        String telefono = consola.leerTexto("Tel. guardia [" + admin.getTelefonoGuardia() + "]: ");
        if (!telefono.isEmpty()) admin.setTelefonoGuardia(telefono);

        if (adminDAO.modificar(admin)) {
            consola.mostrarExito("Administrador modificado.");
        } else {
            consola.mostrarError("No se pudo modificar.");
        }
    }

    private void modificarUsuarioNormal() {
        System.out.println("\n-- MODIFICAR USUARIO NORMAL --");
        String correo = consola.leerTexto("Correo del usuario: ");
        UsuarioNormal u = normalDAO.buscarPorCorreo(correo);
        if (u == null) {
            consola.mostrarError("Usuario no encontrado.");
            return;
        }
        System.out.println("Dejar vacio para mantener valor actual.");
        String nombre = consola.leerTexto("Nombre [" + u.getNombre() + "]: ");
        if (!nombre.isEmpty()) u.setNombre(nombre);
        String contrasena = consola.leerTexto("Contrasena: ");
        if (!contrasena.isEmpty()) u.setContrasena(contrasena);
        String dir = consola.leerTexto("Direccion [" + u.getDireccion() + "]: ");
        if (!dir.isEmpty()) u.setDireccion(dir);
        String tel = consola.leerTexto("Telefono [" + u.getTelefonoMovil() + "]: ");
        if (!tel.isEmpty()) u.setTelefonoMovil(tel);

        if (normalDAO.modificar(u)) {
            consola.mostrarExito("Usuario modificado.");
        } else {
            consola.mostrarError("No se pudo modificar.");
        }
    }

    private void listarTodos() { vista.mostrarListaUsuarios(usuarioDAO.obtenerTodos()); }
    private void listarAdministradores() { vista.mostrarListaAdministradores(adminDAO.obtenerTodos()); }
    private void listarUsuariosNormales() { vista.mostrarListaUsuariosNormales(normalDAO.obtenerTodos()); }

    private void buscarPorNombre() {
        String nombre = consola.leerTexto("Nombre a buscar: ");
        vista.mostrarListaUsuarios(usuarioDAO.buscarPorNombre(nombre));
    }

    private void buscarPorCorreo() {
        String correo = consola.leerTexto("Correo a buscar: ");
        Usuario u = usuarioDAO.buscarPorCorreo(correo);
        if (u != null) {
            System.out.println("\n" + u.toString());
        } else {
            consola.mostrarError("Usuario no encontrado.");
        }
    }

    private LocalDate leerFecha() {
        while (true) {
            String s = consola.leerTexto("Fecha nacimiento (DD-MM-AAAA): ");
            try {
                String[] partes = s.split("-");
                return LocalDate.of(Integer.parseInt(partes[2]), Integer.parseInt(partes[1]), Integer.parseInt(partes[0]));
            } catch (Exception e) {
                consola.mostrarError("Formato incorrecto. Usa DD-MM-AAAA");
            }
        }
    }
}
