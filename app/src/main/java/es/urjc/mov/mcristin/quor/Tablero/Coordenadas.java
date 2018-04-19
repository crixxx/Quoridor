package es.urjc.mov.mcristin.quor.Tablero;

/**
 * Created by mcristin on 8/02/18.
 */

public class Coordenadas {
    public int x;
    public int y;


    public Coordenadas(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o){
        Coordenadas c = (Coordenadas)o;
        return (c.getX() == this.getX() && c.getY() == this.getY());
    }
}