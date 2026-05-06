package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; // Te faltaba este
import javax.swing.*; // Para el JOptionPane y los botones

import DLL.ControllerUsuario;
 // Para que reconozca el repo

public class Registro extends JFrame {
    
    // 1. Primero declaramos los componentes (los "cuadritos")
    private JTextField txtNombre, txtApellido, txtContrasenia, txtEmail;
    private JButton btnRegistrar;

    public Registro() {
        // Aquí iría toda la parte de diseño (tamaño de ventana, etc.)
        // Si usás WindowBuilder, esto se genera solo en el constructor
        
        btnRegistrar = new JButton("Registrar");

        // 2. RECIÉN AQUÍ ADENTRO va el código del botón
        btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                ControllerUsuario repo = new ControllerUsuario();
                
                repo.registrarUsuario(
                    txtNombre.getText(), 
                    txtApellido.getText(), 
                    txtContrasenia.getText(), 
                    txtEmail.getText()
                );
                
                JOptionPane.showMessageDialog(null, "¡Guardado!");
            }
        });
    }
}