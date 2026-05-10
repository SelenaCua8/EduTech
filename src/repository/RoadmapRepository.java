package repository;

import java.util.List;
import BLL.Roadmap;
import BLL.Tema;

public interface RoadmapRepository {

    // Método para insertar un nuevo Roadmap en la base de datos
    void agregarRoadmap(Roadmap roadmap);

    // Método para insertar un tema vinculado a un rodamap
    void agregarTema(Tema tema);

    // Método para listar todos los roadmaps
    List<Roadmap> mostrarRoadmaps();
    
    // Método para listar temas de un roadmap especifico
    List<Tema> mostrarTemasPorRoadmap(int idRoadmap);
}