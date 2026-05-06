package DLL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import javax.swing.JOptionPane;

import BLL.Alumno;
import BLL.Usuario;
import repository.UsuarioRepository; // Asegurate de tener esta interfaz

public class ControllerUsuario implements UsuarioRepository {
    
    // 1. Definimos 'con' a nivel de clase para que TODOS los métodos la vean
    private Connection con = Conexion.getInstance().getConnection();

    // Método simple para pruebas
    public void registrarUsuario(String nombre, String apellido, String contra, String email) {
        String sql = "INSERT INTO usuarios (nombre, apellido, contrasenia, email) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ps.setString(3, contra);
            ps.setString(4, email);
            
            ps.executeUpdate();
            System.out.println("Usuario guardado con éxito");
        } catch (SQLException e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }
    }

    @Override
    public void agregarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nombre, apellido, contrasenia, email) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, usuario.getNombre());
            statement.setString(2, usuario.getApellido());
            statement.setString(3, usuario.getPassword());
            statement.setString(4, usuario.getEmail());

            int filas = statement.executeUpdate();
            if (filas > 0) {
                System.out.println("✅ Usuario guardado en la base de datos.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al insertar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public LinkedList<Usuario> mostrarUsuarios() {
        LinkedList<Usuario> lista = new LinkedList<>();
        String sql = "SELECT * FROM usuarios";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // En lugar de new Usuario, usamos una clase que SI se pueda instanciar
                // Asegúrate de que Alumno tenga este constructor en BLL.Alumno
                lista.add(new Alumno(
                    rs.getInt("id_usuario"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("email"), // Ojo: verifica el orden en tu constructor de Alumno
                    rs.getString("contrasenia")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public void editar(Usuario usuario) {
        String sql = "UPDATE usuarios SET nombre = ?, apellido = ?, contrasenia = ?, email = ? WHERE id_usuario = ?";
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, usuario.getNombre());
            statement.setString(2, usuario.getApellido());
            statement.setString(3, usuario.getPassword());
            statement.setString(4, usuario.getEmail());
            statement.setInt(5, usuario.getId());

            statement.executeUpdate();
            System.out.println("✅ Usuario actualizado.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(Usuario usuario) {
        String sql = "DELETE FROM usuarios WHERE id_usuario = ?";
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setInt(1, usuario.getId());
            statement.executeUpdate();
            System.out.println("✅ Usuario eliminado.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Usuario login(String nombre, String password) {
        Usuario user = null;
        // Consulta usando 'contrasenia' como está en tu foto de la DB
        String sql = "SELECT * FROM usuarios WHERE nombre = ? AND contrasenia = ?";
        
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, nombre);
            stmt.setString(2, password);
            
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Creamos un Alumno (o la clase que elijas) con los datos de la foto
                // El orden debe ser: id, nombre, apellido, email, contrasenia
                user = new Alumno(
                    rs.getInt("id_usuario"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("email"),       // Según tu foto, email es el último campo
                    rs.getString("contrasenia")  // contrasenia es el anteúltimo
                );
                System.out.println("✅ Usuario encontrado: " + rs.getString("nombre"));
            } else {
                System.out.println("❌ No se encontró el usuario en la base de datos.");
            }
            
        } catch (SQLException e) {
            System.out.println("Error en el login: " + e.getMessage());
            e.printStackTrace();
        }
        
        return user;
    }
}