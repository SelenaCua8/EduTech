package BLL;

import javax.swing.JOptionPane;
import java.util.ArrayList; // Necesario para recibir los datos de las consultas de la BD
import DLL.ControllerProfesor; // Asegurate de importar tu nuevo controlador
import repository.Validaciones;

public class Profesor extends Usuario implements Validaciones {
    
    // Instanciamos el controlador específico para el Profesor
    private ControllerProfesor controllerProf = new ControllerProfesor();

    // Constructor de la clase
    public Profesor(int id, String nombre, String email, String tipo, String password) {
        super(id, nombre, email, tipo, password);
    }

    @Override
    public String toString() {
        return "Profesor [toString()=" + super.toString() + "]";
    }

    @Override
    public void menu() {
        // Un menú directo, limpio y exclusivo para las tareas del Profesor
        String[] opciones = {
            "1. Ver mis Materias", 
            "2. Ver Alumnos y Progreso", 
            "3. Ver Correcciones Pendientes",
            "4. Salir"
        };
        int opcion;
        
        do {
            opcion = JOptionPane.showOptionDialog(
                null, 
                "Panel del Profesor: " + this.getNombre() + "\n¿Qué desea gestionar hoy?", 
                "EduTech System", 
                0, 
                JOptionPane.PLAIN_MESSAGE, 
                null, 
                opciones, 
                opciones[0]
            );
            
            switch (opcion) {
                case 0:
                    this.verMisMaterias();
                    break;
                case 1:
                    this.verAlumnosYProgreso();
                    break;
                case 2: 
                    this.verCorreccionesPendientes();
                    break;
                default:
                    break;
            }
        } while (opcion != 3); 
    }

    // =======================================================
    //   FUNCIONES EXCLUSIVAS DE GESTIÓN ACADÉMICA 
    // =======================================================

    // FUNCIÓN 1: Ver materias que tiene que dar
    public void verMisMaterias() {
        // CORREGIDO: Usamos controllerProf para llamar al método específico
        ArrayList<String> materias = this.controllerProf.obtenerMateriasPorProfesor(this.getId());
        
        if (materias == null || materias.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No tienes materias asignadas en este ciclo.", "Mis Materias", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        String mensaje = "--- TUS MATERIAS ASIGNADAS ---\n";
        for (String materia : materias) {
            mensaje += "• " + materia + "\n";
        }
        JOptionPane.showMessageDialog(null, mensaje, "Mis Materias", JOptionPane.INFORMATION_MESSAGE);
    }

    // FUNCIÓN 2: Ver alumnos de sus materias y su progreso
    public void verAlumnosYProgreso() {
        // CORREGIDO: Usamos controllerProf y limpiamos la condición del 'if'
        ArrayList<String> alumnosProgreso = this.controllerProf.obtenerProgresoAlumnos(this.getId());
        
        if (alumnosProgreso == null || alumnosProgreso.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay alumnos registrados en tus comisiones actualmente.", "Progreso de Alumnos", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        String mensaje = "--- PROGRESO DE ALUMNOS POR MATERIA ---\n";
        for (String linea : alumnosProgreso) {
            mensaje += linea + "\n";
        }
        JOptionPane.showMessageDialog(null, mensaje, "Progreso de Alumnos", JOptionPane.INFORMATION_MESSAGE);
    }

    // FUNCIÓN 3: Ver correcciones pendientes (Entregas sin nota)
    public void verCorreccionesPendientes() {
        // CORREGIDO: Usamos controllerProf para vincularlo a las consultas SQL del docente
        ArrayList<String> pendientes = this.controllerProf.obtenerCorreccionesPendientes(this.getId());
        
        if (pendientes == null || pendientes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "¡Al día! No tienes trabajos prácticos o exámenes pendientes de corregir.", "Correcciones", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        String mensaje = "--- ENTREGAS PENDIENTES DE CALIFICAR ---\n";
        for (String entrega : pendientes) {
            mensaje += "► " + entrega + "\n";
        }
        JOptionPane.showMessageDialog(null, mensaje, "Correcciones Pendientes", JOptionPane.WARNING_MESSAGE);
    }
}
