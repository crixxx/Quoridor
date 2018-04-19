package es.urjc.mov.mcristin.quor.Tablero;

import android.content.Context;
import android.widget.ImageButton;

import es.urjc.mov.mcristin.quor.Tablero.Coordenadas;

public class Casilla extends android.support.v7.widget.AppCompatImageButton{

    Coordenadas coordenadas = new Coordenadas(0,0);

    public enum Estado {
        LIBRE(0),
        PARED(1),
        MI_FICHA(2),
        FICHA_IA(3);

        Estado(int index) {
            this.index = index;
        }

        private int index;

        public static int MaxEstados() {
            return 4;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) { this.index = index; }
    }

    public Estado estadoCasilla;

    public Casilla(Context context, Coordenadas coordenadas, Estado estadoCasilla){
        super(context);
        this.coordenadas = coordenadas;
        this.estadoCasilla = estadoCasilla;
    }


    public Coordenadas getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(Coordenadas coordenadas) {
        this.coordenadas = coordenadas;
    }

    public Estado getEstadoCasilla() {
        return estadoCasilla;
    }

    public void setEstadoCasilla(Estado estadoCasilla) {
        this.estadoCasilla = estadoCasilla;
    }

}