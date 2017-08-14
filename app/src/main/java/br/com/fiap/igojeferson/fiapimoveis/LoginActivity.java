package br.com.fiap.igojeferson.fiapimoveis;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private final String PREFERENCE = "FIAPImoveis";
    private final String PREF_LOGIN = "login";
    private final String PREF_PASS = "senha";
    private final String PREF_KEEP_CONNECTED = "keep";
    private EditText etLogin;
    private EditText etSenha;
    private CheckBox cbManterConectado;
    private static final boolean superMode = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //FIELDS
        etLogin = (EditText) findViewById(R.id.etLogin);
        etSenha = (EditText) findViewById(R.id.etSenha);
        cbManterConectado = (CheckBox) findViewById(R.id.cbManterConectado);

        //GET SHARED PREFERENCES
        SharedPreferences configLogin = getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        //SHOW PREVIOUS STORED DATA
        etLogin.setText(configLogin.getString(PREF_LOGIN,""));
        //etSenha.setText(configLogin.getString(PREF_PASS,""));
        boolean checked = configLogin.getBoolean(PREF_KEEP_CONNECTED, false);


        //START MAIN ACTIVITY IF CHECKED IS TRUE AND LOGIN?PASSWORD IS CORRECT
        if(checked){
            if(isValidUser(etLogin.getText().toString(), etSenha.getText().toString())){
                openMain();
            }
        }
    }

    //CLICK BUTTON ACTION
    public void doLogin(View v){
        String login = etLogin.getText().toString();
        String password = etSenha.getText().toString();
        boolean keepLogged = cbManterConectado.isChecked();
        if(isValidUser(login,password)){
            if(keepLogged){
                //CREATE AND SET SHARED PREFERENCES
                SharedPreferences configLogin = getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = configLogin.edit();
                editor.putString(PREF_LOGIN, login);
                editor.putString(PREF_PASS, password);
                editor.putBoolean(PREF_KEEP_CONNECTED, keepLogged);
                editor.commit();
            }
            openMain();
        }else{
            final View snackBarCoordinatorLayoutView = findViewById(R.id.snackbar_position);
            //Snackbar.make(snackBarCoordinatorLayoutView,getString(R.string.textLoginInvalido),Snackbar.LENGTH_LONG).show();
            Snackbar.make(snackBarCoordinatorLayoutView,"Login invalido!",Snackbar.LENGTH_LONG).show();
        }
    }

    private boolean isValidUser(String Login, String password){
        boolean returnVar = false;
        //SUPER MODE -> JUST FOR TESTERS
        if(superMode){
            if(Login.equals("fiap") && password.equals("123")){
                returnVar = true;
            }
        }
        return returnVar;
    }

    public void  openMain(){
        Intent i = new Intent(this, ListaImoveisActivity.class);
        startActivity(i);
        finish();
    }



}

