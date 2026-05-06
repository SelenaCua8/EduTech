package GUI;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import BLL.Alumno;
import BLL.Profesor;
import BLL.Usuario;
import DLL.ControllerUsuario;

class Main {
	//contraseñas
	//selena 1235
	//juan 123

	public static void main(String[] args) {
		 
        String[] acciones = { "Login", "Registrar", "Salir" };
        int menu = 0;
        
        do {
            menu = JOptionPane.showOptionDialog(null, "Bienvenido", null, 0, 0, null, acciones, acciones[0]);

            switch (menu) {
                case 0:
                  
                    Usuario usuario = Usuario.Login();
                    if (usuario != null) {
                        if (usuario instanceof Profesor) {
                            JOptionPane.showMessageDialog(null, "Bienvenido Profesor " + usuario.getNombre());
                            // Ir a menu de profesor
                            usuario.menu();
                        } else if (usuario instanceof Alumno) {
                            JOptionPane.showMessageDialog(null, "Bienvenido Alumno " + usuario.getNombre());
                            // Ir a menu de alumno
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
                    }
                    break;

                case 1: 
                	
                	//falta registrar :D
                    
                    break;
            }
        } while (menu != 2);
    }
}



