import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class Main {

    static Scanner teclado = new Scanner(System.in);
    static ArrayList<CasoRescate> casos =
            new ArrayList<CasoRescate>();
    static int siguienteCodigo = 1;
    static final String ARCHIVO = "casos.txt";

    public static void main(String[] args) {
        cargarCasos();

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
                    guardarCasos();
                    System.out.println("Programa finalizado.");
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }

        } while (opcion != 0);
    }

    static void registrarCaso() {
        System.out.println("\n--- REGISTRO DE RESCATE ---");

        System.out.print("Nombre del animal: ");
        String nombre = teclado.nextLine().trim();

        if (nombre.isEmpty()) {
            System.out.println("El nombre no puede estar vacio.");
            return;
        }

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
                siguienteCodigo, nombre, especie, descripcion);

        casos.add(caso);
        siguienteCodigo++;

        guardarCasos();

        System.out.println("Caso registrado correctamente.");
        System.out.println("Codigo asignado: " + caso.codigo);
    }

    static void mostrarCasos() {
        System.out.println("\n--- CASOS REGISTRADOS ---");

        if (casos.isEmpty()) {
            System.out.println("No hay casos registrados.");
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
            System.out.println("Adoptante: " + caso.adoptante);
        }
    }

    static void asignarResponsable() {
        System.out.println("\n--- ASIGNAR RESPONSABLE ---");

        if (casos.isEmpty()) {
            System.out.println("No hay casos registrados.");
            return;
        }

        mostrarCasos();

        System.out.print("Codigo del caso: ");
        int codigo = leerEntero();

        CasoRescate caso = buscarCaso(codigo);

        if (caso == null) {
            System.out.println("No existe ese caso.");
            return;
        }

        System.out.print("Nombre del responsable: ");
        String responsable = teclado.nextLine().trim();

        if (responsable.isEmpty()) {
            System.out.println("El nombre no puede estar vacio.");
            return;
        }

        System.out.println("1. Voluntario");
        System.out.println("2. Veterinario");
        System.out.print("Tipo de responsable: ");

        int tipo = leerEntero();

        if (tipo == 1) {
            caso.tipoResponsable = "Voluntario";
        } else if (tipo == 2) {
            caso.tipoResponsable = "Veterinario";
        } else {
            System.out.println("Tipo no valido.");
            return;
        }

        caso.responsable = responsable;
        guardarCasos();

        System.out.println("Responsable asignado correctamente.");
    }

    static void cambiarEstado() {
        System.out.println("\n--- CAMBIAR ESTADO ---");

        if (casos.isEmpty()) {
            System.out.println("No hay casos registrados.");
            return;
        }

        mostrarCasos();

        System.out.print("Codigo del caso: ");
        int codigo = leerEntero();

        CasoRescate caso = buscarCaso(codigo);

        if (caso == null) {
            System.out.println("No existe ese caso.");
            return;
        }

        System.out.println("1. Rescatado");
        System.out.println("2. En tratamiento");
        System.out.println("3. Disponible para adopcion");
        System.out.println("4. Adoptado");
        System.out.print("Nuevo estado: ");

        int opcion = leerEntero();

        switch (opcion) {
            case 1:
                caso.estado = "Rescatado";
                break;
            case 2:
                caso.estado = "En tratamiento";
                break;
            case 3:
                caso.estado = "Disponible para adopcion";
                break;
            case 4:
                caso.estado = "Adoptado";
                break;
            default:
                System.out.println("Estado no valido.");
                return;
        }

        guardarCasos();
        System.out.println("Estado actualizado correctamente.");
    }

    static void registrarAdopcion() {
        System.out.println("\n--- REGISTRAR ADOPCION ---");

        if (casos.isEmpty()) {
            System.out.println("No hay casos registrados.");
            return;
        }

        mostrarCasos();

        System.out.print("Codigo del animal: ");
        int codigo = leerEntero();

        CasoRescate caso = buscarCaso(codigo);

        if (caso == null) {
            System.out.println("No existe ese caso.");
            return;
        }

        if (caso.estado.equals("Adoptado")) {
            System.out.println("El animal ya esta registrado como adoptado.");
            return;
        }

        if (!caso.estado.equals("Disponible para adopcion")) {
            System.out.println("Primero cambie el estado a Disponible para adopcion.");
            return;
        }

        System.out.print("Nombre de la persona adoptante: ");
        String adoptante = teclado.nextLine().trim();

        if (adoptante.isEmpty()) {
            System.out.println("El nombre no puede estar vacio.");
            return;
        }

        caso.adoptante = adoptante;
        caso.estado = "Adoptado";

        guardarCasos();

        System.out.println("Adopcion registrada correctamente.");
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

    static void guardarCasos() {
        try (PrintWriter archivo = new PrintWriter(
                new OutputStreamWriter(
                        new FileOutputStream(ARCHIVO),
                        StandardCharsets.UTF_8))) {

            for (CasoRescate caso : casos) {
                archivo.println(
                        caso.codigo + "|" +
                        limpiar(caso.nombre) + "|" +
                        limpiar(caso.especie) + "|" +
                        limpiar(caso.descripcion) + "|" +
                        limpiar(caso.estado) + "|" +
                        limpiar(caso.responsable) + "|" +
                        limpiar(caso.tipoResponsable) + "|" +
                        limpiar(caso.adoptante)
                );
            }

        } catch (IOException e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }
    }

    static void cargarCasos() {
        File archivo = new File(ARCHIVO);

        if (!archivo.exists()) {
            return;
        }

        try (BufferedReader lector = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(archivo),
                        StandardCharsets.UTF_8))) {

            String linea;

            while ((linea = lector.readLine()) != null) {
                String[] datos = linea.split("\\|", -1);

                if (datos.length != 8) {
                    System.out.println("Se omitio un registro incompleto.");
                    continue;
                }

                try {
                    int codigo = Integer.parseInt(datos[0]);

                    CasoRescate caso = new CasoRescate(
                            codigo, datos[1], datos[2], datos[3]);

                    caso.estado = datos[4];
                    caso.responsable = datos[5];
                    caso.tipoResponsable = datos[6];
                    caso.adoptante = datos[7];

                    casos.add(caso);

                    if (codigo >= siguienteCodigo) {
                        siguienteCodigo = codigo + 1;
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Se omitio un codigo invalido.");
                }
            }

        } catch (IOException e) {
            System.out.println("Error al cargar: " + e.getMessage());
        }

        if (!casos.isEmpty()) {
            System.out.println("Se cargaron " + casos.size()
                    + " caso(s) guardado(s).");
        }
    }

    static String limpiar(String texto) {
        return texto.replace("|", "/")
                    .replace("\n", " ")
                    .replace("\r", " ");
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