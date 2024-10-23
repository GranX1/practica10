import java.util.ArrayList;

public class Escenario {
    private String nombre;
    private Elemento[][] campoDeBatalla;

    public Escenario(String nombre) {
        this.nombre = nombre;
        this.campoDeBatalla = new Elemento[10][10]; // Fixed size 10x10 for simplicity
    }

    public void agregarElemento(Elemento e) {
        Posicion p = e.getPosicion();
        campoDeBatalla[p.getRenglon()][p.getColumna()] = e;
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


        for (Elemento e : elementosADestruir) {
            System.out.println(((Destruible) e).destruir());
            campoDeBatalla[e.getPosicion().getRenglon()][e.getPosicion().getColumna()] = null; // Remove from grid
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i <10; i++) {
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
