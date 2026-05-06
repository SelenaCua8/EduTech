package BLL;

import javax.swing.JOptionPane;
import DLL.ControllerUsuario;

public abstract  class Usuario {
    protected int id;
    protected String nombre;
    protected String apellido; // <-- AGREGADO
    protected String email;
    protected String password;
    // Quitamos 'tipo' si no lo vas a usar en la tabla de la base de datos
    
    private static ControllerUsuario controller = new ControllerUsuario();

    // Actualizamos el constructor para incluir apellido
    public Usuario(int id, String nombre, String apellido, String email, String password) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido; // <-- ASIGNADO
        this.email = email;
        this.password = password;
    }

    public Usuario() {
    }

    // --- GETTERS Y SETTERS ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; } // <-- MÉTODO QUE TE PEDÍA EL ERROR
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    @Override
    public String toString() {
        return "Usuario [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", email=" + email + "]\n";
    }

    // El método Login se mantiene igual, ya funciona con el controller
    public static Usuario Login() {
        String nombre = "";
        while (nombre.isEmpty()) {
            nombre = JOptionPane.showInputDialog("Ingrese nombre");
            if (nombre == null) return null; // Por si cancela el diálogo
            if (nombre.isEmpty()) JOptionPane.showMessageDialog(null, "Incorrecto");
        }

        String contrasenia = "";
        while (contrasenia.isEmpty()) {
            contrasenia = JOptionPane.showInputDialog("Ingrese contraseña");
            if (contrasenia == null) return null;
            if (contrasenia.isEmpty()) JOptionPane.showMessageDialog(null, "Incorrecto");
        }

        return controller.login(nombre, contrasenia);
    }

    public abstract void menu();

    public static ControllerUsuario getController() {
        return controller;
    }

    public static void setController(ControllerUsuario controller) {
        Usuario.controller = controller;
    }
}