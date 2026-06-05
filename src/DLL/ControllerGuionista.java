import java.sql.*;

public class ControllerGuionista {
    
    // Tarea 1: Listar los roadmaps existentes
    public void listarRoadmaps(int idGuionista) {
        String sql = "SELECT * FROM roadmaps WHERE id_contenidista = ?";
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idGuionista);
            ResultSet rs = ps.executeQuery();
            
            System.out.println("\n--- TUS ROADMAPS ---");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id_roadmap") + " | Nombre: " + rs.getString("nombre"));
            }
        } catch (Exception e) {
            System.out.println("Error al listar roadmaps: " + e.getMessage());
        }
    }
}