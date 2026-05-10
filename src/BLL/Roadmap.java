package BLL;

public class Roadmap {
    // Atributos privados (Encapsulamiento)
    private int id;
    private String titulo;
    private String descripcion;
    private int idContenidista;

    // Constructor vacío
    public Roadmap() {}

    // Constructor con parámetros para creación
    public Roadmap(String titulo, String descripcion, int idContenidista) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.idContenidista = idContenidista;
    }

    // Constructor completo para cuando traemos datos de la DB
    public Roadmap(int id, String titulo, String descripcion, int idContenidista) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.idContenidista = idContenidista;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public int getIdContenidista() { return idContenidista; }
    public void setIdContenidista(int idContenidista) { this.idContenidista = idContenidista; }
}