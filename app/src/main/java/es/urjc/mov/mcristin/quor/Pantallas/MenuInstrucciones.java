package es.urjc.mov.mcristin.quor.Pantallas;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import es.urjc.mov.mcristin.quor.R;


/**
 * Created by mcristin on 21/03/18.
 */

public class MenuInstrucciones extends Activity {

    private void restaurarCampos(Bundle savedInstanceState){

        // Si hay algo en el bundle, es que se ha guardado algo y lo recuperaremos
        if (savedInstanceState != null) {
            return;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_instrucciones);

        if(savedInstanceState != null){
            restaurarCampos(savedInstanceState);
            //setContentView(R.layout.activity_menu_instrucciones);
        }

        TextView titulo = (TextView) findViewById(R.id.textViewTitulo);
        TextView instrucciones = (TextView) findViewById(R.id.textViewInstrucciones);

        String tit = "INSTRUCCIONES:";
        String inst = "El objetivo de cada jugador es llegar con su peón a su  lado opuesto" +
                " antes de que lo hagan los contrarios. En tu turno tienes dos opciones, o mover " +
                "una casilla en horizontal o vertical (un click), nunca en diagonal o colocar una barrera (click largo). " +
                "Las barreras se pueden tocar unas con otras formando muros y en cualquier posicion del tablero" +
                "que no esté ocupado por un jugador. Una vez puestas no puedes moverlas así que hay que tener ojo a " +
                "administrarlas correctamente. Tampoco puedes encerrar completamente a los adversarios así que siempre " +
                "tienes que dejarle una salida para que pueda alcanzar su lado deseado.\n";

        titulo.setText(tit);
        titulo.setTypeface(null, Typeface.BOLD);

        instrucciones.setText(inst);

        ImageView imgTab=(ImageView) findViewById(R.id.imgTab);
        imgTab.setImageResource(R.drawable.example);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

}