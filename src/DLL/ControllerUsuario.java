package DLL;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import javax.swing.JOptionPane;

import BLL.Administrador;
import BLL.Alumno;
import BLL.Contenidista;
import BLL.Profesor;
import BLL.Usuario;
import repository.UsuarioRepository; // Asegurate de tener esta interfaz

public class ControllerUsuario implements UsuarioRepository {
    
 
    private Connection con = Conexion.getInstance().getConnection();

    // No me acuerdo para que estaba hecho este, lo dejo por las dudas vemos mas adelante, creo que era para que el admin agregue usuarios 
    /*public void registrarUsuario(String nombre, String apellido, String password, String email) {
        String sql = "INSERT INTO usuarios (nombre, apellido, contrasenia, email) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ps.setString(3, password);
            ps.setString(4, email);
            
            ps.executeUpdate();
            System.out.println("Usuario guardado con éxito");
        } catch (SQLException e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }
    }
 */
    public boolean insertarUsuarioNuevo(String nombre, String apellido, String password, String email, String rol) {
        // IMPORTANTE: Ponemos 'PENDIENTE' directamente en el SQL
        String sql = "INSERT INTO usuarios (nombre, apellido, password, email, rol, estado) VALUES (?, ?, ?, ?, ?, 'PENDIENTE')";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ps.setString(3, password); 
            ps.setString(4, email);
            ps.setString(5, rol); // El rol que eligió en el menú (ALUMNO, PROFESOR, etc.)
            
            int resultado = ps.executeUpdate();
            if (resultado > 0) {
                System.out.println("✅ Solicitud de registro guardada. Estado: PENDIENTE.");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al registrar en la DLL: " + e.getMessage());
            // Si el email ya existe, saltará por aquí por el campo UNIQUE de la DB
        }
        return false;
    }
    
    //controlar que no exista el usuario a traves del mail
    public boolean existeEmail(String email) {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE email = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Si el conteo es mayor a 0, ya existe
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    //lo mismo
    @Override
    public void agregarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nombre, apellido, password, email) VALUES (?, ?, ?, ?)";
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
                
                lista.add(new Alumno(
                    rs.getInt("id_usuario"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("email"), 
                    rs.getString("password")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public void editar(Usuario usuario) {
        String sql = "UPDATE usuarios SET nombre = ?, apellido = ?, password = ?, email = ? WHERE id_usuario = ?";
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
    public Usuario login(String email, String password) {
        Usuario user = null;
        String sql = "SELECT * FROM usuarios WHERE email = ? AND password = ? AND estado = 'ACTIVO'";
        
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password);
            
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // 1. Leemos el ROL
                String rol = rs.getString("rol"); 
                
                // 2. Extraemos datos
                int id = rs.getInt("id_usuario");
                String nom = rs.getString("nombre");
                String ape = rs.getString("apellido");
                String mail = rs.getString("email");
                String pass = rs.getString("password");

                // 3. Creamos el objeto según Rol
                if (rol.equalsIgnoreCase("ADMIN")) {
                    user = new Administrador(id, nom, ape, mail, pass);
                } else if (rol.equalsIgnoreCase("PROFESOR")) {
                    user = new Profesor(id, nom, ape, mail, pass);
                } else if (rol.equalsIgnoreCase("GUIONISTA") || rol.equalsIgnoreCase("CONTENIDISTA")) {
                    user = new Contenidista(id, nom, ape, mail, pass);
                } else {
                    user = new Alumno(id, nom, ape, mail, pass);
                }
                
                // El print debe ir ADENTRO del if para poder usar la variable 'rol'
                System.out.println("✅ " + rol + " detectado. Entrando al sistema...");
            } else {
                System.out.println("❌ No se encontró el usuario o no está ACTIVO.");
            }
            
        } catch (SQLException e) {
            System.out.println("Error en el login: " + e.getMessage());
            e.printStackTrace();
        }
        
        return user;
    } 
} 