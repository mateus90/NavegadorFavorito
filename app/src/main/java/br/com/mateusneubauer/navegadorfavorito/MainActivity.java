package br.com.mateusneubauer.navegadorfavorito;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import br.com.mateusneubauer.navegadorfavorito.dao.UsuarioDAO;
import br.com.mateusneubauer.navegadorfavorito.model.Usuario;
import br.com.mateusneubauer.navegadorfavorito.view.adapter.UsuarioAdapter;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private RecyclerView rvLista;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rvLista = (RecyclerView) findViewById(R.id.rvLista);

        carregarUsuarios();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irParaTelaCadastro();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_CANCELED) {
            Toast.makeText(MainActivity.this, "Cancelado",
                    Toast.LENGTH_LONG).show();
        } else if(requestCode == NovoUsuarioActivity.CODE_NOVO_USUARIO) {
            carregarUsuarios();
        }
    }

    private void irParaTelaCadastro()
    {
        startActivityForResult(new Intent(MainActivity.this,
                        NovoUsuarioActivity.class),
                NovoUsuarioActivity.CODE_NOVO_USUARIO);
    }

    private void carregarUsuarios() {
        UsuarioDAO usuarioDAO = new UsuarioDAO(this);
        List<Usuario> listaUsuario = usuarioDAO.getAll();

        rvLista.setAdapter(new UsuarioAdapter(listaUsuario, this));

        RecyclerView.LayoutManager layout = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);

        rvLista.setLayoutManager(layout);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_cadastrar:
                irParaTelaCadastro();
                break;
            case R.id.nav_listar:
                break;
            case R.id.nav_sobre:
                irParaTelaSobre();
                break;
            case R.id.nav_sair:
                sair();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void irParaTelaSobre()
    {
        startActivityForResult(new Intent(MainActivity.this,
                        AboutActivity.class),
                AboutActivity.CODE_ABOUT);
    }

    private void sair() {

        SharedPreferences pref = getSharedPreferences(LoginActivity.KEY_APP_PREFERENCES,
                LoginActivity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(LoginActivity.KEY_LOGIN, "");
        editor.apply();

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
