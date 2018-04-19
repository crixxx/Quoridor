package es.urjc.mov.mcristin.quor.Jugador;

import android.content.Context;

import es.urjc.mov.mcristin.quor.Tablero.Casilla;
import es.urjc.mov.mcristin.quor.Tablero.Coordenadas;

import static es.urjc.mov.mcristin.quor.Juego.arrayParedes;
import static es.urjc.mov.mcristin.quor.Tablero.Tablero.COLUMNAS;
import static es.urjc.mov.mcristin.quor.Tablero.Tablero.FILAS;


public class JugadorHumano extends Jugador {
    public JugadorHumano() {
        super();
        tipoJugador tipo = tipoJugador.HUMANO;
    }

    public void pedirJugada(){
        boolean miTurno = true;

        while(miTurno){

            miTurno = false;
        }
    }

}
