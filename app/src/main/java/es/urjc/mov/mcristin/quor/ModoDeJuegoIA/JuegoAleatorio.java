package es.urjc.mov.mcristin.quor.ModoDeJuegoIA;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import es.urjc.mov.mcristin.quor.Juego;
import es.urjc.mov.mcristin.quor.Tablero.Casilla;
import es.urjc.mov.mcristin.quor.Tablero.Coordenadas;

import static es.urjc.mov.mcristin.quor.Juego.arrayParedes;
import static es.urjc.mov.mcristin.quor.Tablero.Casilla.Estado.FICHA_IA;
import static es.urjc.mov.mcristin.quor.Tablero.Casilla.Estado.LIBRE;
import static es.urjc.mov.mcristin.quor.Tablero.Casilla.Estado.MI_FICHA;
import static es.urjc.mov.mcristin.quor.Tablero.Casilla.Estado.PARED;
import static es.urjc.mov.mcristin.quor.Tablero.Tablero.COLUMNAS;
import static es.urjc.mov.mcristin.quor.Tablero.Tablero.FILAS;

/**
 * Created by crixx on 7/04/18.
 */

public class JuegoAleatorio extends ModoDeJuegoIA {

    static Juego juego = new Juego();

    static List<Casilla> posiblesMovs = new ArrayList<Casilla>();
    static Casilla anteriorCasillaIA;
    static Casilla newCasilla;
    static Casilla movimiento;

    static Coordenadas coorIA = new Coordenadas(0,0);

    public static Casilla casillaOptima(List<Casilla> arrayPosiblesMovs, Context context){

        int i = 0;
        Casilla casillaOptima = arrayPosiblesMovs.get(i);
        int random = (int) (Math.random() * 100 +1);

        for(int j = 0; j < arrayPosiblesMovs.size(); j++){
            if(arrayPosiblesMovs.get(j).getEstadoCasilla() == MI_FICHA
                    || arrayPosiblesMovs.get(j).getEstadoCasilla() == FICHA_IA){
                arrayPosiblesMovs.remove(j);
            }
        }

        if(random >= 30){
            for(i = 0 ;i < arrayPosiblesMovs.size(); i++){
                if(arrayPosiblesMovs.get(i).getCoordenadas().getX() >= casillaOptima.getCoordenadas().getX()){
                    casillaOptima = arrayPosiblesMovs.get(i);
                }else{
                    continue;
                }
            }
        }else{
            casillaOptima = ponParedRndn(context);
        }

        movimiento = casillaOptima;
        return casillaOptima;
    }

    public static Casilla[][] MueveFicha(Context context, Casilla[][] tab) {

        anteriorCasillaIA = new Casilla(context,coorIA, LIBRE);
        posiblesMovs = juego.getPosiblesMovs(context, anteriorCasillaIA);
        boolean b = false; // Cuando sea posible movimiento si no, que vuelva a hacer click

        newCasilla = casillaOptima(posiblesMovs, context);

        if(newCasilla.getEstadoCasilla() == PARED){
            for(int i = 0; i < FILAS; i++){
                for(int j = 0 ; j < COLUMNAS; j++){
                    if(tab[i][j].getCoordenadas().getX() == newCasilla.getCoordenadas().getX()
                            && tab[i][j].getCoordenadas().getY() == newCasilla.getCoordenadas().getY()){
                        tab[i][j].setEstadoCasilla(PARED);
                        b = true;
                    }
                }

            }
        }else{
            if(newCasilla != null){
                for(int i = 0; i < FILAS; i++){
                    for(int j = 0 ; j < COLUMNAS; j++){
                        if(tab[i][j].getCoordenadas().getX() == newCasilla.getCoordenadas().getX()
                                && tab[i][j].getCoordenadas().getY() == newCasilla.getCoordenadas().getY()){
                            tab[i][j].setEstadoCasilla(FICHA_IA);
                        }else if(tab[i][j].getCoordenadas().getX() == anteriorCasillaIA.getCoordenadas().getX()
                                && tab[i][j].getCoordenadas().getY() == anteriorCasillaIA.getCoordenadas().getY()){
                            tab[i][j].setEstadoCasilla(LIBRE);
                        }
                    }

                }
                coorIA = new Coordenadas(newCasilla.getCoordenadas().getX(), newCasilla.getCoordenadas().getY());
            }else{
                tab = null;
            }
        }
        anteriorCasillaIA = newCasilla;

        return tab;
    }

    public static Casilla ponParedRndn(Context context){
        Random r = new Random();
        int x;
        int y;
        r.nextInt(FILAS + 1);

        x = (int) (Math.random() * COLUMNAS);
        y = (int) (Math.random() * FILAS);

        Coordenadas coordenadas = new Coordenadas(x,y);
        Casilla pared = new Casilla(context,coordenadas, PARED);
        arrayParedes.add(pared);

        return pared;

    }

    public Casilla[][] iniciaPartida(Context context, Casilla[][] tablero){
        for(int i = 0; i < FILAS; i++){
            for(int j = 0 ; j < COLUMNAS; j++){
                if(tablero[i][j].getCoordenadas().getX() == 0 && tablero[i][j].getCoordenadas().getY() == 0) {
                    tablero[i][j].setEstadoCasilla(FICHA_IA);
                    anteriorCasillaIA = tablero[i][j];
                    newCasilla = anteriorCasillaIA;
                }else if(tablero[i][j].getEstadoCasilla() == MI_FICHA){
                    tablero[i][j].setEstadoCasilla(MI_FICHA);
                }else{
                    tablero[i][j].setEstadoCasilla(LIBRE);
                }
            }

        }

        return tablero;
    }


    public static boolean compruebaGanador() {
        if(juego.esCasillaGanadora(movimiento.getCoordenadas(), "IA")){
            return true;
        }
        return false;
    }
}

