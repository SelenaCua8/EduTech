package BLL;

import javax.swing.JOptionPane;
import java.util.List;
import DLL.ControllerRoadmap;
import repository.Validaciones;

public class Contenidista extends Usuario implements Validaciones {

    public Contenidista(String nombre, String apellido, String email, String password, String rol) {
        super(nombre, apellido, email, password, rol);
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
                    
                    // Usamos el ID del objeto actual (this.getId())
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
}