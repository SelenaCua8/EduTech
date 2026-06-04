import java.util.Scanner;

public class MenuGuionista {
    public static void mostrarMenu(int idLogueado) {
        Scanner sc = new Scanner(System.in);
        ControllerGuionista controller = new ControllerGuionista();
        int opcion = 0;

        do {
            System.out.println("\n--- PANEL DE GUIONISTA ---");
            System.out.println("1. Crear nuevo Roadmap");
            System.out.println("2. Agregar Tema/Módulo a un Roadmap");
            System.out.println("3. Ver mis Roadmaps");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();
            sc.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    System.out.print("Nombre de la nueva ruta de aprendizaje: ");
                    String nombreR = sc.nextLine();
                    if(controller.crearRoadmap(nombreR, idLogueado)) {
                        System.out.println("¡Roadmap creado exitosamente!");
                    }
                    break;
                case 2:
                    System.out.print("ID del Roadmap al que pertenece: ");
                    int idR = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Nombre del Tema/Módulo: ");
                    String nombreT = sc.nextLine();
                    // Enviamos null al padre porque es un tema principal
                    if(controller.crearTema(nombreT, null, idR)) {
                        System.out.println("¡Tema vinculado correctamente!");
                    }
                    break;
                case 3:
                    controller.listarRoadmaps(idLogueado);
                    break;
                case 4:
                    System.out.println("Cerrando sesión de Guionista...");
                    break;
                default:
                    System.out.println("Opción incorrecta.");
            }
        } while (opcion != 4);
    }
}