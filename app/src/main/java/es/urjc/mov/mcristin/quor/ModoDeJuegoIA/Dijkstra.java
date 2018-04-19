package es.urjc.mov.mcristin.quor.ModoDeJuegoIA;

import java.util.List;

import es.urjc.mov.mcristin.quor.Tablero.Casilla;
import es.urjc.mov.mcristin.quor.Tablero.Coordenadas;
import es.urjc.mov.mcristin.quor.Juego;
import static es.urjc.mov.mcristin.quor.Tablero.Casilla.Estado.PARED;
import static es.urjc.mov.mcristin.quor.Tablero.Tablero.COLUMNAS;
import static es.urjc.mov.mcristin.quor.Tablero.Tablero.FILAS;

/**
 * Created by crixx on 30/03/18.
 */
public class Dijkstra extends ModoDeJuegoIA{
    // Inundacion primero: inicializar a false las casillas, y si encuentra camino a true, si esta ocupada, false,
    // for para recorrer todas la casillas a true,
    // el destino con 0, si devuelve true es porque hay camino, destino 0, vecinas, sumadas 1, y asi hasta llegar al origen,
    // devuelvw un arraylist de posiciones, la ultima posicion es el movimiento

    static final int infinito = 9999;
    Juego metodosJuego = new Juego();

    public Dijkstra(Casilla casilla) {
        List<Casilla> posiblesMovimientos = null;
        int peso = 0;
        boolean esValido = true;
        int arrayPesos[] = null;
        int j = 0;

        if(casilla != null){
            posiblesMovimientos = metodosJuego.arrayPosiblesMovimientos();
        }

        for(int i = 0; i < posiblesMovimientos.size(); i++){
            Coordenadas coordenadas = posiblesMovimientos.get(i).getCoordenadas();

            //Comprobamos que la casilla no esta fuera del tablero
            if(coordenadas.getX() >= FILAS || coordenadas.getY() >= COLUMNAS ||
                    coordenadas.getX() < 0 || coordenadas.getY() < 0) {
                esValido = false;
            }

            //Damos pesos segun sea camino optimo o no.
            if(esValido){
                if(posiblesMovimientos.get(i).getEstadoCasilla() == PARED) {
                    peso = infinito;
                } else {
                    if(posiblesMovimientos.get(i).getCoordenadas().getY() < casilla.getCoordenadas().getY()) {
                        peso = 0;
                    } else if (posiblesMovimientos.get(i).getCoordenadas().getY() == casilla.getCoordenadas().getY()) {
                        peso = 1;
                    } else {
                        peso = 2;
                    }
                }
                arrayPesos[j] = peso;
                j++;
            }

        }

    }
}
