import java.io.*;
import java.util.ArrayList;

public class Escenario {
    private String nombre;
    private Elemento[][] campoDeBatalla;
    private ArrayList<Elemento> elementos; // Lista de todos los elementos en el escenario

    public Escenario(String nombre) {
        this.nombre = nombre;
        this.campoDeBatalla = new Elemento[10][10]; // Tamaño fijo de 10x10
        this.elementos = new ArrayList<>();
    }

    public void agregarElemento(Elemento e) {
        Posicion p = e.getPosicion();
        campoDeBatalla[p.getRenglon()][p.getColumna()] = e;
        elementos.add(e); // Agregar a la lista de elementos
    }

    public void destruirElementos(Posicion p, int radio) {
        ArrayList<Elemento> elementosADestruir = new ArrayList<>();

        for (int i = Math.max(0, p.getRenglon() - radio); i <= Math.min(9, p.getRenglon() + radio); i++) {
            for (int j = Math.max(0, p.getColumna() - radio); j <= Math.min(9, p.getColumna() + radio); j++) {
                Elemento e = campoDeBatalla[i][j];
                if (e instanceof Destruible) {
                    elementosADestruir.add(e);
                }
            }
        }

        // Eliminar elementos destruibles del campo de batalla y la lista de elementos
        for (Elemento e : elementosADestruir) {
            System.out.println(((Destruible) e).destruir());
            campoDeBatalla[e.getPosicion().getRenglon()][e.getPosicion().getColumna()] = null;
            elementos.remove(e);
        }
    }

    // Método para obtener la bomba en una posición específica
    public Bomba obtenerBombaEnPosicion(Posicion p) {
        Elemento e = campoDeBatalla[p.getRenglon()][p.getColumna()];
        if (e instanceof Bomba) {
            return (Bomba) e;
        }
        return null;
    }

    // Método para cargar la configuración inicial desde un archivo
    public void cargarConfiguracionDesdeArchivo(String archivo) {
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
                        agregarElemento(new Terricola(nombre, this, posicion));
                        break;
                    case "Extraterrestre":
                        agregarElemento(new Extraterrestre("Alien", this, posicion));
                        break;
                    case "Roca":
                        agregarElemento(new Roca(this, posicion));
                        break;
                    case "Bomba":
                        int radio = Integer.parseInt(partes[3]);
                        agregarElemento(new Bomba(this, posicion, radio));
                        break;
                    default:
                        System.out.println("Elemento desconocido en el archivo: " + tipo);
                }
            }
        } catch (IOException ex) {
            System.out.println("Error al leer el archivo de configuración: " + ex.getMessage());
        }
    }

    // Método para guardar la configuración actual en un archivo
    public void guardarConfiguracionEnArchivo(String archivo) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
            for (Elemento elemento : elementos) {
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

    // Método para obtener todos los elementos
    public ArrayList<Elemento> getElementos() {
        return elementos;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (campoDeBatalla[i][j] == null) {
                    sb.append("0 ");
                } else if (campoDeBatalla[i][j] instanceof Terricola) {
                    sb.append("T ");
                } else if (campoDeBatalla[i][j] instanceof Extraterrestre) {
                    sb.append("E ");
                } else if (campoDeBatalla[i][j] instanceof Roca) {
                    sb.append("R ");
                } else if (campoDeBatalla[i][j] instanceof Bomba) {
                    sb.append("B ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
