import java.io.*;
import java.util.Scanner;

public class MisionPosibleMain {
    public static void main(String[] args) {
        Escenario e = new Escenario("Nostromo");

        cargarConfiguracionInicial(e, "src//configuracion.txt");


        System.out.println("Estado inicial del escenario:");
        System.out.println(e);


        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese la posición de la bomba para detonar (formato: fila columna): ");
        int fila = scanner.nextInt();
        int columna = scanner.nextInt();


        Bomba bomba = e.obtenerBombaEnPosicion(new Posicion(fila, columna));
        if (bomba != null) {
            bomba.explotar();
            System.out.println("Bomba detonada.");
        } else {
            System.out.println("No se encontró ninguna bomba en esa posición.");
        }


        System.out.println("Estado del escenario después de la explosión:");
        System.out.println(e);


        guardarConfiguracionActual(e, "src//configuracion.txt");
    }

    public static void cargarConfiguracionInicial(Escenario e, String archivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(" ");
                String tipo = partes[0];
                int fila = Integer.parseInt(partes[1]);
                int columna = Integer.parseInt(partes[2]);
                Posicion posicion = new Posicion(fila, columna);

                switch (tipo) {
                    case "Terricola":
                        String nombre = partes[3];
                        e.agregarElemento(new Terricola(nombre, e, posicion));
                        break;
                    case "Extraterrestre":
                        e.agregarElemento(new Extraterrestre("Alien", e, posicion));
                        break;
                    case "Roca":
                        e.agregarElemento(new Roca(e, posicion));
                        break;
                    case "Bomba":
                        int radio = Integer.parseInt(partes[3]);
                        e.agregarElemento(new Bomba(e, posicion, radio));
                        break;
                    default:
                        System.out.println("Elemento desconocido: " + tipo);
                }
            }
        } catch (IOException ex) {
            System.out.println("Error al leer el archivo de configuración: " + ex.getMessage());
        }
    }

    public static void guardarConfiguracionActual(Escenario e, String archivo) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
            for (Elemento elemento : e.getElementos()) {
                if (elemento instanceof Terricola) {
                    Terricola t = (Terricola) elemento;
                    pw.println("Terricola " + t.getPosicion().getRenglon() + " " + t.getPosicion().getColumna() + " " + t.getNombre());
                } else if (elemento instanceof Extraterrestre) {
                    Extraterrestre et = (Extraterrestre) elemento;
                    pw.println("Extraterrestre " + et.getPosicion().getRenglon() + " " + et.getPosicion().getColumna());
                } else if (elemento instanceof Roca) {
                    Roca r = (Roca) elemento;
                    pw.println("Roca " + r.getPosicion().getRenglon() + " " + r.getPosicion().getColumna());
                } else if (elemento instanceof Bomba) {
                    Bomba b = (Bomba) elemento;
                    pw.println("Bomba " + b.getPosicion().getRenglon() + " " + b.getPosicion().getColumna() + " " + b.getRadio());
                }
            }
        } catch (IOException ex) {
            System.out.println("Error al guardar el archivo de configuración: " + ex.getMessage());
        }
    }
}
