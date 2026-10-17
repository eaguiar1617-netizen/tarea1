
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class Main {

    static Scanner teclado = new Scanner(System.in);

    // Patron Singleton
    static GestionCasos gestor = GestionCasos.getInstancia();

    // Patron Observer
    static NotificadorEstado notificador = new NotificadorEstado();

    public static void main(String[] args) {

        notificador.agregarObservador(new ObservadorConsola());

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
                    gestor.guardarCasos();
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
                gestor.nuevoCodigo(), nombre, especie, descripcion);

        gestor.getCasos().add(caso);
        gestor.guardarCasos();

        System.out.println("Caso registrado correctamente.");
        System.out.println("Codigo asignado: " + caso.codigo);
    }

    static void mostrarCasos() {
        System.out.println("\n--- CASOS REGISTRADOS ---");

        if (gestor.getCasos().isEmpty()) {
            System.out.println("No hay casos registrados.");
            return;
        }

        for (CasoRescate caso : gestor.getCasos()) {
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

        if (gestor.getCasos().isEmpty()) {
            System.out.println("No hay casos registrados.");
            return;
        }

        mostrarCasos();

        System.out.print("Codigo del caso: ");
        int codigo = leerEntero();

        CasoRescate caso = gestor.buscarCaso(codigo);

        if (caso == null) {
            System.out.println("No existe ese caso.");
            return;
        }

        System.out.print("Nombre del responsable: ");
        String nombre = teclado.nextLine().trim();

        if (nombre.isEmpty()) {
            System.out.println("El nombre no puede estar vacio.");
            return;
        }

        System.out.println("1. Voluntario");
        System.out.println("2. Veterinario");
        System.out.print("Tipo de responsable: ");

        int tipo = leerEntero();
        ResponsableFactory fabrica;

        if (tipo == 1) {
            fabrica = new FabricaVoluntario();
        } else if (tipo == 2) {
            fabrica = new FabricaVeterinario();
        } else {
            System.out.println("Tipo no valido.");
            return;
        }

        Responsable responsable = fabrica.crearResponsable(nombre);

        caso.responsable = responsable.getNombre();
        caso.tipoResponsable = responsable.getTipo();

        gestor.guardarCasos();

        System.out.println("Responsable asignado correctamente.");
        System.out.println("Tipo: " + responsable.getTipo());
    }

    static void cambiarEstado() {
        System.out.println("\n--- CAMBIAR ESTADO ---");

        if (gestor.getCasos().isEmpty()) {
            System.out.println("No hay casos registrados.");
            return;
        }

        mostrarCasos();

        System.out.print("Codigo del caso: ");
        int codigo = leerEntero();

        CasoRescate caso = gestor.buscarCaso(codigo);

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
        String nuevoEstado;

        switch (opcion) {
            case 1:
                nuevoEstado = "Rescatado";
                break;
            case 2:
                nuevoEstado = "En tratamiento";
                break;
            case 3:
                nuevoEstado = "Disponible para adopcion";
                break;
            case 4:
                nuevoEstado = "Adoptado";
                break;
            default:
                System.out.println("Estado no valido.");
                return;
        }

        caso.cambiarEstado(nuevoEstado, notificador);
        gestor.guardarCasos();

        System.out.println("Estado actualizado correctamente.");
    }

    static void registrarAdopcion() {
        System.out.println("\n--- REGISTRAR ADOPCION ---");

        if (gestor.getCasos().isEmpty()) {
            System.out.println("No hay casos registrados.");
            return;
        }

        mostrarCasos();

        System.out.print("Codigo del animal: ");
        int codigo = leerEntero();

        CasoRescate caso = gestor.buscarCaso(codigo);

        if (caso == null) {
            System.out.println("No existe ese caso.");
            return;
        }

        if (caso.estado.equals("Adoptado")) {
            System.out.println("El animal ya esta registrado como adoptado.");
            return;
        }

        if (!caso.estado.equals("Disponible para adopcion")) {
            System.out.println(
                    "Primero cambie el estado a Disponible para adopcion.");
            return;
        }

        System.out.print("Nombre de la persona adoptante: ");
        String adoptante = teclado.nextLine().trim();

        if (adoptante.isEmpty()) {
            System.out.println("El nombre no puede estar vacio.");
            return;
        }

        caso.adoptante = adoptante;
        caso.cambiarEstado("Adoptado", notificador);

        gestor.guardarCasos();

        System.out.println("Adopcion registrada correctamente.");
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

// ==========================================
// PATRON SINGLETON
// Una sola instancia administra los casos.
// ==========================================

class GestionCasos {

    private static final GestionCasos instancia = new GestionCasos();

    private final ArrayList<CasoRescate> casos =
            new ArrayList<CasoRescate>();

    private int siguienteCodigo = 1;
    private final String archivo = "casos.txt";

    private GestionCasos() {
        cargarCasos();
    }

    public static GestionCasos getInstancia() {
        return instancia;
    }

    public ArrayList<CasoRescate> getCasos() {
        return casos;
    }

    public int nuevoCodigo() {
        return siguienteCodigo++;
    }

    public CasoRescate buscarCaso(int codigo) {
        for (CasoRescate caso : casos) {
            if (caso.codigo == codigo) {
                return caso;
            }
        }
        return null;
    }

    public void guardarCasos() {
        try (PrintWriter salida = new PrintWriter(
                new OutputStreamWriter(
                        new FileOutputStream(archivo),
                        StandardCharsets.UTF_8))) {

            for (CasoRescate caso : casos) {
                salida.println(
                        caso.codigo + "|" +
                        limpiar(caso.nombre) + "|" +
                        limpiar(caso.especie) + "|" +
                        limpiar(caso.descripcion) + "|" +
                        limpiar(caso.estado) + "|" +
                        limpiar(caso.responsable) + "|" +
                        limpiar(caso.tipoResponsable) + "|" +
                        limpiar(caso.adoptante));
            }

        } catch (IOException e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }
    }

    private void cargarCasos() {
        File fichero = new File(archivo);

        if (!fichero.exists()) {
            return;
        }

        try (BufferedReader lector = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(fichero),
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

            if (!casos.isEmpty()) {
                System.out.println("Se cargaron " + casos.size()
                        + " caso(s) guardado(s).");
            }

        } catch (IOException e) {
            System.out.println("Error al cargar: " + e.getMessage());
        }
    }

    private String limpiar(String texto) {
        return texto.replace("|", "/")
                    .replace("\n", " ")
                    .replace("\r", " ");
    }
}

// ==========================================
// PATRON OBSERVER
// Notifica cuando cambia el estado de un caso.
// ==========================================

interface ObservadorCaso {
    void actualizar(CasoRescate caso, String estadoAnterior);
}

class NotificadorEstado {

    private final ArrayList<ObservadorCaso> observadores =
            new ArrayList<ObservadorCaso>();

    public void agregarObservador(ObservadorCaso observador) {
        observadores.add(observador);
    }

    public void notificar(CasoRescate caso, String estadoAnterior) {
        for (ObservadorCaso observador : observadores) {
            observador.actualizar(caso, estadoAnterior);
        }
    }
}

class ObservadorConsola implements ObservadorCaso {

    @Override
    public void actualizar(CasoRescate caso, String estadoAnterior) {
        System.out.println("\n[NOTIFICACION]");
        System.out.println("Caso: " + caso.codigo
                + " - " + caso.nombre);
        System.out.println("Estado anterior: " + estadoAnterior);
        System.out.println("Estado nuevo: " + caso.estado);
    }
}

// ==========================================
// PATRON FACTORY METHOD
// Crea responsables segun su tipo.
// ==========================================

class Responsable {

    private final String nombre;
    private final String tipo;

    public Responsable(String nombre, String tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }
}

abstract class ResponsableFactory {

    public abstract Responsable crearResponsable(String nombre);
}

class FabricaVoluntario extends ResponsableFactory {

    @Override
    public Responsable crearResponsable(String nombre) {
        return new Responsable(nombre, "Voluntario");
    }
}

class FabricaVeterinario extends ResponsableFactory {

    @Override
    public Responsable crearResponsable(String nombre) {
        return new Responsable(nombre, "Veterinario");
    }
}

// ==========================================
// CLASE DEL CASO DE RESCATE
// ==========================================

class CasoRescate {

    int codigo;
    String nombre;
    String especie;
    String descripcion;
    String estado;
    String responsable;
    String tipoResponsable;
    String adoptante;

    public CasoRescate(int codigo, String nombre, String especie,
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

    public void cambiarEstado(String nuevoEstado,
                              NotificadorEstado notificador) {
        String estadoAnterior = this.estado;
        this.estado = nuevoEstado;

        if (!estadoAnterior.equals(nuevoEstado)) {
            notificador.notificar(this, estadoAnterior);
        }
    }
}