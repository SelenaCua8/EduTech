package GUI;

import javax.swing.JOptionPane;
import BLL.Usuario;
import DLL.ControllerUsuario;
import repository.Hashing;

public class Main {
    public static void main(String[] args) {
        
        String[] acciones = { "Login", "Registrar", "Salir" };
        int menu = 0;
        
        do {
            // 1. Menú Principal Limpio
            menu = JOptionPane.showOptionDialog(
                null, 
                "Bienvenido al panel de usuarios de EduTech\n¿Qué desea hacer?", 
                "EduTech System", 
                JOptionPane.DEFAULT_OPTION, 
                JOptionPane.PLAIN_MESSAGE, 
                null, // Al pasar null, Swing usa la interfaz estándar sin buscar imágenes
                acciones, 
                acciones[0]
            );

            switch (menu) {
                case 0: // LOGIN
                    Usuario usuarioLogueado = Usuario.Login();
                    
                    if (usuarioLogueado != null) {
                        // Bienvenida general estándar
                        JOptionPane.showMessageDialog(null, "Bienvenido " + usuarioLogueado.getNombre(), "EduTech", JOptionPane.INFORMATION_MESSAGE);
                        
                        // SALTO AL MENÚ ESPECÍFICO (Direcciona según el rol)
                        usuarioLogueado.menu(); 
                        
                    } else {
                        JOptionPane.showMessageDialog(null, "Acceso denegado: Datos incorrectos o cuenta PENDIENTE.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;

                case 1: // REGISTRAR
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