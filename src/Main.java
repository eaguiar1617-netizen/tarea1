import java.util.Scanner;

public class Main {

    static Scanner teclado = new Scanner(System.in);

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

            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    System.out.println("Registro de rescate: modulo pendiente.");
                    break;

                case 2:
                    System.out.println("Listado de casos: modulo pendiente.");
                    break;

                case 3:
                    System.out.println("Asignacion de responsable: modulo pendiente.");
                    break;

                case 4:
                    System.out.println("Cambio de estado: modulo pendiente.");
                    break;

                case 5:
                    System.out.println("Registro de adopcion: modulo pendiente.");
                    break;

                case 0:
                    System.out.println("Gracias por utilizar el sistema.");
                    break;

                default:
                    System.out.println("Opcion no valida.");
            }

        } while (opcion != 0);
    }
}