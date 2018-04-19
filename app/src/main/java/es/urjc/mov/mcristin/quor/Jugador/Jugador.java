package es.urjc.mov.mcristin.quor.Jugador;


public class Jugador {
    public Jugador() {

    }

    public enum tipoJugador {

        RED(0),
        HUMANO(1),
        IA(2);

        private int index;

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        tipoJugador(int index) {
            this.index = index;
        }

    }
}
