package br.com.mateusneubauer.navegadorfavorito;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

import br.com.mateusneubauer.navegadorfavorito.dao.BrowserDAO;
import br.com.mateusneubauer.navegadorfavorito.dao.UsuarioDAO;
import br.com.mateusneubauer.navegadorfavorito.model.Browser;
import br.com.mateusneubauer.navegadorfavorito.model.Usuario;

public class NovoUsuarioActivity extends AppCompatActivity {

    public final static int CODE_NOVO_USUARIO = 1002;
    private TextInputLayout tilNomeUsuario;
    private Spinner spBrowser;
    private List<Browser> browsers;

    private EditText etNome;
    private Browser browser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_usuario);

        tilNomeUsuario = (TextInputLayout) findViewById(R.id.tilNomeUsuario);
        spBrowser = (Spinner) findViewById(R.id.spBrowser);

        etNome = (EditText) findViewById(R.id.etNome);

        carregarUsuario();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabVoltar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retornaParaTelaAnterior();
            }
        });

    }

    public void carregarUsuario() {
        BrowserDAO browserDAO = new BrowserDAO(this);
        browsers = browserDAO.getAll();
        ArrayAdapter<Browser> adapter =
                new ArrayAdapter<Browser>(getApplicationContext(),
                        R.layout.browser_spinner_item, browsers);
        adapter.setDropDownViewResource(R.layout.browser_spinner_item);
        spBrowser.setAdapter(adapter);

        final Bundle extras = getIntent().getExtras();
        Integer usuarioId = (extras != null) ? extras.getInt("usuarioId") : null;

        if (usuarioId != null) {
            UsuarioDAO usuarioDAO = new UsuarioDAO(this);
            Usuario usuario = usuarioDAO.getById(usuarioId);
            etNome.setText(usuario.getNome());
            spBrowser.setSelection(adapter.getPosition(usuario.getBrowser()));
        }
    }

    public void cadastrar(View v) {
        final Bundle extras = getIntent().getExtras();
        Integer usuarioId = (extras != null) ? extras.getInt("usuarioId") : null;

        UsuarioDAO usuarioDAO = new UsuarioDAO(this);
        Usuario usuario = new Usuario();
        usuario.setNome(tilNomeUsuario.getEditText().getText().toString());
        usuario.setBrowser((Browser) spBrowser.getSelectedItem());
        if (usuarioId == null) {
            usuarioDAO.add(usuario);
        } else {
            usuarioDAO.update(usuario, usuarioId);
        }
        retornaParaTelaAnterior();
    }

    public void retornaParaTelaAnterior() {
        Intent intentMessage = new Intent();
        setResult(CODE_NOVO_USUARIO, intentMessage);
        finish();
    }
}
