package BLL;

public class Tema {
    private int id;
    private String nombre;
    private String descripcion;
    private Integer idTemaPadre; // Usamos Integer por si es null
    private int idRoadmap;

    public Tema() {}

    public Tema(String nombre, String descripcion, Integer idTemaPadre, int idRoadmap) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.idTemaPadre = idTemaPadre;
        this.idRoadmap = idRoadmap;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Integer getIdTemaPadre() { return idTemaPadre; }
    public void setIdTemaPadre(Integer idTemaPadre) { this.idTemaPadre = idTemaPadre; }

    public int getIdRoadmap() { return idRoadmap; }
    public void setIdRoadmap(int idRoadmap) { this.idRoadmap = idRoadmap; }
}