package com.uisrael.mejia.lector;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.security.NetworkSecurityPolicy;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.uisrael.mejia.control.LoginControl;
import com.uisrael.mejia.control.LoginControlImpl;
import com.uisrael.mejia.model.Usuario;

public class MainActivity extends AppCompatActivity {

    private Button mLogin;
    private EditText mUsuario, mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUsuario = findViewById(R.id.usuario);
        mPassword = findViewById(R.id.password);
        mLogin = findViewById(R.id.login);

    }

    public void login(View view) {
        if (validar()){
            AsyncLoginWs taskLogin=new AsyncLoginWs();
            Usuario us=new Usuario();
            us.setUsuario(mUsuario.getText().toString());
            us.setPwd(mPassword.getText().toString());
            taskLogin.execute(us);
        }else {
            AlertDialog.Builder dialogo = new AlertDialog.Builder(MainActivity.this);
            dialogo.setTitle("Resultado"); // setando título
            // setando mensagem
            dialogo.setMessage("Campos vacios");
            dialogo.setNeutralButton("OK", null); // setando botão
            dialogo.show();
            mPassword.setText("");

        }
    }

    private class AsyncLoginWs extends AsyncTask<Usuario, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Usuario... usuario) {
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
            LoginControl control = new LoginControlImpl();
            try {
                return control.login(usuario[0].getUsuario(), usuario[0].getPwd());
            } catch (Exception e) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean == false) {
                AlertDialog.Builder dialogo = new AlertDialog.Builder(MainActivity.this);
                dialogo.setTitle("Resultado"); // setando título
                dialogo.setMessage("Usuario o contraseña incorrecta");
                dialogo.setNeutralButton("OK", null); // setando botão
                mPassword.setText("");
                dialogo.show();   // chamando o AlertDialog
            }else{
                startActivity(new Intent(MainActivity.this,ListActivity.class));
            }
        }
    }

    public boolean validar() {
        if (mUsuario.getText().toString() == null || mUsuario.getText().toString().trim().isEmpty()) {
            return false;
        }
        if (mPassword.getText().toString() == null || mPassword.getText().toString().trim().isEmpty()) {
            return false;
        }
        return true;
    }
}
