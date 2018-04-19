package es.urjc.mov.mcristin.quor;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import es.urjc.mov.mcristin.quor.ModoDeJuegoIA.ModoDeJuegoIA;
import es.urjc.mov.mcristin.quor.Tablero.Coordenadas;


/**
 * Created by crixx on 2/04/18.
 */

public class Notas {
    /*

package es.urjc.mov.mcristin.quor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.makeText;
import static es.urjc.mov.mcristin.quor.Casilla.Estado.FICHA_IA;
import static es.urjc.mov.mcristin.quor.Casilla.Estado.LIBRE;
import static es.urjc.mov.mcristin.quor.Casilla.Estado.MI_FICHA;
import static es.urjc.mov.mcristin.quor.Casilla.Estado.PARED;

public class InterfazUsuario extends AppCompatActivity {

    static final int FILAS = 4;
    static final int COLUMNAS = 4;
    private static final String TAG = "USER --> ";

    int dimension = FILAS*COLUMNAS;
    boolean esInicio = true;
    boolean ganador = false;

    Casilla tablero[][] = new Casilla[FILAS][COLUMNAS];
    Casilla newTab[][] = new Casilla[FILAS][COLUMNAS];
    Casilla tableroInicio[][] = new Casilla[FILAS][COLUMNAS];
    Juego juego = new Juego();
    IntArt IA = new IntArt();
    Casilla posicionIA = null;
    Casilla posicionJugador = null;
    Coordenadas coorIni = new Coordenadas(FILAS-1,COLUMNAS-1);
    Coordenadas coorIniIA = new Coordenadas(0,0);
    boolean botonJugar = false;

    static Juego juegoRecuperado = new Juego();
    static IntArt maquinaRecuperada = new IntArt();

    public void tableroToString(Casilla[][] tablero){
        for(int i = 0; i < FILAS; i++){
            for(int j = 0 ; j < COLUMNAS; j++){
                if(tablero[i][j].getEstadoCasilla() != LIBRE){
                    Log.d(TAG, "Estado = "+ tablero[i][j].getEstadoCasilla() + " Coordenadas: ("+ tablero[i][j].getCoordenadas().getX()
                            + "," + tablero[i][j].getCoordenadas().getY()+ ")");
                }
            }
        }
    }


    private ArrayList<Integer> guardaEstadosCasillas(Casilla tablero[][]){
        ArrayList<Integer> estadosCasillas = new ArrayList<Integer>();
        Integer estado = 0;
        for(int i = 0; i < FILAS; i++){
            for(int j = 0; j < COLUMNAS;j++){
                estado = tablero[i][j].getEstadoCasilla().getIndex();
                estadosCasillas.add(estado);
            }
        }
        return estadosCasillas;
    }

    private void redibujaTablero(Casilla[][] newTab) {

        Log.d(TAG, "***********************************************************");

        for(int i = 0; i < FILAS; i++){
            for(int j = 0 ; j < COLUMNAS; j++){
                Casilla.Estado est = newTab[i][j].getEstadoCasilla();
                int draw = getCasillaDrawable(est);
                tablero[i][j].setBackgroundResource(draw);
                Log.d(TAG, "CASILLA: "+ tablero[i][j].getEstadoCasilla() + " - COORDENADAS : (" + i + "," + j + ")");
            }

        }
    }

    private int getCasillaDrawable(Casilla.Estado est) {
        int draw[] = new int[Casilla.Estado.MaxEstados()];
        draw[PARED.getIndex()] = R.drawable.red_button_peq;
        draw[LIBRE.getIndex()] = R.drawable.blue_button_peq;
        draw[MI_FICHA.getIndex()] = R.drawable.green_piece;
        draw[FICHA_IA.getIndex()] = R.drawable.red_piece;

        return draw[est.getIndex()];
    }

    public class EmpezarPartida implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            posicionJugador = new Casilla(InterfazUsuario.this, coorIni, Casilla.Estado.MI_FICHA);
            posicionIA = new Casilla(InterfazUsuario.this, coorIniIA, Casilla.Estado.FICHA_IA);
            Casilla auxTab[][];

            int p = 0;
            tablero = reiniciaTablero();
            auxTab = reiniciaTablero();

            newTab = juego.IniciaPartida(InterfazUsuario.this,auxTab);
            //newTab = IA.iniciaPartida(InterfazUsuario.this, newTab);

            tablero = newTab;
            redibujaTablero(tablero);
            esInicio = false;
            ImageView imgMiFicha = findViewById(R.id.userIcon);
            ImageView imgIAFicha = findViewById(R.id.iAIcon);

            imgMiFicha.setVisibility(View.INVISIBLE);
            imgIAFicha.setVisibility(View.INVISIBLE);
            newTab = tablero;
            tablero = newTab;

            botonJugar = true;
        }
    }

    public class Movimiento implements View.OnClickListener {
        int x;
        int y;

        public Movimiento(Coordenadas coordenadas){
            x = coordenadas.getX();
            y = coordenadas.getY();
        }

        @Override
        public void onClick(View b) {
            Coordenadas coordenadas = new Coordenadas(x,y);
            Casilla casilla = new Casilla(InterfazUsuario.this,coordenadas, LIBRE);
            int time = Toast.LENGTH_SHORT;
            List<Casilla> posiblesMovimientos = null;
            Toast msg;
            boolean esValida = false;

            posiblesMovimientos = juego.getPosiblesMovs(InterfazUsuario.this, posicionJugador);
            for(int i = 0; i < posiblesMovimientos.size(); i++){
                if(posiblesMovimientos.get(i).getCoordenadas().getX() == coordenadas.getX()
                        && posiblesMovimientos.get(i).getCoordenadas().getY() == coordenadas.getY()){
                    esValida = true;
                }
            }

            if(esValida){
                posicionJugador = casilla;
                if(x == 0){
                    ganador = true;
                    msg = makeText(InterfazUsuario.this, "Has ganado!", Toast.LENGTH_LONG);
                    msg.show();
                }else if(posicionIA.getCoordenadas().getX() == FILAS-1){
                    ganador = true;
                    msg = makeText(InterfazUsuario.this, "Oh! Has perdido!", Toast.LENGTH_LONG);
                    msg.show();
                }

                if(ganador){
                    tablero = reiniciaTablero();
                    newTab = juego.IniciaPartida(InterfazUsuario.this,tablero);
                    newTab = IA.iniciaPartida(InterfazUsuario.this, newTab);
                    redibujaTablero(newTab);
                    ganador = false;
                    posicionIA = null;
                }else{
                    // Mi turno
                    newTab = juego.MueveFicha(InterfazUsuario.this, coordenadas, tablero);
                    redibujaTablero(newTab);
                    tablero = newTab;

                    // Su turno
                    newTab = IA.MueveFicha(InterfazUsuario.this, tablero);
                    redibujaTablero(newTab);
                    tablero = newTab;
                }
            }
        }
    }

    private Casilla[][] reiniciaTablero() {

        for(int i = 0; i < FILAS; i++){
            for(int j = 0 ; j < COLUMNAS; j++){
                tablero[i][j].setEstadoCasilla(LIBRE);
            }
        }

        return tablero;
    }


    public class Pared implements View.OnLongClickListener {
        int x;
        int y;

        public Pared(Coordenadas coordenadas) {
            x = coordenadas.getX();
            y = coordenadas.getY();
        }

        @Override
        public boolean onLongClick(View view) {
            Coordenadas coordenadas =  new Coordenadas(x,y);
            Toast msg;
            Casilla[][] aux = null;

            newTab = juego.ponPared(coordenadas, tablero);
            redibujaTablero(newTab);

            //Su turno
            aux = IA.MueveFicha(InterfazUsuario.this,newTab);
            redibujaTablero(aux);

            return true;
        }
    }


    public class MostrarInstrucciones implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent instrucciones = new Intent(InterfazUsuario.this, MenuInstrucciones.class);
            startActivity(instrucciones);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interfaz_usuario);

        if(savedInstanceState != null){
            recuperaEstadosCasillas(savedInstanceState);
            return;
        }

        //Creamos la tabla
        TableRow[] tr = new TableRow[dimension];
        TableLayout tl = (TableLayout)findViewById(R.id.tableLayout);

        Button bJugar = (Button) findViewById(R.id.bJugar);
        bJugar.setOnClickListener(new EmpezarPartida());

        Button bInstrucciones = (Button) findViewById(R.id.bInstrucciones);
        bInstrucciones.setOnClickListener(new MostrarInstrucciones());

        for(int i = 0;i < FILAS;i++){
            tr[i] = new TableRow(this);
            TableRow.LayoutParams lr = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT);
            tr[i].setLayoutParams(lr);
            tl.addView(tr[i]);

            for(int j = 0; j < COLUMNAS; j++){
                Coordenadas c = new Coordenadas(i,j);
                Casilla casilla = new Casilla(this,c, LIBRE);

                tablero[i][j] = casilla;

                tablero[i][j].setBackgroundResource(R.drawable.blue_button_peq);
                tablero[i][j].setOnClickListener(new Movimiento(c));
                tablero[i][j].setOnLongClickListener(new Pared(c));
                tablero[i][j].setLayoutParams(lr);
                tr[i].addView(tablero[i][j]);
            }
        }
    }

    /*@Override
    protected void onSaveInstanceState(Bundle estado){
        ArrayList<Integer> estadoTablero = guardaEstadosCasillas(tablero);
        super.onSaveInstanceState(estado);
        estado.putIntegerArrayList("estados", estadoTablero);
    }

    public Casilla[][] recuperaTablero(ArrayList<Integer> arrayEstados){
        Casilla[][] nuevoTablero = new Casilla[FILAS][COLUMNAS];
        int iterator = 0;

        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                Casilla.Estado estado = null;
                int index = arrayEstados.get(iterator);
                switch (index){
                    case 0:
                        estado = LIBRE;
                        break;
                    case 1:
                        estado = PARED;
                        break;
                    case 2:
                        estado = MI_FICHA;
                        break;
                    case 3:
                        estado = FICHA_IA;
                        break;

                }
                Coordenadas c = new Coordenadas(i,j);
                Casilla casilla = new Casilla(this,c, estado);
                nuevoTablero[i][j] = casilla;
                int draw = getCasillaDrawable(estado);
                nuevoTablero[i][j].setBackgroundResource(draw);
                iterator++;
            }
        }

        tablero = nuevoTablero;
        return nuevoTablero;
    }


    public void recuperaEstadosCasillas(Bundle savedInstanceState){
        ArrayList<Integer> arrayEstados = savedInstanceState.getIntegerArrayList("estados");
        Casilla[][] auxTab = new Casilla[FILAS][COLUMNAS];
        if(arrayEstados != null){
            Casilla[][] nuevoTablero = recuperaTablero(arrayEstados);

            //Creamos la nueva tabla
            TableRow[] tr = new TableRow[dimension];
            TableLayout tl = (TableLayout)findViewById(R.id.tableLayout);

            Button bJugar = (Button) findViewById(R.id.bJugar);
            bJugar.setOnClickListener(new EmpezarPartida());

            Button bInstrucciones = (Button) findViewById(R.id.bInstrucciones);
            bInstrucciones.setOnClickListener(new MostrarInstrucciones());

            for (int i = 0; i < FILAS; i++) {
                tr[i] = new TableRow(this);
                TableRow.LayoutParams lr = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT);
                tr[i].setLayoutParams(lr);
                tl.addView(tr[i]);
                for (int j = 0; j < COLUMNAS; j++) {
                    Coordenadas c = new Coordenadas(i,j);
                    Casilla casilla = new Casilla(this,c, LIBRE);

                    auxTab[i][j] = casilla;

                    auxTab[i][j].setBackgroundResource(R.drawable.blue_button_peq);

                    auxTab[i][j].setOnClickListener(new Movimiento(c));
                    auxTab[i][j].setOnLongClickListener(new Pared(c));
                    auxTab[i][j].setLayoutParams(lr);
                    tr[i].addView(auxTab[i][j]);
                }
            }

            /*auxTab = nuevoTablero;
            tablero = auxTab;
            redibujaTablero(auxTab);*/

/*}

    }


    @Override
    protected void onResume() {
        super.onResume();
        controladora.onVistaVisible();
    }

    public void onVistaVisible() {
        if(estadoAnterior != null){
            motor = estadoAnterior;
        }
        vista.actualizarTablero(motor.getTablero());
        vista.actualizarParedesJugador1(motor.getJugador1().getParedesDisponibles());
        vista.actualizarParedesJugador2(motor.getJugador2().getParedesDisponibles());
    }

     @Override
    protected void onDestroy() {
        super.onDestroy();
        controladora.onVistaOculta();
    }
    public void onVistaOculta() {
        estadoAnterior = motor;
    }
*/

/*
package es.urjc.mov.mcristin.quor;

import android.app.Fragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.makeText;
import static es.urjc.mov.mcristin.quor.Casilla.Estado.FICHA_IA;
import static es.urjc.mov.mcristin.quor.Casilla.Estado.LIBRE;
import static es.urjc.mov.mcristin.quor.Casilla.Estado.MI_FICHA;
import static es.urjc.mov.mcristin.quor.Casilla.Estado.PARED;

public class InterfazUsuario extends AppCompatActivity {

    static final int FILAS = 4;
    static final int COLUMNAS = 4;
    private static final String TAG = "USER --> ";

    int dimension = FILAS*COLUMNAS;
    boolean esInicio = true;
    boolean ganador = false;

    Casilla tablero[][] = new Casilla[FILAS][COLUMNAS];
    Casilla newTab[][] = new Casilla[FILAS][COLUMNAS];
    Juego juego = new Juego();
    IntArt IA = new IntArt();
    Casilla posicionIA = null;
    Casilla posicionJugador = null;
    Coordenadas coorIni = new Coordenadas(FILAS-1,COLUMNAS-1);
    Coordenadas coorIniIA = new Coordenadas(0,0);
    boolean botonJugar = false;

    //Variables para recuperar estado
    static Juego juegoRecuperado = new Juego();
    static IntArt maquinaRecuperada = new IntArt();
    static Casilla tableroRecuperado[][] = null;

    public void tableroToString(Casilla[][] tablero){
        for(int i = 0; i < FILAS; i++){
            for(int j = 0 ; j < COLUMNAS; j++){
                if(tablero[i][j].getEstadoCasilla() != LIBRE){
                    Log.d(TAG, "Estado = "+ tablero[i][j].getEstadoCasilla() + " Coordenadas: ("+ tablero[i][j].getCoordenadas().getX()
                            + "," + tablero[i][j].getCoordenadas().getY()+ ")");
                }
            }
        }
    }


    private ArrayList<Integer> guardaEstadosCasillas(Casilla tablero[][]){
        ArrayList<Integer> estadosCasillas = new ArrayList<Integer>();
        Integer estado = 0;
        for(int i = 0; i < FILAS; i++){
            for(int j = 0; j < COLUMNAS;j++){
                estado = tablero[i][j].getEstadoCasilla().getIndex();
                estadosCasillas.add(estado);
            }
        }
        return estadosCasillas;
    }

    private void redibujaTablero(Casilla[][] newTab) {

        Log.d(TAG, "***********************************************************");

        for(int i = 0; i < FILAS; i++){
            for(int j = 0 ; j < COLUMNAS; j++){
                Casilla.Estado est = newTab[i][j].getEstadoCasilla();
                int draw = getCasillaDrawable(est);
                tablero[i][j].setBackgroundResource(draw);
                Log.d(TAG, "CASILLA: "+ tablero[i][j].getEstadoCasilla() + " - COORDENADAS : (" + i + "," + j + ")");
            }

        }
    }

    private int getCasillaDrawable(Casilla.Estado est) {
        int draw[] = new int[Casilla.Estado.MaxEstados()];
        draw[PARED.getIndex()] = R.drawable.red_button_peq;
        draw[LIBRE.getIndex()] = R.drawable.blue_button_peq;
        draw[MI_FICHA.getIndex()] = R.drawable.green_piece;
        draw[FICHA_IA.getIndex()] = R.drawable.red_piece;

        return draw[est.getIndex()];
    }

    public class EmpezarPartida implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            posicionJugador = new Casilla(InterfazUsuario.this, coorIni, Casilla.Estado.MI_FICHA);
            posicionIA = new Casilla(InterfazUsuario.this, coorIniIA, Casilla.Estado.FICHA_IA);
            Casilla auxTab[][];

            int p = 0;
            tablero = reiniciaTablero();
            auxTab = reiniciaTablero();

            newTab = juego.IniciaPartida(InterfazUsuario.this,auxTab);
            //newTab = IA.iniciaPartida(InterfazUsuario.this, newTab);

            tablero = newTab;
            redibujaTablero(tablero);
            esInicio = false;
            ImageView imgMiFicha = findViewById(R.id.userIcon);
            ImageView imgIAFicha = findViewById(R.id.iAIcon);

            imgMiFicha.setVisibility(View.INVISIBLE);
            imgIAFicha.setVisibility(View.INVISIBLE);
            newTab = tablero;
            tablero = newTab;

            botonJugar = true;
        }
    }

    public class Movimiento implements View.OnClickListener {
        int x;
        int y;

        public Movimiento(Coordenadas coordenadas){
            x = coordenadas.getX();
            y = coordenadas.getY();
        }

        @Override
        public void onClick(View b) {
            Coordenadas coordenadas = new Coordenadas(x,y);
            Casilla casilla = new Casilla(InterfazUsuario.this,coordenadas, LIBRE);
            int time = Toast.LENGTH_SHORT;
            List<Casilla> posiblesMovimientos = null;
            Toast msg;
            boolean esValida = false;

            posiblesMovimientos = juego.getPosiblesMovs(InterfazUsuario.this, posicionJugador);
            for(int i = 0; i < posiblesMovimientos.size(); i++){
                if(posiblesMovimientos.get(i).getCoordenadas().getX() == coordenadas.getX()
                        && posiblesMovimientos.get(i).getCoordenadas().getY() == coordenadas.getY()){
                    esValida = true;
                }
            }

            if(esValida){
                posicionJugador = casilla;
                if(x == 0){
                    ganador = true;
                    msg = makeText(InterfazUsuario.this, "Has ganado!", Toast.LENGTH_LONG);
                    msg.show();
                }else if(posicionIA.getCoordenadas().getX() == FILAS-1){
                    ganador = true;
                    msg = makeText(InterfazUsuario.this, "Oh! Has perdido!", Toast.LENGTH_LONG);
                    msg.show();
                }

                if(ganador){
                    tablero = reiniciaTablero();
                    newTab = juego.IniciaPartida(InterfazUsuario.this,tablero);
                    newTab = IA.iniciaPartida(InterfazUsuario.this, newTab);
                    redibujaTablero(newTab);
                    ganador = false;
                    posicionIA = null;
                }else{
                    // Mi turno
                    newTab = juego.MueveFicha(InterfazUsuario.this, coordenadas, tablero);
                    redibujaTablero(newTab);
                    tablero = newTab;

                    // Su turno
                    newTab = IA.MueveFicha(InterfazUsuario.this, tablero);
                    redibujaTablero(newTab);
                    tablero = newTab;
                }
            }
        }
    }

    private Casilla[][] reiniciaTablero() {

        for(int i = 0; i < FILAS; i++){
            for(int j = 0 ; j < COLUMNAS; j++){
                tablero[i][j].setEstadoCasilla(LIBRE);
            }
        }

        return tablero;
    }


    public class Pared implements View.OnLongClickListener {
        int x;
        int y;

        public Pared(Coordenadas coordenadas) {
            x = coordenadas.getX();
            y = coordenadas.getY();
        }

        @Override
        public boolean onLongClick(View view) {
            Coordenadas coordenadas =  new Coordenadas(x,y);
            Toast msg;
            Casilla[][] aux = null;

            newTab = juego.ponPared(coordenadas, tablero);
            redibujaTablero(newTab);

            //Su turno
            aux = IA.MueveFicha(InterfazUsuario.this,newTab);
            redibujaTablero(aux);

            return true;
        }
    }


    public class MostrarInstrucciones implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent instrucciones = new Intent(InterfazUsuario.this, MenuInstrucciones.class);
            startActivity(instrucciones);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interfaz_usuario);

        Log.d(TAG, "ON CREATEEEEE!!");

        //dibujaTableroInicio();



        if(tableroRecuperado != null){
            //dibujaTableroInicio();
            tablero = tableroRecuperado;
            recuperaEstadosCasillas(tablero);
            redibujaTablero(tablero);
        }else{
            dibujaTableroInicio();
        }

        Log.d(TAG, "===============================");

    }

    public void recuperaEstadosCasillas(Casilla[][] tablero) {

        Casilla tabAux[][] = new Casilla[FILAS][COLUMNAS];

        TableRow[] tr = new TableRow[dimension];
        TableLayout tl = (TableLayout)findViewById(R.id.tableLayout);

        Button bJugar = (Button) findViewById(R.id.bJugar);
        bJugar.setOnClickListener(new EmpezarPartida());

        Button bInstrucciones = (Button) findViewById(R.id.bInstrucciones);
        bInstrucciones.setOnClickListener(new MostrarInstrucciones());

        ImageView imgMiFicha = findViewById(R.id.userIcon);
        ImageView imgIAFicha = findViewById(R.id.iAIcon);

        imgMiFicha.setVisibility(View.INVISIBLE);
        imgIAFicha.setVisibility(View.INVISIBLE);

        for(int i = 0; i < FILAS; i++){
            tr[i] = new TableRow(this);
            TableRow.LayoutParams lr = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT);
            tr[i].setLayoutParams(lr);
            tl.addView(tr[i]);
            for(int j = 0 ; j < COLUMNAS; j++){
                Coordenadas c = new Coordenadas(i,j);

                //Recupero los estados anteriores
                Casilla.Estado est = tablero[i][j].getEstadoCasilla();
                int draw;
                draw = getCasillaDrawable(est);

                Log.d(TAG, "CASILLA: "+ tablero[i][j].getEstadoCasilla() + " - COORDENADAS : (" + i + "," + j + ")");

                //Creo el tablero recuperado
                Casilla casilla = new Casilla(this,c, est);
                tabAux[i][j] = casilla;
                tabAux[i][j].setBackgroundResource(draw);
                tabAux[i][j].setOnClickListener(new Movimiento(c));
                tabAux[i][j].setOnLongClickListener(new Pared(c));
                tabAux[i][j].setLayoutParams(lr);
                tr[i].addView(tabAux[i][j]);
            }

        }

        tablero = tabAux;
    }

    private void dibujaTableroInicio() {

        //Creamos la tabla
        TableRow[] tr = new TableRow[dimension];
        TableLayout tl = (TableLayout)findViewById(R.id.tableLayout);

        Button bJugar = (Button) findViewById(R.id.bJugar);
        bJugar.setOnClickListener(new EmpezarPartida());

        Button bInstrucciones = (Button) findViewById(R.id.bInstrucciones);
        bInstrucciones.setOnClickListener(new MostrarInstrucciones());

        for(int i = 0;i < FILAS;i++){
            tr[i] = new TableRow(this);
            TableRow.LayoutParams lr = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT);
            tr[i].setLayoutParams(lr);
            tl.addView(tr[i]);

            for(int j = 0; j < COLUMNAS; j++){
                Coordenadas c = new Coordenadas(i,j);
                Casilla casilla = new Casilla(this,c, LIBRE);

                tablero[i][j] = casilla;

                tablero[i][j].setBackgroundResource(R.drawable.blue_button_peq);
                tablero[i][j].setOnClickListener(new Movimiento(c));
                tablero[i][j].setOnLongClickListener(new Pared(c));
                tablero[i][j].setLayoutParams(lr);
                tr[i].addView(tablero[i][j]);
            }
        }

    }

    @Override
    protected void onDestroy(){

        super.onDestroy();
        Log.d(TAG, "ON DESTROOOOOOOY!!!");
        juegoRecuperado = juego;
        tableroRecuperado = tablero;
        Log.d(TAG, "===============================");

    }

    @Override
    public void onResume() {

        super.onResume();

        Log.d(TAG, "ON RESUME!!!");

        if(juegoRecuperado != null && maquinaRecuperada != null && tableroRecuperado != null){
            tablero = tableroRecuperado;
            juego = juegoRecuperado;
            IA = maquinaRecuperada;

            redibujaTablero(tableroRecuperado);
        }
        Log.d(TAG, "===============================");

    }
}
 */



/*
package es.urjc.mov.mcristin.quor.ModoDeJuegoIA;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import es.urjc.mov.mcristin.quor.Casilla;
import es.urjc.mov.mcristin.quor.Tablero.Coordenadas;
import es.urjc.mov.mcristin.quor.Juego;

import static es.urjc.mov.mcristin.quor.Casilla.Estado.FICHA_IA;
import static es.urjc.mov.mcristin.quor.Casilla.Estado.LIBRE;
import static es.urjc.mov.mcristin.quor.Casilla.Estado.MI_FICHA;
import static es.urjc.mov.mcristin.quor.Casilla.Estado.PARED;
import static es.urjc.mov.mcristin.quor.Pantallas.InterfazUsuario.COLUMNAS;
import static es.urjc.mov.mcristin.quor.Pantallas.InterfazUsuario.FILAS;
import static es.urjc.mov.mcristin.quor.Juego.arrayParedes;


    class IntArt extends ModoDeJuegoIA {

        Juego juego = new Juego();

        List<Casilla> posiblesMovs = new ArrayList<Casilla>();
        Casilla anteriorCasillaIA;
        Casilla newCasilla;
        Casilla movimiento;

        Coordenadas coorIA = new Coordenadas(0,0);

        public Casilla casillaOptima(List<Casilla> arrayPosiblesMovs, Context context){

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

        public Casilla[][] MueveFicha(Context context, Casilla[][] tab) {

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

        public Casilla ponParedRndn(Context context){
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


        public boolean compruebaGanador() {
            if(juego.esCasillaGanadora(movimiento.getCoordenadas(), "IA")){
                return true;
            }
            return false;
        }
    }

    */
}