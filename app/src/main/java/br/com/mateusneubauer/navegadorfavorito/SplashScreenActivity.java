package br.com.mateusneubauer.navegadorfavorito;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import br.com.mateusneubauer.navegadorfavorito.dao.LoginDao;
import br.com.mateusneubauer.navegadorfavorito.model.Login;

/**
 * Created by Mateus on 14/04/2017.
 */

public class SplashScreenActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 5000;

    private ImageView ivLogo;
    private LoginDao loginDao = new LoginDao(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ivLogo = (ImageView) findViewById(R.id.ivLogo);

        Animation anim = AnimationUtils.loadAnimation(this,
                R.anim.animacao_splashscreen);
        anim.reset();

        if (ivLogo != null) {
            ivLogo.clearAnimation();
            ivLogo.startAnimation(anim);
        }

        new BuscaDados().execute("http://www.mocky.io/v2/58b9b1740f0000b614f09d2f");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this,
                        LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                SplashScreenActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    private class BuscaDados extends AsyncTask<String, Void, String> {

        ProgressDialog pdLoading;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading = new ProgressDialog(SplashScreenActivity.this);
            pdLoading.setMessage("Carregando os dados");
            pdLoading.show();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                URL url = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setReadTimeout(15000);
                conn.setConnectTimeout(10000);

                conn.setRequestMethod("GET");

                conn.setDoOutput(true);

                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK){

                    InputStream is = conn.getInputStream();
                    BufferedReader buffer =
                            new BufferedReader(
                                    new InputStreamReader(is));

                    StringBuilder result =  new StringBuilder();
                    String linha;

                    while((linha = buffer.readLine()) != null){
                        result.append(linha);
                    }

                    conn.disconnect();
                    return  result.toString();

                }

            }catch (MalformedURLException e){

            }catch (IOException ioe){

            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            //super.onPostExecute(s);

            if (s == null){
                Toast.makeText(SplashScreenActivity.this, "Erro", Toast.LENGTH_SHORT).show();

            }else {
                try {
                    JSONObject data = new JSONObject(s);
                    String usuario = data.getString("usuario");
                    String senha = data.getString("senha");

                    Login login = loginDao.getByUsuario(usuario);
                    if(login != null) {
                        login.setUsuario(usuario);
                        loginDao.update(login, login.getId());
                    }
                    else{
                        login = new Login();
                        login.setUsuario(usuario);
                        login.setSenha(senha);
                        loginDao.add(login);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            pdLoading.dismiss();
        }

    }
}