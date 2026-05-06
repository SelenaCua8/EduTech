package repository;

import javax.swing.JOptionPane;

public interface Validaciones {

	default String validarString(String mensaje) {
		String dato;
		do {
			dato = JOptionPane.showInputDialog(mensaje);
			
			if (dato.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Ingrese nuevamente");
			}
			
		} while (dato.isEmpty());
		
		return dato;
	}
}
