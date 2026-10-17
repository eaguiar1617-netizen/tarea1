import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static Scanner teclado = new Scanner(System.in);
    static ArrayList<CasoRescate> casos = new ArrayList<CasoRescate>();
    static int siguienteCodigo = 1;

    public static void main(String[] args) {
        int opcion;

        do {
            System.out.println("\n======================================");
            System.out.println(" SISTEMA DE RESCATE Y ADOPCION ANIMAL");
            System.out.println("======================================");
            System.out.println("1. Registrar caso de rescate");
            System.out.println("2. Mostrar casos registrados");
            System.out.println("3. Asignar responsable");
            System.out.println("4. Cambiar estado del caso");
            System.out.println("5. Registrar adopcion");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opcion: ");

            opcion = leerEntero();

            switch (opcion) {
                case 1:
                    registrarCaso();
                    break;

                case 2:
                    mostrarCasos();
                    break;

                case 3:
                    asignarResponsable();
                    break;

                case 4:
                    cambiarEstado();
                    break;

                case 5:
                    registrarAdopcion();
                    break;

                case 0:
                    System.out.println("Gracias por utilizar el sistema.");
                    break;

                default:
                    System.out.println("Opcion no valida.");
            }

        } while (opcion != 0);
    }

    static void registrarCaso() {
        System.out.println("\n--- REGISTRO DE CASO DE RESCATE ---");

        System.out.print("Nombre del animal: ");
        String nombre = teclado.nextLine().trim();

        if (nombre.isEmpty()) {
            System.out.println("El nombre no puede estar vacio.");
            return;
        }

        System.out.println("Especie:");
        System.out.println("1. Perro");
        System.out.println("2. Gato");
        System.out.println("3. Fauna silvestre");
        System.out.print("Seleccione la especie: ");

        int opcionEspecie = leerEntero();
        String especie;

        switch (opcionEspecie) {
            case 1:
                especie = "Perro";
                break;
            case 2:
                especie = "Gato";
                break;
            case 3:
                especie = "Fauna silvestre";
                break;
            default:
                System.out.println("Especie no valida.");
                return;
        }

        System.out.print("Descripcion del rescate: ");
        String descripcion = teclado.nextLine().trim();

        CasoRescate caso = new CasoRescate(
            siguienteCodigo, nombre, especie, descripcion
        );

        casos.add(caso);
        siguienteCodigo++;

        System.out.println("Caso registrado correctamente.");
        System.out.println("Codigo asignado: " + caso.codigo);
    }

    static void mostrarCasos() {
        System.out.println("\n--- CASOS REGISTRADOS ---");

        if (casos.isEmpty()) {
            System.out.println("Todavia no hay casos registrados.");
            return;
        }

        for (CasoRescate caso : casos) {
            System.out.println("------------------------------");
            System.out.println("Codigo: " + caso.codigo);
            System.out.println("Animal: " + caso.nombre);
            System.out.println("Especie: " + caso.especie);
            System.out.println("Descripcion: " + caso.descripcion);
            System.out.println("Estado: " + caso.estado);
            System.out.println("Responsable: " + caso.responsable);
            System.out.println("Tipo de responsable: "
                    + caso.tipoResponsable);
        }
    }

    static void asignarResponsable() {
        System.out.println("\n--- ASIGNAR RESPONSABLE ---");

        if (casos.isEmpty()) {
            System.out.println("No hay casos registrados.");
            return;
        }

        mostrarCasos();

        System.out.print("Ingrese el codigo del caso: ");
        int codigo = leerEntero();

        CasoRescate seleccionado = buscarCaso(codigo);

        if (seleccionado == null) {
            System.out.println("No existe un caso con ese codigo.");
            return;
        }

        System.out.print("Nombre del responsable: ");
        String responsable = teclado.nextLine().trim();

        if (responsable.isEmpty()) {
            System.out.println("El nombre no puede estar vacio.");
            return;
        }

        System.out.println("Tipo de responsable:");
        System.out.println("1. Voluntario");
        System.out.println("2. Veterinario");
        System.out.print("Seleccione una opcion: ");

        int tipo = leerEntero();

        if (tipo == 1) {
            seleccionado.responsable = responsable;
            seleccionado.tipoResponsable = "Voluntario";
        } else if (tipo == 2) {
            seleccionado.responsable = responsable;
            seleccionado.tipoResponsable = "Veterinario";
        } else {
            System.out.println("Tipo no valido.");
            return;
        }

        System.out.println("Responsable asignado correctamente.");
    }

    static void cambiarEstado() {
        System.out.println("\n--- CAMBIAR ESTADO DEL CASO ---");

        if (casos.isEmpty()) {
            System.out.println("No hay casos registrados.");
            return;
        }

        mostrarCasos();

        System.out.print("Ingrese el codigo del caso: ");
        int codigo = leerEntero();

        CasoRescate seleccionado = buscarCaso(codigo);

        if (seleccionado == null) {
            System.out.println("No existe un caso con ese codigo.");
            return;
        }

        System.out.println("Seleccione el nuevo estado:");
        System.out.println("1. Rescatado");
        System.out.println("2. En tratamiento");
        System.out.println("3. Disponible para adopcion");
        System.out.println("4. Adoptado");
        System.out.print("Opcion: ");

        int opcionEstado = leerEntero();

        switch (opcionEstado) {
            case 1:
                seleccionado.estado = "Rescatado";
                break;
            case 2:
                seleccionado.estado = "En tratamiento";
                break;
            case 3:
                seleccionado.estado = "Disponible para adopcion";
                break;
            case 4:
                seleccionado.estado = "Adoptado";
                break;
            default:
                System.out.println("Estado no valido.");
                return;
        }

        System.out.println("Estado actualizado correctamente.");
    }

    static void registrarAdopcion() {
        System.out.println("\n--- REGISTRAR ADOPCION ---");

        if (casos.isEmpty()) {
            System.out.println("No hay casos registrados.");
            return;
        }

        mostrarCasos();

        System.out.print("Codigo del animal que sera adoptado: ");
        int codigo = leerEntero();

        CasoRescate seleccionado = buscarCaso(codigo);

        if (seleccionado == null) {
            System.out.println("No existe un caso con ese codigo.");
            return;
        }

        if (seleccionado.estado.equals("Adoptado")) {
            System.out.println("Este animal ya esta registrado como adoptado.");
            return;
        }

        System.out.print("Nombre de la persona adoptante: ");
        String adoptante = teclado.nextLine().trim();

        if (adoptante.isEmpty()) {
            System.out.println("El nombre no puede estar vacio.");
            return;
        }

        seleccionado.adoptante = adoptante;
        seleccionado.estado = "Adoptado";

        System.out.println("Adopcion registrada correctamente.");
        System.out.println("Animal: " + seleccionado.nombre);
        System.out.println("Adoptante: " + seleccionado.adoptante);
    }

    static CasoRescate buscarCaso(int codigo) {
        for (CasoRescate caso : casos) {
            if (caso.codigo == codigo) {
                return caso;
            }
        }

        return null;
    }

    static int leerEntero() {
        while (!teclado.hasNextInt()) {
            System.out.print("Ingrese un numero valido: ");
            teclado.nextLine();
        }

        int numero = teclado.nextInt();
        teclado.nextLine();

        return numero;
    }
}

class CasoRescate {

    int codigo;
    String nombre;
    String especie;
    String descripcion;
    String estado;
    String responsable;
    String tipoResponsable;
    String adoptante;

    CasoRescate(int codigo, String nombre, String especie,
                String descripcion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.especie = especie;
        this.descripcion = descripcion;
        this.estado = "Rescatado";
        this.responsable = "Sin asignar";
        this.tipoResponsable = "Sin asignar";
        this.adoptante = "Sin registrar";
    }
}