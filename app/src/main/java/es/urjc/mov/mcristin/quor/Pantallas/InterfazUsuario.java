package es.urjc.mov.mcristin.quor.Pantallas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.util.ArrayList;

import es.urjc.mov.mcristin.quor.Juego;
import es.urjc.mov.mcristin.quor.ModoDeJuegoIA.JuegoAleatorio;
import es.urjc.mov.mcristin.quor.ModoDeJuegoIA.ModoDeJuegoIA;
import es.urjc.mov.mcristin.quor.R;
import es.urjc.mov.mcristin.quor.Tablero.Casilla;
import es.urjc.mov.mcristin.quor.Tablero.Coordenadas;

import static android.widget.Toast.makeText;
import static es.urjc.mov.mcristin.quor.Tablero.Casilla.Estado.FICHA_IA;
import static es.urjc.mov.mcristin.quor.Tablero.Casilla.Estado.LIBRE;
import static es.urjc.mov.mcristin.quor.Tablero.Casilla.Estado.MI_FICHA;
import static es.urjc.mov.mcristin.quor.Tablero.Casilla.Estado.PARED;
import static es.urjc.mov.mcristin.quor.Tablero.Tablero.COLUMNAS;
import static es.urjc.mov.mcristin.quor.Tablero.Tablero.FILAS;

public class InterfazUsuario extends AppCompatActivity {

    /*public static final int FILAS = 4;
    public static final int COLUMNAS = 4;*/
    private static final String TAG = "USER --> ";

    int dimension = FILAS * COLUMNAS;
    boolean esInicio = true;

    Casilla tablero[][] = new Casilla[FILAS][COLUMNAS];
    Casilla newTab[][] = new Casilla[FILAS][COLUMNAS];
    Juego juego = new Juego();
    ModoDeJuegoIA IA = new JuegoAleatorio();
    Casilla posicionIA = null;
    Casilla posicionJugador = null;
    Coordenadas coorIni = new Coordenadas(FILAS - 1, COLUMNAS - 1);
    Coordenadas coorIniIA = new Coordenadas(0, 0);

    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interfaz_usuario);


        Log.d(TAG, "ON CREATEEEEE!!");
        prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        guardarEstadoPartida(false);

        inicializarPosiciones();
        dibujaTableroInicio();

    }

    private void guardarEstadoPartida(Boolean haComenzado) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("estadoPartida", haComenzado);
        editor.apply();
    }

    private boolean obtenerEstadoPartida() {
        return prefs.getBoolean("estadoPartida", false);
    }

    // Para que el estado del activity se restaure correctamente es necesario llamar y hacer
    // override a esta función
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        int xJugador = savedInstanceState.getInt("xjugador");
        int yJugador = savedInstanceState.getInt("yjugador");
        Coordenadas coodenadaJugador = new Coordenadas(xJugador, yJugador);

        int xIA = savedInstanceState.getInt("xIA");
        int yIA = savedInstanceState.getInt("yIA");
        Coordenadas coodenadaIA = new Coordenadas(xIA, yIA);

        recuperaPosicionesJugadores(xJugador, yJugador, xIA, yIA);
        ArrayList<Integer> arrayEstados = savedInstanceState.getIntegerArrayList("estados");
        recuperaEstadosCasillas(arrayEstados);

    }

    private void inicializarPosiciones() {
        Coordenadas ini = new Coordenadas(FILAS - 1, COLUMNAS - 1);
        posicionIA = new Casilla(InterfazUsuario.this, ini, FICHA_IA);

        ini = new Coordenadas(0, 0);
        posicionJugador = new Casilla(InterfazUsuario.this, ini, MI_FICHA);
    }

    public void asignarIniciales(){
        tablero[posicionIA.getCoordenadas().getX()][posicionIA.getCoordenadas().getY()].setEstadoCasilla(posicionIA.getEstadoCasilla());
        tablero[posicionJugador.getCoordenadas().getX()][posicionJugador.getCoordenadas().getY()].setEstadoCasilla(posicionJugador.getEstadoCasilla());
    }

    //Recuperar estado
    @Override
    protected void onSaveInstanceState(Bundle estado) {
        super.onSaveInstanceState(estado);
        //Comprobar si es null
        if (estado == null) return;

        ArrayList<Integer> estadoTablero = guardaEstadosCasillas(tablero);
        estado.putIntegerArrayList("estados", estadoTablero);
        int xJugador = posicionJugador.getCoordenadas().getX();
        int yJugador = posicionJugador.getCoordenadas().getY();

        int xIA = posicionIA.getCoordenadas().getX();
        int yIA = posicionIA.getCoordenadas().getY();

        estado.putInt("xjugador", xJugador);
        estado.putInt("yjugador", yJugador);
        estado.putInt("xIA", xIA);
        estado.putInt("yIA", yIA);
    }

    private ArrayList<Integer> guardaEstadosCasillas(Casilla tablero[][]) {
        ArrayList<Integer> estadosCasillas = new ArrayList<Integer>();
        Integer estado = 0;
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                estado = tablero[i][j].getEstadoCasilla().getIndex();
                estadosCasillas.add(estado);
            }
        }
        return estadosCasillas;
    }

    // Pinta el tablero que se le pase
    private void redibujaTablero(Casilla[][] newTab) {

        Log.d(TAG, "***********************************************************");

        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                Casilla.Estado est = newTab[i][j].getEstadoCasilla();
                int draw = getCasillaDrawable(est);
                tablero[i][j].setBackgroundResource(draw);
            }
        }
    }

    //Dibuja imagen segun indice de estado
    private int getCasillaDrawable(Casilla.Estado est) {
        int draw[] = new int[Casilla.Estado.MaxEstados()];
        draw[PARED.getIndex()] = R.drawable.red_button_peq;
        draw[LIBRE.getIndex()] = R.drawable.blue_button_peq;
        draw[MI_FICHA.getIndex()] = R.drawable.green_piece;
        draw[FICHA_IA.getIndex()] = R.drawable.red_piece;

        return draw[est.getIndex()];
    }

    private void mostrarFichasIniciales() {
        ImageView imgMiFicha = findViewById(R.id.userIcon);
        ImageView imgIAFicha = findViewById(R.id.iAIcon);

        imgMiFicha.setVisibility(View.VISIBLE);
        imgIAFicha.setVisibility(View.VISIBLE);


    }

    private void mostrarToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void recuperaPosicionesJugadores(int xJugador, int yJugador, int xIA, int yIA) {
        coorIni = new Coordenadas(xJugador, yJugador);
        posicionJugador = new Casilla(InterfazUsuario.this, coorIni, MI_FICHA);

        coorIniIA = new Coordenadas(xIA, yIA);
        posicionIA = new Casilla(InterfazUsuario.this, coorIniIA, FICHA_IA);
    }

    public void recuperaEstadosCasillas(ArrayList<Integer> arrayEstados) {

        //Pinta nuevo tablero
        Casilla tabAux[][] = new Casilla[FILAS][COLUMNAS];

        TableRow[] tr = new TableRow[dimension];
        TableLayout tl = (TableLayout) findViewById(R.id.tableLayout);

        // Limpiar tablero
        tl.removeAllViews();

        Button bJugar = (Button) findViewById(R.id.bJugar);
        bJugar.setOnClickListener(new EmpezarPartida());

        Button bInstrucciones = (Button) findViewById(R.id.bInstrucciones);
        bInstrucciones.setOnClickListener(new MostrarInstrucciones());

        ImageView imgMiFicha = findViewById(R.id.userIcon);
        ImageView imgIAFicha = findViewById(R.id.iAIcon);

        // Validamos que haya comenzado la partida antes de hacer invisibles las fichas iniciales
        boolean haComenzadoPartida = obtenerEstadoPartida();
        if (haComenzadoPartida) {
            imgMiFicha.setVisibility(View.INVISIBLE);
            imgIAFicha.setVisibility(View.INVISIBLE);
        }

        int index = 0;

        for (int i = 0; i < FILAS; i++) {
            tr[i] = new TableRow(this);
            TableRow.LayoutParams lr = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT);
            tr[i].setLayoutParams(lr);
            tl.addView(tr[i]);
            for (int j = 0; j < COLUMNAS; j++) {
                Coordenadas c = new Coordenadas(i, j);

                //Recupero los estados anteriores
                Casilla.Estado est = null;

                switch (arrayEstados.get(index)) {
                    case 0: //Libre
                        est = LIBRE;
                        break;
                    case 1: //Pared
                        est = PARED;
                        break;
                    case 2: //Mi ficha
                        est = MI_FICHA;
                        break;
                    case 3: // Ficha IA
                        est = FICHA_IA;
                        break;
                }

                //Creo el tablero recuperado
                Casilla casilla = new Casilla(this, c, est);
                tabAux[i][j] = casilla;
                tabAux[i][j].setBackgroundResource(getCasillaDrawable(est));
                tabAux[i][j].setOnClickListener(new Movimiento(c));
                tabAux[i][j].setOnLongClickListener(new Pared(c));
                tabAux[i][j].setLayoutParams(lr);
                tr[i].addView(tabAux[i][j]);
                index++;
            }

        }

        tablero = tabAux;
    }

    private void dibujaTableroInicio() {
        // Limpiamos el table layout
        TableLayout tl = findViewById(R.id.tableLayout);
        tl.removeAllViews();

        //Creamos la tabla
        TableRow[] tr = new TableRow[dimension];

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
                Coordenadas c = new Coordenadas(i, j);
                Casilla casilla = new Casilla(this, c, LIBRE);

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
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //BOTON JUGAR
    public class EmpezarPartida implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            //Guardamos el estado actual de la partida para indicar que ha comenzado el juego
            inicializarPosiciones();
            guardarEstadoPartida(true);

            //Log.d(TAG, "coorIni: " + coorIni.x + " coorINi Y: " + coorIni.y);
            //posicionJugador = new Casilla(InterfazUsuario.this, coorIni, MI_FICHA);
            //posicionIA = new Casilla(InterfazUsuario.this, coorIniIA, FICHA_IA);

//            tablero = juego.reiniciaTablero(InterfazUsuario.this);
//            Log.d(TAG, "Tablero " + tablero[3][3].estadoCasilla);
//            auxTab = juego.reiniciaTablero(InterfazUsuario.this);

            newTab = juego.IniciaPartida(InterfazUsuario.this, tablero);
            //newTab = IA.iniciaPartida(InterfazUsuario.this, newTab);

            tablero = newTab;
            asignarIniciales();
            redibujaTablero(tablero);
            esInicio = false;
            ImageView imgMiFicha = findViewById(R.id.userIcon);
            ImageView imgIAFicha = findViewById(R.id.iAIcon);

            imgMiFicha.setVisibility(View.INVISIBLE);
            imgIAFicha.setVisibility(View.INVISIBLE);
            newTab = tablero;
            tablero = newTab;

        }
    }

    public class Movimiento implements View.OnClickListener {
        int x;
        int y;

        public Movimiento(Coordenadas coordenadas) {
            x = coordenadas.getX();
            y = coordenadas.getY();
        }

        @Override
        public void onClick(View b) {

            boolean haComenzadoPartida = obtenerEstadoPartida();

            // Validamos que haya comenzado la partida antes de ejecutar alguna jugada
            if (!haComenzadoPartida) {
                mostrarToast(getString(R.string.debe_seleccionar_jugar));
                return;
            }

            // Array con dos humanos(uno puede ser null), mirar turno(synchronize porque
            // lo puede mirar el otro yambien) si no es null, coge al humano
            // y le mete una jugada tablero.pedirJugada(x, y, pared)
            // el jugador tiene que ser un generador de jugadas
            // La juagada es una x una y y si es pared o movimiento == mi clase Casilla
            // Jugar =
            // Es el onclick el que mete la jugada
            // Tiene que haber un hilo que inicie la partida que no sabe si es humano o no


            Coordenadas coordenadas = new Coordenadas(x, y);
            Casilla casilla = new Casilla(InterfazUsuario.this, coordenadas, LIBRE);

            Log.d(TAG, "Coordenadas Pulsadas: " + coordenadas);

            Toast msg;

            boolean estaEntrePosibles;
            estaEntrePosibles = juego.estaEntrePosibles(coordenadas, InterfazUsuario.this, posicionJugador);

            boolean esCasillaGanadora = false;

            if (estaEntrePosibles) {
                posicionJugador = casilla;
                if (juego.esCasillaGanadora(coordenadas, "jugador")) {
                    msg = makeText(InterfazUsuario.this, "Has ganado!", Toast.LENGTH_LONG);
                    msg.show();
                    newTab = juego.reiniciaPartida(tablero, InterfazUsuario.this);
                    redibujaTablero(newTab);
                    tablero = newTab;
                    dibujaTableroInicio();
                    inicializarPosiciones();
                    asignarIniciales();
                    mostrarFichasIniciales();
                    guardarEstadoPartida(false);
                } else {
                    // Mi turno
                    newTab = juego.MueveFicha(InterfazUsuario.this, coordenadas, tablero);
                    Log.d(TAG, "Ha acabado MueveFicha");
                    redibujaTablero(newTab);
                    tablero = newTab;

                    // Su turno
                    newTab = JuegoAleatorio.MueveFicha(InterfazUsuario.this, tablero);
                    redibujaTablero(newTab);
                    tablero = newTab;
                    if (JuegoAleatorio.compruebaGanador()) {
                        msg = makeText(InterfazUsuario.this, "Oh!Has perdido!", Toast.LENGTH_LONG);
                        msg.show();
                        newTab = juego.reiniciaPartida(tablero, InterfazUsuario.this);
                        redibujaTablero(newTab);
                        tablero = newTab;
                        dibujaTableroInicio();
                        inicializarPosiciones();
                        asignarIniciales();
                        mostrarFichasIniciales();
                        guardarEstadoPartida(false);
                    }
                }
            }
        }
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
            Coordenadas coordenadas = new Coordenadas(x, y);
            Casilla[][] aux;

            newTab = juego.ponPared(coordenadas, tablero);
            redibujaTablero(newTab);

            //Su turno
            aux = JuegoAleatorio.MueveFicha(InterfazUsuario.this, newTab);
            redibujaTablero(aux);

            return true;
        }
    }

    public class MostrarInstrucciones implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent instrucciones = new Intent(InterfazUsuario.this, MenuInstrucciones.class);
            startActivity(instrucciones);
        }
    }

}