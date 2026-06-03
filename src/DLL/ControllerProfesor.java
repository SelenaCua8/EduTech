package DLL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ControllerProfesor {

    // Usamos el singleton de conexión que ya tienen armado
    private Connection con = Conexion.getInstance().getConnection();

    // =========================================================================
    // 1. OBTENER CURSOS/MATERIAS QUE DICTA EL PROFESOR (A través de comisiones)
    // =========================================================================
    public ArrayList<String> obtenerMateriasPorProfesor(int idProfesor) {
        ArrayList<String> lista = new ArrayList<>();
        
        // Conectamos la tabla comisiones con cursos usando tus nombres reales
        String sql = "SELECT c.titulo FROM comisiones co " +
                     "INNER JOIN cursos c ON co.id_curso = c.id_curso " +
                     "WHERE co.id_docente = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idProfesor);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(rs.getString("titulo"));
            }
        } catch (SQLException e) {
            System.out.println("❌ Error en obtenerMateriasPorProfesor: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }

    // =========================================================================
    // 2. OBTENER ALUMNOS ASIGNADOS A SUS COMISIONES
    // =========================================================================
    public ArrayList<String> obtenerProgresoAlumnos(int idProfesor) {
        ArrayList<String> lista = new ArrayList<>();
        
        // Hacemos el mapeo real relacionando inscripciones, comisiones, cursos y usuarios
        String sql = "SELECT u.nombre, u.apellido, cu.titulo AS nombre_curso, co.id_comision " +
                     "FROM usuarios u " +
                     "INNER JOIN inscripciones i ON u.id_usuario = i.id_alumno " +
                     "INNER JOIN comisiones co ON i.id_comision = co.id_comision " +
                     "INNER JOIN cursos cu ON co.id_curso = cu.id_curso " +
                     "WHERE co.id_docente = ? AND u.rol = 'ALUMNO'";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idProfesor);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String renglon = "Curso: " + rs.getString("nombre_curso") + 
                                 " (Comisión: " + rs.getInt("id_comision") + ")" +
                                 " | Alumno: " + rs.getString("nombre") + " " + rs.getString("apellido") +
                                 " | Estado: REGULAR";
                lista.add(renglon);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error en obtenerProgresoAlumnos: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }

    // =========================================================================
    // 3. OBTENER ENTREGAS PENDIENTES DE NOTA (Filtrado por las comisiones del docente)
    // =========================================================================
    public ArrayList<String> obtenerCorreccionesPendientes(int idProfesor) {
        ArrayList<String> lista = new ArrayList<>();
        
        // Conectamos las entregas con las evaluaciones del curso correspondientes al docente
        String sql = "SELECT en.id_entrega, ev.titulo AS titulo_evaluacion, u.nombre, u.apellido, cu.titulo AS nombre_curso " +
                     "FROM entregas en " +
                     "INNER JOIN usuarios u ON en.id_alumno = u.id_usuario " +
                     "INNER JOIN evaluaciones ev ON en.id_evaluacion = ev.id_evaluacion " +
                     "INNER JOIN cursos cu ON ev.id_curso = cu.id_curso " +
                     "INNER JOIN comisiones co ON cu.id_curso = co.id_curso " +
                     "WHERE co.id_docente = ? AND en.nota IS NULL";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idProfesor);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String entregaInfo = "ID Entrega: " + rs.getInt("id_entrega") + 
                                     " | Evaluación: " + rs.getString("titulo_evaluacion") + 
                                     " | Alumno: " + rs.getString("nombre") + " " + rs.getString("apellido") + 
                                     " [" + rs.getString("nombre_curso") + "]";
                lista.add(entregaInfo);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error en obtenerCorreccionesPendientes: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }
}