package BLL;

import javax.swing.JOptionPane;

import repository.Validaciones;

public class Profesor extends Usuario implements Validaciones{

    

	public Profesor(int id, String nombre, String email, String tipo, String password) {
		super(id, nombre, email, tipo, password);
	}

	@Override
	public String toString() {
		return "Profesor [toString()=" + super.toString() + "]";
	}

	@Override
	public void menu() {
		String[] opciones = {"Agregar alumno","Ver alumnos","Eliminar alumno", "editar alumno","salir"};
		int opcion;
		
		do {
			
			opcion = JOptionPane.showOptionDialog(null, "Elija opcion", "", 0, 0, null, opciones, opciones);
			switch (opcion) {
			case 0:
				this.AgregarAlumno();
				break;
			case 1:
			this.MostrarAlumno();
				
				break;
			case 2: 
                this.EliminarAlumno();
                break;
			case 3: 
                this.EditarAlumno();
                break;
				
			default:
				break;
			}
		} while (opcion!=4);
		
	}
	public void AgregarAlumno() {
		
		Alumno nuevo = new Alumno(
				this.validarString("Ingrse nombre"),
				
				this.validarString("Ingrse email"),
				
				"Alumno", 
				this.validarString("Ingrse passwoard"));
		
		this.getController().agregarUsuario(nuevo);
	}

	
	public void EliminarAlumno() {
	    try {
	        // Pedimos el ID al profesor
	        String input = JOptionPane.showInputDialog("Ingrese el ID del alumno que desea eliminar:");
	        
	        if (input != null && !input.isEmpty()) {
	            int idParaEliminar = Integer.parseInt(input);
	            
	            // Creamos un alumno vacío solo con el ID para que el Controller sepa a quién borrar
	            // Nota: Alumno(id, nombre, email, tipo, password)
	            Alumno alumnoBorrar = new Alumno(idParaEliminar, "", "", "Alumno", "");
	            
	            // Llamamos al método del controlador que creamos antes
	            this.getController().eliminar(alumnoBorrar);
	            
	            JOptionPane.showMessageDialog(null, "Proceso de eliminación finalizado.");
	        }
	    } catch (NumberFormatException e) {
	        JOptionPane.showMessageDialog(null, "Error: Debe ingresar un número de ID válido.");
	    }
	}

	public void EditarAlumno() {
	    try {
	        // 1. Pedimos el ID para saber A QUIÉN editar
	        String input = JOptionPane.showInputDialog("Ingrese el ID del alumno que desea EDITAR:");
	        
	        if (input != null && !input.isEmpty()) {
	            int idParaEditar = Integer.parseInt(input);
	            
	            // 2. Pedimos los NUEVOS datos (usando tus métodos de validación)
	            String nuevoNombre = this.validarString("Ingrese el NUEVO nombre:");
	            String nuevoEmail = this.validarString("Ingrese el NUEVO email:");
	            String nuevoPass = this.validarString("Ingrese la NUEVA contraseña:");
	            
	            // 3. Creamos el objeto con el ID original y los datos actualizados
	            // Nota: El tipo sigue siendo "Alumno"
	            Alumno alumnoEditado = new Alumno(idParaEditar, nuevoNombre, nuevoEmail, "Alumno", nuevoPass);
	            
	            // 4. Se lo mandamos al controlador
	            this.getController().editar(alumnoEditado);
	            
	            JOptionPane.showMessageDialog(null, "¡Alumno actualizado con éxito!");
	        }
	    } catch (NumberFormatException e) {
	        JOptionPane.showMessageDialog(null, "Error: El ID debe ser un número válido.");
	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(null, "Ocurrió un error al intentar editar.");
	        e.printStackTrace();
	    }}
	    public void MostrarAlumno() {
		    try {
		        // 1. Pedimos el ID para saber A QUIÉN editar
		        String input = JOptionPane.showInputDialog("Ingrese el ID del alumno que desea Mostrar:");
		        
		        if (input != null && !input.isEmpty()) {
		            int idParaEditar = Integer.parseInt(input);
		            
		            // 2. Pedimos los NUEVOS datos (usando tus métodos de validación)
		            String nuevoNombre = this.validarString("Ingrese el NUEVO nombre:");
		            String nuevoEmail = this.validarString("Ingrese el NUEVO email:");
		            String nuevoPass = this.validarString("Ingrese la NUEVA contraseña:");
		            
		            // 3. Creamos el objeto con el ID original y los datos actualizados
		            // Nota: El tipo sigue siendo "Alumno"
		            Alumno alumnoEditado = new Alumno(idParaEditar, nuevoNombre, nuevoEmail, "Alumno", nuevoPass);
		            
		            // 4. Se lo mandamos al controlador
		            this.getController().editar(alumnoEditado);
		            
		            JOptionPane.showMessageDialog(null, "¡Alumno actualizado con éxito!");
		        }
		    } catch (NumberFormatException e) {
		        JOptionPane.showMessageDialog(null, "Error: El ID debe ser un número válido.");
		    } catch (Exception e) {
		        JOptionPane.showMessageDialog(null, "Ocurrió un error al intentar editar.");
		        e.printStackTrace();
		    }
	  
}}
    

 

