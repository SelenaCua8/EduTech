package BLL;

import javax.swing.JOptionPane;

import DLL.ControllerCoordinador;
import repository.Validaciones;

public class Contenidista extends Usuario implements Validaciones {
	
	private ControllerCoordinador controllerCoord = new ControllerCoordinador();

	public Contenidista(int id, String nom, String ape, String mail, String pass) {
		// TODO Auto-generated constructor stub
		super(id, nom, ape, mail, pass);
	}
	
	@Override
	public void menu() {

		 String[] opciones = {
		            "Validar Cursos",
		            "Gestionar Comisiones",
		            "Asignar Docente a Comision",
		            "Control de Alumnos",
		            "Salir"
		        };
		 
		        int opcion;
		 
		        do {
		            opcion = JOptionPane.showOptionDialog(null,
		                "Bienvenido, Coordinador " + this.getNombre() + "\n¿Qué desea hacer?",
		                "Menu Coordinador",
		                0, JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);
		 
		            switch (opcion) {
		                case 0: menuValidarCursos(); break;
		                case 1: menuGestionarComisiones(); break;
		                case 2: asignarDocente(); break;
		                case 3: menuControlAlumnos(); break;
		                default: break;
		            }
		        } while (opcion != 4 && opcion != -1);
		
	}
	
	// ─────────────────────────────────────────────
    // 1. VALIDACIÓN DE CURSOS
    // ─────────────────────────────────────────────
    private void menuValidarCursos() {
        String[] opciones = {"Ver todos los cursos", "Crear nuevo curso", "Volver"};
        int op = JOptionPane.showOptionDialog(null, "Gestión de Cursos",
            "Cursos", 0, JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);
 
        switch (op) {
            case 0: verCursos(); break;
            case 1: crearCurso(); break;
            default: break;
        }
    }
 
    private void verCursos() {
        String lista = controllerCoord.listarCursos();
        JOptionPane.showMessageDialog(null, lista.isEmpty() ? "No hay cursos cargados." : lista,
            "Cursos disponibles", JOptionPane.INFORMATION_MESSAGE);
    }
 
    private void crearCurso() {
        String titulo = this.validarString("Titulo del curso:");
        String descripcion = this.validarString("Descripcion:");
        String nivel = this.validarString("Nivel (Inicial / Intermedio / Avanzado):");
 
        boolean ok = controllerCoord.insertarCurso(titulo, descripcion, nivel);
        JOptionPane.showMessageDialog(null,
            ok ? "✅ Curso creado con exito." : "❌ Error al crear el curso.");
    }
 
    // ─────────────────────────────────────────────
    // 2. GESTIÓN DE COMISIONES
    // ─────────────────────────────────────────────
    private void menuGestionarComisiones() {
        String[] opciones = {"Ver comisiones", "Crear comision", "Volver"};
        int op = JOptionPane.showOptionDialog(null, "Gestión de Comisiones",
            "Comisiones", 0, JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);
 
        switch (op) {
            case 0: verComisiones(); break;
            case 1: crearComision(); break;
            default: break;
        }
    }
 
    private void verComisiones() {
        String lista = controllerCoord.listarComisiones();
        JOptionPane.showMessageDialog(null, lista.isEmpty() ? "No hay comisiones cargadas." : lista,
            "Comisiones", JOptionPane.INFORMATION_MESSAGE);
    }
 
    private void crearComision() {
        // Mostrar cursos disponibles primero
        String cursos = controllerCoord.listarCursos();
        JOptionPane.showMessageDialog(null, "Cursos disponibles:\n" + cursos, "Seleccionar curso", JOptionPane.INFORMATION_MESSAGE);
 
        try {
            String idCursoStr = this.validarString("Ingrese el ID del curso:");
            int idCurso = Integer.parseInt(idCursoStr);
 
            String fechaInicio = this.validarString("Fecha de inicio (AAAA-MM-DD):");
            String fechaFin = this.validarString("Fecha de fin (AAAA-MM-DD):");
            String fechaSoporte = this.validarString("Fecha limite de soporte (AAAA-MM-DD):");
 
            boolean ok = controllerCoord.insertarComision(idCurso, fechaInicio, fechaFin, fechaSoporte);
            JOptionPane.showMessageDialog(null,
                ok ? "✅ Comision creada con exito." : "❌ Error al crear la comision.");
 
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "❌ El ID debe ser un número.");
        }
    }
 
    // ─────────────────────────────────────────────
    // 3. ASIGNACIÓN DOCENTE
    // ─────────────────────────────────────────────
    private void asignarDocente() {
        String comisiones = controllerCoord.listarComisiones();
        JOptionPane.showMessageDialog(null, "Comisiones:\n" + comisiones, "Comisiones", JOptionPane.INFORMATION_MESSAGE);
 
        String profesores = controllerCoord.listarProfesores();
        JOptionPane.showMessageDialog(null, "Profesores activos:\n" + profesores, "Profesores", JOptionPane.INFORMATION_MESSAGE);
 
        try {
            String idComisionStr = this.validarString("Ingrese el ID de la comision:");
            int idComision = Integer.parseInt(idComisionStr);
 
            String idDocenteStr = this.validarString("Ingrese el ID del docente:");
            int idDocente = Integer.parseInt(idDocenteStr);
 
            boolean ok = controllerCoord.asignarDocente(idComision, idDocente);
            JOptionPane.showMessageDialog(null,
                ok ? "✅ Docente asignado con exito." : "❌ Error al asignar el docente.");
 
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "❌ El ID debe ser un número.");
        }
    }
	
    // ─────────────────────────────────────────────
    // 4. CONTROL DE ALUMNOS
    // ─────────────────────────────────────────────
    private void menuControlAlumnos() {
        String[] opciones = {"Ver alumnos pendientes", "Activar alumno", "Ver alumnos de una comision", "Inscribir alumno", "Volver"};
        int op = JOptionPane.showOptionDialog(null, "Control de Alumnos",
            "Alumnos", 0, JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);
 
        switch (op) {
            case 0: verAlumnosPendientes(); break;
            case 1: activarAlumno(); break;
            case 2: verAlumnosComision(); break;
            case 3: inscribirAlumno(); break;
            default: break;
        }
    }
 
    private void verAlumnosPendientes() {
        String lista = controllerCoord.listarAlumnosPendientes();
        JOptionPane.showMessageDialog(null, lista.isEmpty() ? "No hay alumnos pendientes." : lista,
            "Alumnos Pendientes", JOptionPane.INFORMATION_MESSAGE);
    }
 
    private void activarAlumno() {
        String lista = controllerCoord.listarAlumnosPendientes();
        JOptionPane.showMessageDialog(null, "Alumnos pendientes:\n" + lista, "Pendientes", JOptionPane.INFORMATION_MESSAGE);
 
        try {
            String idStr = this.validarString("Ingrese el ID del alumno a activar:");
            int id = Integer.parseInt(idStr);
 
            boolean ok = controllerCoord.activarAlumno(id);
            JOptionPane.showMessageDialog(null,
                ok ? "✅ Alumno activado con exito." : "❌ No se pudo activar. Verifique el ID.");
 
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "❌ El ID debe ser un número.");
        }
    }
 
    private void verAlumnosComision() {
        String comisiones = controllerCoord.listarComisiones();
        JOptionPane.showMessageDialog(null, "Comisiones:\n" + comisiones, "Comisiones", JOptionPane.INFORMATION_MESSAGE);
 
        try {
            String idStr = this.validarString("Ingrese el ID de la comision:");
            int idComision = Integer.parseInt(idStr);
 
            String lista = controllerCoord.listarAlumnosDeComision(idComision);
            JOptionPane.showMessageDialog(null, lista.isEmpty() ? "No hay alumnos en esta comision." : lista,
                "Alumnos de la Comision", JOptionPane.INFORMATION_MESSAGE);
 
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "❌ El ID debe ser un número.");
        }
    }
 
    private void inscribirAlumno() {
        String comisiones = controllerCoord.listarComisiones();
        JOptionPane.showMessageDialog(null, "Comisiones:\n" + comisiones, "Comisiones", JOptionPane.INFORMATION_MESSAGE);
 
        String alumnos = controllerCoord.listarAlumnosActivos();
        JOptionPane.showMessageDialog(null, "Alumnos activos:\n" + alumnos, "Alumnos", JOptionPane.INFORMATION_MESSAGE);
 
        try {
            String idAlumnoStr = this.validarString("Ingrese el ID del alumno:");
            int idAlumno = Integer.parseInt(idAlumnoStr);
 
            String idComisionStr = this.validarString("Ingrese el ID de la comision:");
            int idComision = Integer.parseInt(idComisionStr);
 
            boolean ok = controllerCoord.inscribirAlumno(idAlumno, idComision);
            JOptionPane.showMessageDialog(null,
                ok ? "✅ Alumno inscripto con exito." : "❌ Error: Ya estaba inscripto o ID incorrecto.");
 
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "❌ El ID debe ser un número.");
        }
    }

}
