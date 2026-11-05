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
        sistema.precargarDatosPrueba();
        System.out.println("================================================================================");
        System.out.println("        UNIVERSIDAD DE CARTAGENA - PROGRAMA DE INGENIERIA DE SOFTWARE           ");
        System.out.println("                   SISTEMA DE MESA DE AYUDA TI (CASO #6)                        ");
        System.out.println("                 Estudiante: Rosary Eilyn Carmona Navarro                       ");
        System.out.println("                 Docente Tutor: John Carlos Arrieta Arrieta                     ");
        System.out.println("================================================================================");
        System.out.println("  [Info] Sistema inicializado con 15 tickets de soporte precargados.");

        boolean continuar = true;
        while (continuar) {
            if (!entrada.hasNextLine()) {
                break;
            }
            mostrarMenu();
            int opcion = leerEntero("Seleccione una opcion (1-10): ");
            try {
                continuar = ejecutarOpcion(opcion);
            } catch (IllegalArgumentException | java.util.NoSuchElementException excepcion) {
                System.out.println("  [Operacion no realizada] " + excepcion.getMessage());
            }
            System.out.println();
        }
        System.out.println("================================================================================");
        System.out.println("  Cerrando sesion de Mesa de Ayuda TI. Sistema finalizado con exito.");
        System.out.println("================================================================================");
    }

    private void mostrarMenu() {
        System.out.println();
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("  1. Registrar elemento");
        System.out.println("  2. Ver todos los elementos registrados");
        System.out.println("  3. Ver elementos pendientes");
        System.out.println("  4. Procesar siguiente elemento");
        System.out.println("  5. Ver historial de elementos procesados");
        System.out.println("  6. Buscar elemento por codigo");
        System.out.println("  7. Cancelar elemento pendiente");
        System.out.println("  8. Deshacer ultimo procesamiento");
        System.out.println("  9. Ver cantidad de elementos");
        System.out.println("  10. Salir");
        System.out.println("--------------------------------------------------------------------------------");
    }

    private boolean ejecutarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> registrar();
            case 2 -> {
                mostrar("Elementos registrados (Adelante / Derecha)", sistema.mostrarTodos());
                System.out.print("¿Desea ver el recorrido inverso hacia atras (mostrarAtras)? (s/n): ");
                String r = entrada.nextLine().trim();
                if (r.equalsIgnoreCase("s")) {
                    mostrar("Elementos registrados (Atras / Izquierda)", sistema.mostrarAtras());
                }
            }
            case 3 -> mostrar("Tickets pendientes (primero en llegar, primero en atender)", sistema.mostrarPendientes());
            case 4 -> System.out.println("  [OK] Ticket procesado: " + sistema.procesarSiguiente());
            case 5 -> mostrar("Historial de procesados (ultimo procesado primero)", sistema.mostrarHistorial());
            case 6 -> buscar();
            case 7 -> cancelar();
            case 8 -> System.out.println("  [OK] Procesamiento deshecho; ticket devuelto al final de la cola:\n       "
                    + sistema.deshacerUltimoProcesamiento());
            case 9 -> System.out.printf("  Total Registrados: %d | Pendientes en Cola: %d | Procesados en Pila: %d%n",
                    sistema.cantidadRegistrados(), sistema.cantidadPendientes(), sistema.cantidadProcesados());
            case 10 -> {
                return false;
            }
            default -> System.out.println("  [Error] Opcion invalida. Seleccione un numero del 1 al 10.");
        }
        return true;
    }

    private void registrar() {
        System.out.println("--- 1. REGISTRAR ELEMENTO ---");
        String codigo = leerTexto("Codigo del ticket (ej. TI-116): ");
        String solicitante = leerTexto("Nombre del solicitante y area: ");
        String categoria = leerTexto("Categoria (Red, Base de datos, Software, Hardware, etc.): ");
        String descripcion = leerTexto("Descripcion del problema: ");
        TicketSoporte ticket = new TicketSoporte(codigo, solicitante, categoria, descripcion);
        sistema.registrar(ticket);
        System.out.println("  [OK] Ticket registrado y agregado al final de la cola: " + ticket);
    }

    private void buscar() {
        System.out.println("--- 6. BUSCAR ELEMENTO POR CODIGO ---");
        String codigo = leerTexto("Codigo que desea consultar: ");
        TicketSoporte ticket = sistema.buscar(codigo);
        if (ticket == null) {
            System.out.println("  [Resultado] No se encontro un ticket con ese codigo.");
        } else {
            System.out.println("  [Resultado] Ticket encontrado en registro general:\n       " + ticket);
        }
    }

    private void cancelar() {
        System.out.println("--- 7. CANCELAR ELEMENTO PENDIENTE (COLA AUXILIAR) ---");
        String codigo = leerTexto("Codigo del ticket pendiente que desea cancelar: ");
        System.out.println("  [OK] Ticket cancelado mediante cola auxiliar:\n       " + sistema.cancelarPendiente(codigo));
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
