package es.urjc.mov.mcristin.quor.Pantallas;

import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import es.urjc.mov.mcristin.quor.R;


public class Login extends android.support.v7.app.AppCompatActivity  {
    Button bLogin,bCancel;
    EditText ed1;
    String login;
    final int MAX_USER = 10;
    ArrayList<String> arrayLogins = new ArrayList<String>();

    private void restaurarCampos(Bundle savedInstanceState){
        // Si hay algo en el bundle, es que se ha guardado algo y lo recuperaremos
        if (savedInstanceState != null) {
            return;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(savedInstanceState != null){
            restaurarCampos(savedInstanceState);
            //setContentView(R.layout.activity_menu_instrucciones);
        }

        bLogin = (Button)findViewById(R.id.button);
        ed1 = (EditText)findViewById(R.id.editText);

        bCancel = (Button)findViewById(R.id.button2);

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login = ed1.getText().toString();
                if(login != null) {
                    buscaLogin(login);
                    Toast.makeText(getApplicationContext(),
                            "Bienvenid@ " + login + "!",Toast.LENGTH_SHORT).show();
                    android.content.Intent main = new android.content.Intent(Login.this, InterfazUsuario.class);
                    main.putExtra("login", login);
                    startActivity(main);
                }
            }

        });

        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected void onDestroy(){
        super.onDestroy();
    }

    protected void onStart(){
        super.onStart();
    }

    protected void onPause(){
        super.onPause();
    }

    private void buscaLogin(String login) {
        boolean esNuevo = true;

        for(int i = 0; i < arrayLogins.size(); i++){
            if(login == arrayLogins.get(i)){
                esNuevo = false;
            }
        }

        if(!esNuevo){
            arrayLogins.add(login);
        }
    }

}