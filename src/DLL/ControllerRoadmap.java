package DLL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

import BLL.Roadmap;
import BLL.Tema;
import repository.RoadmapRepository;

public class ControllerRoadmap implements RoadmapRepository {

    private Connection con = Conexion.getInstance().getConnection();

    @Override
    public void agregarRoadmap(Roadmap roadmap) {
        String sql = "INSERT INTO roadmaps (titulo, descripcion, id_contenidista) VALUES (?, ?, ?)";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, roadmap.getTitulo());
            ps.setString(2, roadmap.getDescripcion());
            ps.setInt(3, roadmap.getIdContenidista());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Roadmap guardado exitosamente.");
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar Roadmap: " + e.getMessage());
        }
    }

    @Override
    public void agregarTema(Tema tema) {
        String sql = "INSERT INTO temas (nombre, descripcion, id_tema_padre, id_roadmap) VALUES (?, ?, ?, ?)";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, tema.getNombre());
            ps.setString(2, tema.getDescripcion());
            
            // Verificamos si es un tema raíz (null) o subtema (id_padre)
            if (tema.getIdTemaPadre() == null) {
                ps.setNull(3, java.sql.Types.INTEGER);
            } else {
                ps.setInt(3, tema.getIdTemaPadre());
            }
            
            ps.setInt(4, tema.getIdRoadmap());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Tema/Hito agregado con éxito.");
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al agregar tema: " + e.getMessage());
        }
    }

    @Override
    public List<Roadmap> mostrarRoadmaps() {
        List<Roadmap> lista = new ArrayList<>();
        String sql = "SELECT * FROM roadmaps";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                Roadmap r = new Roadmap(
                    rs.getInt("id_roadmap"),
                    rs.getString("titulo"),
                    rs.getString("descripcion"),
                    rs.getInt("id_contenidista")
                );
                lista.add(r);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public List<Tema> mostrarTemasPorRoadmap(int idRoadmap) {
        List<Tema> lista = new ArrayList<>();
        String sql = "SELECT * FROM temas WHERE id_roadmap = ?";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idRoadmap);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                Tema t = new Tema(
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    (Integer) rs.getObject("id_tema_padre"), // getObject permite null
                    rs.getInt("id_roadmap")
                );
                t.setId(rs.getInt("id_tema"));
                lista.add(t);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar temas: " + e.getMessage());
        }
        return lista;
    }
}