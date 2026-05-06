package GUI;

import javax.swing.ImageIcon; // Importante
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import BLL.Administrador;
import BLL.Alumno;
import BLL.Contenidista;
import BLL.Profesor;
import BLL.Usuario;

class Main {
    public static void main(String[] args) {
        
    	// 1. Cargas la imagen original
    	ImageIcon imagenOriginal = new ImageIcon(Main.class.getResource("/imagenes/eduTech-logo.png"));
    	// 2. La transformas en un objeto Image para redimensionarla (ejemplo: 100x100 píxeles)
    	java.awt.Image imagenEscalada = imagenOriginal.getImage().getScaledInstance(140, 140, java.awt.Image.SCALE_SMOOTH);
    	// 3. Creas el icono final que vas a usar en el JOptionPane
    	ImageIcon logoRedimensionado = new ImageIcon(imagenEscalada);

        String[] acciones = { "Login", "Registrar", "Salir" };
        int menu = 0;
        
        do {
            // 2. Agregamos el 'logo' en el parámetro del icono (el quinto parámetro)
            menu = JOptionPane.showOptionDialog(
                null, 
                "Bienvenido al panel de usuarios de EduTech\n¿Qué desea hacer?", 
                "EduTech System", 
                JOptionPane.DEFAULT_OPTION, 
                JOptionPane.PLAIN_MESSAGE, 
                logoRedimensionado, 
                acciones, 
                acciones[0]
            );

            switch (menu) {
            case 0:
                Usuario usuario = Usuario.Login();
                
                if (usuario != null) {
                    // Todo esto sucede SOLO si el login fue exitoso
                    if (usuario instanceof Profesor) {
                        JOptionPane.showMessageDialog(null, "Bienvenido Profesor " + usuario.getNombre(), "EduTech", 1, logoRedimensionado);
                        usuario.menu();
                    } else if (usuario instanceof Alumno) {
                        JOptionPane.showMessageDialog(null, "Bienvenido Alumno " + usuario.getNombre(), "EduTech", 1, logoRedimensionado);
                        // usuario.menu(); // Si el alumno tiene menú, activalo acá
                    } else if (usuario instanceof Administrador) {
                        JOptionPane.showMessageDialog(null, "Bienvenido Administrador " + usuario.getNombre(), "EduTech", 1, logoRedimensionado);
                    } else if (usuario instanceof Contenidista) {
                        JOptionPane.showMessageDialog(null, "Bienvenido Contenidista " + usuario.getNombre(), "EduTech", 1, logoRedimensionado);
                    }
                } else {
                    // Esto sucede si el login devuelve null, usuario o contra incorrectas
                    JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
                }
                break;

                case 1: 
                   //aca seria la funcion para registrarseeeeeee
                    break;
                case 2:
                	 JOptionPane.showMessageDialog(null, "Hasta luego", "Adiós", JOptionPane.ERROR_MESSAGE);
            }
        } while (menu != 2);
    }
}