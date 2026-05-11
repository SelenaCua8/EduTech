package DLL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.PreparedStatement;

public class ControllerCoordinador {
	
	private Connection con = Conexion.getInstance().getConnection();
	 
    // ─────────────────────────────────────────────
    // CURSOS
    // ─────────────────────────────────────────────
 
    public String listarCursos() {
        StringBuilder sb = new StringBuilder();
        String sql = "SELECT id_curso, titulo, nivel FROM cursos";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sb.append("ID: ").append(rs.getInt("id_curso"))
                  .append(" | ").append(rs.getString("titulo"))
                  .append(" | Nivel: ").append(rs.getString("nivel"))
                  .append("\n");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al listar cursos: " + e.getMessage());
        }
        return sb.toString();
    }
 
    public boolean insertarCurso(String titulo, String descripcion, String nivel) {
        String sql = "INSERT INTO cursos (titulo, descripcion, nivel) VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, titulo);
            ps.setString(2, descripcion);
            ps.setString(3, nivel);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Error al insertar curso: " + e.getMessage());
        }
        return false;
    }
 
    // ─────────────────────────────────────────────
    // COMISIONES
    // ─────────────────────────────────────────────
 
    public String listarComisiones() {
        StringBuilder sb = new StringBuilder();
        String sql = "SELECT c.id_comision, cu.titulo, c.fecha_inicio, c.fecha_fin, " +
                     "CONCAT(u.nombre, ' ', u.apellido) AS docente " +
                     "FROM comisiones c " +
                     "JOIN cursos cu ON c.id_curso = cu.id_curso " +
                     "LEFT JOIN usuarios u ON c.id_docente = u.id_usuario";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sb.append("ID: ").append(rs.getInt("id_comision"))
                  .append(" | Curso: ").append(rs.getString("titulo"))
                  .append(" | ").append(rs.getString("fecha_inicio"))
                  .append(" → ").append(rs.getString("fecha_fin"))
                  .append(" | Docente: ").append(rs.getString("docente") != null ? rs.getString("docente") : "Sin asignar")
                  .append("\n");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al listar comisiones: " + e.getMessage());
        }
        return sb.toString();
    }
 
    public boolean insertarComision(int idCurso, String fechaInicio, String fechaFin, String fechaSoporte) {
        String sql = "INSERT INTO comisiones (id_curso, fecha_inicio, fecha_fin, fecha_limite_soporte) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idCurso);
            ps.setString(2, fechaInicio);
            ps.setString(3, fechaFin);
            ps.setString(4, fechaSoporte);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Error al insertar comision: " + e.getMessage());
        }
        return false;
    }
 
    // ─────────────────────────────────────────────
    // ASIGNACIÓN DOCENTE
    // ─────────────────────────────────────────────
 
    public String listarProfesores() {
        StringBuilder sb = new StringBuilder();
        String sql = "SELECT id_usuario, nombre, apellido, especialidad FROM usuarios WHERE rol = 'PROFESOR' AND estado = 'ACTIVO'";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sb.append("ID: ").append(rs.getInt("id_usuario"))
                  .append(" | ").append(rs.getString("nombre"))
                  .append(" ").append(rs.getString("apellido"))
                  .append(" | Especialidad: ").append(rs.getString("especialidad") != null ? rs.getString("especialidad") : "-")
                  .append("\n");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al listar profesores: " + e.getMessage());
        }
        return sb.toString();
    }
 
    public boolean asignarDocente(int idComision, int idDocente) {
        String sql = "UPDATE comisiones SET id_docente = ? WHERE id_comision = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idDocente);
            ps.setInt(2, idComision);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Error al asignar docente: " + e.getMessage());
        }
        return false;
    }
 
    // ─────────────────────────────────────────────
    // CONTROL DE ALUMNOS
    // ─────────────────────────────────────────────
 
    public String listarAlumnosPendientes() {
        StringBuilder sb = new StringBuilder();
        String sql = "SELECT id_usuario, nombre, apellido, email FROM usuarios WHERE rol = 'ALUMNO' AND estado = 'PENDIENTE'";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sb.append("ID: ").append(rs.getInt("id_usuario"))
                  .append(" | ").append(rs.getString("nombre"))
                  .append(" ").append(rs.getString("apellido"))
                  .append(" | ").append(rs.getString("email"))
                  .append("\n");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al listar alumnos pendientes: " + e.getMessage());
        }
        return sb.toString();
    }
 
    public String listarAlumnosActivos() {
        StringBuilder sb = new StringBuilder();
        String sql = "SELECT id_usuario, nombre, apellido FROM usuarios WHERE rol = 'ALUMNO' AND estado = 'ACTIVO'";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sb.append("ID: ").append(rs.getInt("id_usuario"))
                  .append(" | ").append(rs.getString("nombre"))
                  .append(" ").append(rs.getString("apellido"))
                  .append("\n");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al listar alumnos activos: " + e.getMessage());
        }
        return sb.toString();
    }
 
    public boolean activarAlumno(int idAlumno) {
        String sql = "UPDATE usuarios SET estado = 'ACTIVO' WHERE id_usuario = ? AND rol = 'ALUMNO'";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idAlumno);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Error al activar alumno: " + e.getMessage());
        }
        return false;
    }
 
    public String listarAlumnosDeComision(int idComision) {
        StringBuilder sb = new StringBuilder();
        String sql = "SELECT u.id_usuario, u.nombre, u.apellido, u.email " +
                     "FROM inscripciones i " +
                     "JOIN usuarios u ON i.id_alumno = u.id_usuario " +
                     "WHERE i.id_comision = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idComision);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sb.append("ID: ").append(rs.getInt("id_usuario"))
                  .append(" | ").append(rs.getString("nombre"))
                  .append(" ").append(rs.getString("apellido"))
                  .append(" | ").append(rs.getString("email"))
                  .append("\n");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al listar alumnos de comision: " + e.getMessage());
        }
        return sb.toString();
    }
 
    public boolean inscribirAlumno(int idAlumno, int idComision) {
        String sql = "INSERT INTO inscripciones (id_alumno, id_comision) VALUES (?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idAlumno);
            ps.setInt(2, idComision);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            // Clave primaria duplicada = ya estaba inscripto
            System.out.println("❌ Error al inscribir alumno: " + e.getMessage());
        }
        return false;
    }

}
