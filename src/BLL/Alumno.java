package BLL;

public class Alumno extends Usuario {

    
    public Alumno(int id, String nombre, String email, String tipo, String password) {
		super(id, nombre, email, tipo, password);
	}
    public Alumno(String nombre, String email, String tipo, String password) {
		super(0, nombre, email, tipo, password);
	}
    public Alumno() {
        super();
    }
	@Override
	public String toString() {
		return "Alumno [toString()=" + super.toString() + "]";
	}
	@Override
	public void menu() {
		// TODO Auto-generated method stub
		
	}
    
    
   
}
