import java.sql.*;

public class ControllerGuionista {
    
    // listar los roadmaps existentes
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

    // se crea roadmap
    public boolean crearRoadmap(String nombre, int idGuionista) {
        // id_coordinador va NULL por defecto
        String sql = "INSERT INTO roadmaps (nombre, porcentaje_completado, id_contenidista, id_coordinador) VALUES (?, 0, ?, NULL)";
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setInt(2, idGuionista);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error al crear roadmap: " + e.getMessage());
            return false;
        }
    }

    // se crea tema
    public boolean crearTema(String nombre, Integer idTemaPadre, int idRoadmap) {
        String sql = "INSERT INTO temas (nombre, id_tema_padre, id_roadmap) VALUES (?, ?, ?)";
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombre);
            
            // FIX: Manejo correcto de nulos para la base de datos
            if (idTemaPadre == null) {
                ps.setNull(2, Types.INTEGER); 
            } else {
                ps.setInt(2, idTemaPadre);
            }
            
            ps.setInt(3, idRoadmap);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error al crear tema: " + e.getMessage());
            return false;
        }
    }
}