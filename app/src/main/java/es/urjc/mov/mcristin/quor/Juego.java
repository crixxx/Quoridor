package es.urjc.mov.mcristin.quor;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import es.urjc.mov.mcristin.quor.ModoDeJuegoIA.JuegoAleatorio;
import es.urjc.mov.mcristin.quor.Pantallas.InterfazUsuario;
import es.urjc.mov.mcristin.quor.Tablero.Casilla;
import es.urjc.mov.mcristin.quor.Tablero.Coordenadas;
import static es.urjc.mov.mcristin.quor.Tablero.Tablero.COLUMNAS;
import static es.urjc.mov.mcristin.quor.Tablero.Tablero.FILAS;

/**
 * Created by mcristin on 18/02/18.
 */

public class Juego {

    private static final String TAG = "JUEGO --> ";
    public static List<Casilla> arrayParedes = new ArrayList<Casilla>();
    Casilla casillaAnteriorIA;
    Casilla miCasillaAnterior;
    List<Casilla> posiblesMovs = new ArrayList<Casilla>();
    boolean ganador = false;

    JuegoAleatorio IA;

    public List<Casilla> arrayPosiblesMovimientos(){
        return posiblesMovs;
    }

    public Casilla[][] build(Context contexto){
        Casilla tab[][] = new Casilla[FILAS][COLUMNAS];
        Coordenadas miFicha = new Coordenadas(FILAS-1,COLUMNAS-1);
        Coordenadas fichaIA = new Coordenadas(0,0);
        miCasillaAnterior = new Casilla(contexto, miFicha, Casilla.Estado.LIBRE);
        casillaAnteriorIA = new Casilla(contexto, fichaIA, Casilla.Estado.LIBRE);

        for(int i = 0; i < FILAS; i++){
            for(int j = 0 ; j < COLUMNAS; j++){
                Casilla c = tab[i][j];
                if(c.equals(miCasillaAnterior)){
                    c.setEstadoCasilla(Casilla.Estado.MI_FICHA);
                    miCasillaAnterior = c;
                }else if(c.equals(casillaAnteriorIA)){
                    c.setEstadoCasilla(Casilla.Estado.FICHA_IA);
                    casillaAnteriorIA = c;
                }else{
                    c.setEstadoCasilla(Casilla.Estado.LIBRE);
                }
            }
        }
        return tab;
    }

    public Casilla[][] IniciaPartida(InterfazUsuario context, Casilla[][] tab) {
        arrayParedes.clear();
        Coordenadas miFicha = new Coordenadas(FILAS-1,COLUMNAS-1);
        Coordenadas fichaIA = new Coordenadas(0,0);
        miCasillaAnterior = new Casilla(context, miFicha, Casilla.Estado.LIBRE);
        casillaAnteriorIA = new Casilla(context, fichaIA, Casilla.Estado.LIBRE);

        for(int i = 0; i < FILAS; i++){
            for(int j = 0 ; j < COLUMNAS; j++){
                Casilla cas = tab[i][j];
                Coordenadas c = tab[i][j].getCoordenadas();
                if(c.equals(miCasillaAnterior.getCoordenadas())){
                    cas.setEstadoCasilla(Casilla.Estado.MI_FICHA);
                    miCasillaAnterior = cas;
                }else if(c.equals(casillaAnteriorIA.getCoordenadas())){
                    cas.setEstadoCasilla(Casilla.Estado.FICHA_IA);
                    casillaAnteriorIA = cas;
                }else{
                    cas.setEstadoCasilla(Casilla.Estado.LIBRE);
                }
            }
        }
        return tab;
    }

    //Mueve ficha cuando es el usuario
    public Casilla[][] MueveFicha(Context context, Coordenadas coordenadas, Casilla[][] tab) {

        if(esMovimientoValido(coordenadas)){
            for(int i = 0; i < FILAS; i++){
                for(int j = 0 ; j < COLUMNAS; j++){
                    Casilla cas = tab[i][j];
                    Coordenadas c = cas.getCoordenadas();
                    if(c.equals(coordenadas)){
                        cas.setEstadoCasilla(Casilla.Estado.MI_FICHA);
                    }else if(c.equals(miCasillaAnterior.getCoordenadas())){
                        cas.setEstadoCasilla(Casilla.Estado.LIBRE);
                    }
                }

            }

            miCasillaAnterior = new Casilla(context,coordenadas, Casilla.Estado.LIBRE);
        }else{
            tab = null;
        }

        return tab;
    }

    //Devuelve los posibles movimientos en forma de array de casillas
    public List<Casilla> getPosiblesMovs(Context context, Casilla casilla) {
        posiblesMovs.clear();
        Coordenadas arriba, abajo, izq, drch;
        Casilla cas1,cas2,cas3,cas4;

        int x = casilla.getCoordenadas().getX();
        int y = casilla.getCoordenadas().getY();

        //Calculo las coordenadas adyacentes
        arriba = new Coordenadas(x,y-1);
        abajo = new Coordenadas(x,y+1);
        drch = new Coordenadas(x+1,y);
        izq = new Coordenadas(x-1,y);

        List<Coordenadas> posiblesAux = new ArrayList<Coordenadas>();

        posiblesAux.add(arriba);
        posiblesAux.add(abajo);
        posiblesAux.add(drch);
        posiblesAux.add(izq);

        for(int i = 0; i < posiblesAux.size(); i++){
            if(esMovimientoValido(posiblesAux.get(i))
                    && casilla.getEstadoCasilla() != Casilla.Estado.PARED){
                Casilla cas = new Casilla(context,posiblesAux.get(i),Casilla.Estado.LIBRE);
                posiblesMovs.add(cas);
            }
        }

        List<Casilla> posiblesMovAux = posiblesMovs;

        for(int j = 0; j < posiblesMovAux.size(); j++){
            for(int k = 0; k < arrayParedes.size();k++){
                if(posiblesMovAux.get(j).getCoordenadas().getX() == arrayParedes.get(k).getCoordenadas().getX()
                        && posiblesMovAux.get(j).getCoordenadas().getY() == arrayParedes.get(k).getCoordenadas().getY()){
                    posiblesMovAux.remove(j);
                }
            }

        }

        return posiblesMovAux;
    }

    private boolean esMovimientoValido(Coordenadas coordenadas) {
        boolean esValido = true;

        if(coordenadas.getX() >= FILAS || coordenadas.getY() >= COLUMNAS ||
                coordenadas.getX() < 0 || coordenadas.getY() < 0) {
            esValido = false;
        }else{
            //Busco si es pared
            for(int i = 0;i < arrayParedes.size(); i++){
                if(arrayParedes.get(i).getX() == coordenadas.getX()
                        && arrayParedes.get(i).getY() == coordenadas.getY()){
                    esValido = false;
                    break;
                }else{
                    continue;
                }
            }
        }

        return esValido;
    }

    public boolean haGanado(Coordenadas coordenadas) {

        if(coordenadas.getX() >= FILAS){
            ganador = true;
        }

        return ganador;
    }


    public Casilla[][] ponPared(Coordenadas coordenadas, Casilla[][] tab) {

        for(int i = 0; i < FILAS; i++){
            for(int j = 0 ; j < COLUMNAS; j++){
                if(tab[i][j].getCoordenadas().getX() == coordenadas.getX()
                        && tab[i][j].getCoordenadas().getY() == coordenadas.getY()){
                    tab[i][j].setEstadoCasilla(Casilla.Estado.PARED);
                    arrayParedes.add(tab[i][j]);
                }
            }

        }
        return tab;
    }

    public Casilla getCasilla(){
        return miCasillaAnterior;
    }

    public boolean estaEntrePosibles(Coordenadas coordenadas, Context contexto, Casilla posicionJugador) {
        List<Casilla> posiblesMovimientos;
        boolean estaEntrePosibles = false;
        posiblesMovimientos = getPosiblesMovs(contexto, posicionJugador);
        for(int i = 0; i < posiblesMovimientos.size(); i++){
            if(posiblesMovimientos.get(i).getCoordenadas().getX() == coordenadas.getX()
                    && posiblesMovimientos.get(i).getCoordenadas().getY() == coordenadas.getY()){
                estaEntrePosibles = true;
            }
        }
        return estaEntrePosibles;
    }

    public boolean esCasillaGanadora(Coordenadas coordenadas, String jugador) {
        int x = coordenadas.getX();
        boolean esGanador = false;

        if(jugador == "IA"){
            if(x == FILAS-1){
                esGanador = true;
            }
        }else{
            if(x == 0){
                esGanador = true;
            }
        }

        return esGanador;
    }

    public Casilla[][] reiniciaPartida(Casilla[][] tablero, InterfazUsuario contexto) {
        Casilla newTab[][];
        newTab = build(contexto);
        //newTab = IA.iniciaPartida(contexto, newTab);
        return newTab;
    }
}