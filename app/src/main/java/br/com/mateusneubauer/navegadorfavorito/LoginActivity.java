package br.com.mateusneubauer.navegadorfavorito;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import br.com.mateusneubauer.navegadorfavorito.dao.LoginDao;
import br.com.mateusneubauer.navegadorfavorito.model.Login;

public class LoginActivity extends AppCompatActivity {

    public static final String KEY_APP_PREFERENCES = "info";
    public static final String KEY_LOGIN = "login";
    private TextInputLayout tilLogin;
    private TextInputLayout tilSenha;
    private CheckBox cbManterConectado;
    private Button btLogar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tilLogin = (TextInputLayout) findViewById(R.id.tilLogin);
        tilSenha = (TextInputLayout) findViewById(R.id.tilSenha);
        cbManterConectado = (CheckBox) findViewById(R.id.cbManterConectado);
        btLogar = (Button) findViewById(R.id.btLogar);
        if(isConectado())
            iniciarApp();

        btLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logar(view);
            }
        });
    }
    //Método que será chamado no onclick do botao
    public void logar(View v){
        if(isLoginValido()){
            if(cbManterConectado.isChecked()){
                manterConectado();
            }
            iniciarApp();
        }else {
            Toast.makeText(getApplicationContext(), "Usuario ou senha invalida", Toast.LENGTH_SHORT).show();
        }
    }
    // Valida o login
    private boolean isLoginValido() {
        String usuario = tilLogin.getEditText().getText().toString();
        String senha = tilSenha.getEditText().getText().toString();

        LoginDao loginDao = new LoginDao(this);
        Login login = loginDao.getByUsuario(usuario,senha);
        if(login != null) {
            return true;
        } else
            return false;
    }
    private void manterConectado(){
        String login = tilLogin.getEditText().getText().toString();
        SharedPreferences pref = getSharedPreferences(KEY_APP_PREFERENCES,
                MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(KEY_LOGIN, login);
        editor.apply();
    }
    private boolean isConectado() {
        SharedPreferences shared = getSharedPreferences(KEY_APP_PREFERENCES,MODE_PRIVATE);
        String login = shared.getString(KEY_LOGIN, "");
        if(login.equals(""))
            return false;
        else
            return true;
    }
    private void iniciarApp() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
