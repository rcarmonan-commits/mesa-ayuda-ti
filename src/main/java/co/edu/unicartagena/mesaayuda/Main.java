package co.edu.unicartagena.mesaayuda;

import java.util.Scanner;

/** Punto de entrada y presentación interactiva del sistema de tickets por consola. */
public class Main {
    private final Scanner entrada = new Scanner(System.in);
    private final SistemaDelCaso sistema = new SistemaDelCaso();

    /**
     * Inicia la aplicación de consola.
     *
     * @param args argumentos de línea de comandos; no se utilizan
     */
    public static void main(String[] args) {
        new Main().ejecutar();
    }

    private void ejecutar() {
        System.out.println("=== Mesa de ayuda TI: sistema de tickets ===");
        boolean continuar = true;
        while (continuar) {
            if (!entrada.hasNextLine()) {
                break;
            }
            mostrarMenu();
            int opcion = leerEntero("Seleccione una opcion: ");
            try {
                continuar = ejecutarOpcion(opcion);
            } catch (IllegalArgumentException | java.util.NoSuchElementException excepcion) {
                System.out.println("Operacion no realizada: " + excepcion.getMessage());
            }
            System.out.println();
        }
        System.out.println("Hasta luego.");
    }

    private void mostrarMenu() {
        System.out.println("""
                1. Registrar elemento
                2. Ver todos los elementos registrados
                3. Ver elementos pendientes
                4. Procesar siguiente elemento
                5. Ver historial de elementos procesados
                6. Buscar elemento por codigo
                7. Cancelar elemento pendiente
                8. Deshacer ultimo procesamiento
                9. Ver cantidad de elementos
                10. Salir""");
    }

    private boolean ejecutarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> registrar();
            case 2 -> mostrar("Elementos registrados", sistema.mostrarTodos());
            case 3 -> mostrar("Tickets pendientes (primero en llegar, primero en atender)", sistema.mostrarPendientes());
            case 4 -> System.out.println("Ticket procesado: " + sistema.procesarSiguiente());
            case 5 -> mostrar("Historial de procesados (ultimo procesado primero)", sistema.mostrarHistorial());
            case 6 -> buscar();
            case 7 -> cancelar();
            case 8 -> System.out.println("Procesamiento deshecho; ticket devuelto al final de la cola: "
                    + sistema.deshacerUltimoProcesamiento());
            case 9 -> System.out.printf("Registrados: %d | Pendientes: %d | Procesados: %d%n",
                    sistema.cantidadRegistrados(), sistema.cantidadPendientes(), sistema.cantidadProcesados());
            case 10 -> {
                return false;
            }
            default -> System.out.println("Opcion invalida. Seleccione un numero del 1 al 10.");
        }
        return true;
    }

    private void registrar() {
        String codigo = leerTexto("Codigo del ticket: ");
        String solicitante = leerTexto("Nombre del solicitante: ");
        String categoria = leerTexto("Categoria (hardware, software, red, etc.): ");
        String descripcion = leerTexto("Descripcion del problema: ");
        TicketSoporte ticket = new TicketSoporte(codigo, solicitante, categoria, descripcion);
        sistema.registrar(ticket);
        System.out.println("Ticket registrado y agregado al final de la cola: " + ticket);
    }

    private void buscar() {
        String codigo = leerTexto("Codigo que desea buscar: ");
        TicketSoporte ticket = sistema.buscar(codigo);
        if (ticket == null) {
            System.out.println("No se encontro un ticket con ese codigo.");
        } else {
            System.out.println(ticket);
        }
    }

    private void cancelar() {
        String codigo = leerTexto("Codigo del ticket pendiente que desea cancelar: ");
        System.out.println("Ticket cancelado: " + sistema.cancelarPendiente(codigo));
    }

    private void mostrar(String titulo, String contenido) {
        System.out.println("=== " + titulo + " ===");
        System.out.println(contenido.isBlank() ? "No hay elementos para mostrar." : contenido);
    }

    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        String valor = entrada.nextLine().trim();
        if (valor.isEmpty()) {
            throw new IllegalArgumentException("El campo no puede estar vacio.");
        }
        return valor;
    }

    private int leerEntero(String mensaje) {
        System.out.print(mensaje);
        String valor = entrada.nextLine().trim();
        try {
            return Integer.parseInt(valor);
        } catch (NumberFormatException excepcion) {
            throw new IllegalArgumentException("Debe ingresar un numero entero.");
        }
    }
}
