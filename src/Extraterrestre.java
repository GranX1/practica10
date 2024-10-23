public class Extraterrestre extends Personaje implements Destruible {

    public Extraterrestre(String nombre, Escenario escenario, Posicion posicion) {
        super(nombre, escenario, posicion);
    }

    @Override
    public String destruir() {
        return nombre + " (Extraterrestre) destruido";
    }
}
