public class MisionPosibleMain {
    public static void main(String[] args) {
        Escenario e = new Escenario("Nostromo");


        e.agregarElemento(new Terricola("Ripley", e, new Posicion(3, 2)));
        e.agregarElemento(new Extraterrestre("Alien", e, new Posicion(3, 5)));
        e.agregarElemento(new Roca(e, new Posicion(4, 3)));


        Bomba b= new Bomba(e, new Posicion(4, 4), 1);
        e.agregarElemento(b);


        System.out.println(e);


        b.explotar();
    }
}
