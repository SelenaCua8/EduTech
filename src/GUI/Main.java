package GUI;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import java.awt.Image;

import BLL.Administrador;
import BLL.Alumno;
import BLL.Contenidista;
import BLL.Profesor;
import BLL.Usuario;
import DLL.ControllerUsuario;
import repository.Hashing;

public class Main {
    public static void main(String[] args) {
        
        // 1. Configuración del Logo
        ImageIcon iconoOriginal = new ImageIcon("/imagenes/eduTech-logo.png");
        Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(140, 140, Image.SCALE_SMOOTH);
        ImageIcon logo = new ImageIcon(imagenEscalada);

        String[] acciones = { "Login", "Registrar", "Salir" };
        int menu = 0;
        
        do {
            // 2. Menú Principal
            menu = JOptionPane.showOptionDialog(
                null, 
                "Bienvenido al panel de usuarios de EduTech\n¿Qué desea hacer?", 
                "EduTech System", 
                JOptionPane.DEFAULT_OPTION, 
                JOptionPane.PLAIN_MESSAGE, 
                logo, 
                acciones, 
                acciones[0]
            );

            switch (menu) {
            case 0: // LOGIN
                Usuario usuarioLogueado = Usuario.Login();
                
                if (usuarioLogueado != null) {
                    // 1. Bienvenida general (funciona para cualquier rol)
                    JOptionPane.showMessageDialog(null, "Bienvenido " + usuarioLogueado.getNombre(), "EduTech", 1, logo);
                    
                    // 2. SALTO AL MENÚ ESPECÍFICO
                    // Si es Profesor, abre el de profesor. Si es Admin, el de admin.
                    usuarioLogueado.menu(); 
                    
                } else {
                    JOptionPane.showMessageDialog(null, "Acceso denegado: Datos incorrectos o cuenta PENDIENTE.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                break;
                case 1: // REGISTRAR
                    // Pedimos datos en orden
                    String nom = JOptionPane.showInputDialog("Nombre:");
                    String ape = JOptionPane.showInputDialog("Apellido:");
                    String mail = JOptionPane.showInputDialog("Email:");
                    String pass = JOptionPane.showInputDialog("Contraseña:");
                    
                    if (nom == null || mail == null || pass == null) break;

                    // Selección de Rol
                    String[] roles = { "ALUMNO", "PROFESOR", "GUIONISTA", "ADMIN" };
                    int seleccion = JOptionPane.showOptionDialog(null, "Seleccione su rol:", "Registro",
                            0, JOptionPane.QUESTION_MESSAGE, null, roles, roles[0]);
                    
                    if (seleccion != -1) {
                        String rolElegido = roles[seleccion];
                        String passCifrada = Hashing.hash(pass);

                        ControllerUsuario con = new ControllerUsuario();
                        // Enviamos en orden: nombre, apellido, email, pass, rol
                        boolean exito = con.insertarUsuarioNuevo(nom, ape, mail, passCifrada, rolElegido);

                        if (exito) {
                            JOptionPane.showMessageDialog(null, "¡Registro enviado! Tu cuenta está PENDIENTE de aprobación.");
                        } else {
                            JOptionPane.showMessageDialog(null, "Error: El email ya está registrado o hubo un fallo en la base de datos.");
                        }
                    }
                    break;

                case 2: // SALIR
                    JOptionPane.showMessageDialog(null, "Gracias por usar EduTech System", "Adiós", JOptionPane.INFORMATION_MESSAGE);
                    break;
            }
        } while (menu != 2);
    }
}