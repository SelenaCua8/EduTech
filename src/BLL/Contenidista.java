package BLL;

import javax.swing.JOptionPane;
import java.util.List;
import DLL.ControllerRoadmap;
import repository.Validaciones;

public class Contenidista extends Usuario implements Validaciones {

    // CONSTRUCTOR PRINCIPAL: Recibe los 5 datos que la clase padre "Usuario" necesita
    public Contenidista(int id, String nombre, String apellido, String email, String password) {
        super(id, nombre, apellido, email, password); // Llama al constructor de Usuario perfectamente
    }

    @Override
    public void menu() {
        ControllerRoadmap controllerRM = new ControllerRoadmap();
        String[] opciones = { "Crear nuevo Roadmap", "Ver mis Roadmaps", "Agregar Tema/Hito", "Volver" };
        int eleccion = 0;

        do {
            eleccion = JOptionPane.showOptionDialog(null, 
                "Panel de Gestión de Contenidos\nRol: Contenidista", 
                "EduTech System", 
                JOptionPane.DEFAULT_OPTION, 
                JOptionPane.INFORMATION_MESSAGE, 
                null, opciones, opciones[0]);

            switch (eleccion) {
                case 0: // Crear Roadmap
                    String titulo = validarString("Ingrese el título del Roadmap:");
                    String desc = validarString("Ingrese descripción del curso:");
                    
                    // Usamos el ID del objeto actual (this.getId()) heredado de Usuario
                    Roadmap nuevoRM = new Roadmap(titulo, desc, this.getId());
                    controllerRM.agregarRoadmap(nuevoRM);
                    break;

                case 1: // Ver Roadmaps
                    List<Roadmap> lista = controllerRM.mostrarRoadmaps();
                    String listado = "--- MIS ROADMAPS ---\n";
                    for (Roadmap r : lista) {
                        listado += "ID: " + r.getId() + " | " + r.getTitulo() + "\n";
                    }
                    JOptionPane.showMessageDialog(null, listado);
                    break;

                case 2: // Agregar Tema
                    String idStr = JOptionPane.showInputDialog("ID del Roadmap:");
                    if(idStr == null) break;
                    
                    int idRM = Integer.parseInt(idStr);
                    String nomTema = validarString("Nombre del Tema:");
                    String descTema = validarString("Contenido/Bibliografía:");
                    
                    Tema nuevoTema = new Tema(nomTema, descTema, null, idRM);
                    controllerRM.agregarTema(nuevoTema);
                    break;
            }
        } while (eleccion != 3);
    }

    // Recordá implementar aquí los métodos que te pida la interfaz 'Validaciones'
    @Override
    public String validarString(String mensaje) {
        // Tu lógica de validación aquí (ej. JOptionPanes que no acepten vacíos)
        String entrada = "";
        while(entrada.trim().isEmpty()) {
            entrada = JOptionPane.showInputDialog(mensaje);
            if(entrada == null) return "";
        }
        return entrada;
    }
}