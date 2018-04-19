package es.urjc.mov.mcristin.quor.Tablero;

import android.util.Log;

/**
 * Created by crixx on 7/04/18.
 */

public class Tablero {
    public static final int FILAS = 4;
    public static final int COLUMNAS = 4;

    public Casilla[][] tablero;

    private static final String TAG = "TABLERO --> ";

    public Tablero() {
        tablero = new Casilla[FILAS][COLUMNAS];
    }

    public void pintaCasillasTablero(){
        Log.d(TAG, "======================");
        for(int i = 0; i < FILAS; i++){
            for(int j = 0 ; j < COLUMNAS; j++){
                Log.d(TAG, "Casilla: ("+i+", "+j+")");
            }
        }
        Log.d(TAG, "======================");

    }


}
