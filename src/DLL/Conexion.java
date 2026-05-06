package DLL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static String URL = "jdbc:mysql://localhost:3306/EduTech";
    private static String USER = "root";
    private static String PASSWORD = ""; // Asegurate que en XAMPP sea vacío

    private static Connection conect;
    private static Conexion instance;

    private Conexion() {
        try {
            // 1. CARGAR EL DRIVER (Esto es lo que te faltaba para que Java encuentre MySQL)
        	Class.forName("com.mysql.jdbc.Driver"); // Sin el ".cj"
            
            // 2. CONECTAR
            conect = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Se conectó correctamente");
            
        } catch (ClassNotFoundException e) {
            System.out.println("❌ Error: No se encontró el conector .JAR");
        } catch (SQLException e) {
            System.out.println("❌ Error de SQL: " + e.getMessage());
        }
    }

    public static Conexion getInstance() {
        if (instance == null) {
            instance = new Conexion();
        }
        return instance;
    }

    public Connection getConnection() {
        return conect;
    }
}
	

