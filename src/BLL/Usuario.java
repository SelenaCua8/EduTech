package BLL;

import javax.swing.JOptionPane;

import DLL.ControllerUsuario;
import repository.Hashing;

public abstract  class Usuario {
    protected int id;
    protected String nombre;
    protected String apellido;
    protected String email;
    protected String password;
 
    
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

    public String getApellido() { return apellido; } 
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    @Override
    public String toString() {
        return "Usuario [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", email=" + email + "]\n";
    }

    
    public static Usuario Login() {
        String email = "";
        while (email.isEmpty()) {
            email = JOptionPane.showInputDialog("Ingrese email");
            if (email == null) return null; // Por si cancela el diálogo
            if (email.isEmpty()) JOptionPane.showMessageDialog(null, "Debe ingresar un nombre");
        }

        String contrasenia = "";
        while (contrasenia.isEmpty()) {
            contrasenia = JOptionPane.showInputDialog("Ingrese contraseña");
            if (contrasenia == null) return null;
            if (contrasenia.isEmpty()) JOptionPane.showMessageDialog(null, "No puedo estar vacío esto");
        }

        return controller.login(email, contrasenia);
    }

    public abstract void menu();

    public static ControllerUsuario getController() {
        return controller;
    }

    public static void setController(ControllerUsuario controller) {
        Usuario.controller = controller;
    }


    public static void Registro() {
        ControllerUsuario con = new ControllerUsuario();

        // 1. Captura y validación inmediata del Email
        String mail = JOptionPane.showInputDialog("Ingrese su Email:");
        if (mail == null || mail.trim().isEmpty()) return; // Si cancela, sale

        // Verificamos si el mail ya existe en la DB
        if (con.existeEmail(mail)) {
            JOptionPane.showMessageDialog(null, "Error: Ya existe un usuario registrado con el mail: " + mail, "Email Duplicado", JOptionPane.ERROR_MESSAGE);
            return; // Corta el proceso aquí
        }

        // 2. Captura del resto de los datos
        String nom = JOptionPane.showInputDialog("Ingrese su Nombre:");
        String ape = JOptionPane.showInputDialog("Ingrese su Apellido:");
        String pass = JOptionPane.showInputDialog("Ingrese su Contraseña:");
        
        if (nom == null || ape == null || pass == null) return;

        // 3. Selección de Rol con protección de cierre
        String[] roles = { "ALUMNO", "PROFESOR", "GUIONISTA", "ADMIN" };
        int seleccion = JOptionPane.showOptionDialog(null, "Seleccione su función en EduTech:", "Registro de Rol",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, roles, roles[0]);
        
        if (seleccion == -1) { // Si cierra la ventana sin elegir
            JOptionPane.showMessageDialog(null, "Registro cancelado: Debe elegir un rol.");
            return;
        }
        String rolElegido = roles[seleccion];

        // 4. Ciframos la contraseña (Usando tu interfaz Hashing)
        String passCifrada = Hashing.hash(pass);

        // 5. Mandamos a la DLL (
        boolean exito = con.insertarUsuarioNuevo(nom, ape, mail, passCifrada, rolElegido);

        if (exito) {
            JOptionPane.showMessageDialog(null, "¡Solicitud enviada con éxito!\nEstado: PENDIENTE.\nUn administrador debe aprobar tu acceso.");
        } else {
            JOptionPane.showMessageDialog(null, "Error crítico al guardar en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}