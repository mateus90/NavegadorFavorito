package br.com.mateusneubauer.navegadorfavorito;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Mateus on 17/04/2017.
 */

public class AboutActivity extends AppCompatActivity {

    public final static int CODE_ABOUT = 1002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabVoltar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retornaParaTelaAnterior();
            }
        });
    }

    private void retornaParaTelaAnterior() {
        Intent intentMessage = new Intent();
        setResult(CODE_ABOUT, intentMessage);
        finish();
    }
}
