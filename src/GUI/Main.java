package GUI;

import javax.swing.JOptionPane;
import BLL.Usuario;
import BLL.Contenidista;
import BLL.Profesor;
import BLL.Alumno;
import DLL.ControllerUsuario;
import repository.Hashing;

public class Main {
    public static void main(String[] args) {

        String[] acciones = { "Login", "Registrar", "Salir" };
        int menu = 0;
        
        // Instanciamos el controlador para poder usar el login real
        ControllerUsuario controlUsuario = new ControllerUsuario(); 

        do {
            // 1. Menú Principal Limpio
            menu = JOptionPane.showOptionDialog(
                null, 
                "Bienvenido al panel de usuarios de EduTech\n¿Qué desea hacer?", 
                "EduTech System", 
                JOptionPane.DEFAULT_OPTION, 
                JOptionPane.PLAIN_MESSAGE, 
                null, // Swing usa la interfaz estándar sin buscar imágenes
                acciones, 
                acciones[0]
            );

            switch (menu) {
                case 0: // LOGIN
                    // FIX 1: Pedir las credenciales por pantalla usando JOptionPane
                    String emailLogin = JOptionPane.showInputDialog("Ingrese su Email:");
                    String passLogin = JOptionPane.showInputDialog("Ingrese su Contraseña:");
                    
                    if (emailLogin == null || passLogin == null) break;

                    // Validamos contra la base de datos usando el controlador
                    Usuario usuarioLogueado = controlUsuario.login(emailLogin, passLogin);
                    
                    if (usuarioLogueado != null) {
                        JOptionPane.showMessageDialog(null, "Bienvenido " + usuarioLogueado.getNombre(), "EduTech", JOptionPane.INFORMATION_MESSAGE);
                        
                        // FIX 2 (Clase Fantasma): Filtramos por rol usando instanceof (Herencia)
                        // Esto conecta tu menú y pone un "parche" seguro en los que faltan.
                        if (usuarioLogueado instanceof Contenidista) {
                            // Carga TU menú ya estructurado y terminado
                            MenuGuionista.mostrarMenu(usuarioLogueado.getId());
                            
                        } else if (usuarioLogueado instanceof Profesor) {
                            // Parche temporal para que a Selena no le salte error
                            JOptionPane.showMessageDialog(null, "Menú de Profesor en construcción para la 3ra entrega...", "Aviso MVP", JOptionPane.WARNING_MESSAGE);
                            
                        } else if (usuarioLogueado instanceof Alumno) {
                            // Parche temporal para contener el código faltante de Lucca
                            JOptionPane.showMessageDialog(null, "Menú de Alumno en construcción para la 3ra entrega...", "Aviso MVP", JOptionPane.WARNING_MESSAGE);
                            
                        } else {
                            JOptionPane.showMessageDialog(null, "Panel de Administrador en construcción...", "Aviso MVP", JOptionPane.WARNING_MESSAGE);
                        }
                        
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

                        boolean exito = controlUsuario.insertarUsuarioNuevo(nom, ape, mail, passCifrada, rolElegido);

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
        } while (menu != 2 && menu != JOptionPane.CLOSED_OPTION);
    }
}