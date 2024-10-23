public class Bomba extends Elemento implements Destruible {
    private int radio;

    public Bomba(Escenario escenario, Posicion posicion, int radio) {
        super(escenario, posicion);
        this.radio = radio;
    }

    public void explotar() {
        System.out.println("Explotando bomba!!");
        escenario.destruirElementos(posicion, radio);
        // Bomba itself is destroyed
        System.out.println(destruir());
    }

    @Override
    public String destruir() {
        return "Bomba destruida";
    }
}
