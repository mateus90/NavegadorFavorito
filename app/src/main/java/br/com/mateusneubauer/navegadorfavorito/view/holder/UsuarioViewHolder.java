package br.com.mateusneubauer.navegadorfavorito.view.holder;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import br.com.mateusneubauer.navegadorfavorito.NovoUsuarioActivity;
import br.com.mateusneubauer.navegadorfavorito.R;
import br.com.mateusneubauer.navegadorfavorito.dao.UsuarioDAO;
import br.com.mateusneubauer.navegadorfavorito.model.Usuario;
import br.com.mateusneubauer.navegadorfavorito.view.adapter.UsuarioAdapter;

/**
 * Created by Mateus on 07/04/2017.
 */

public class UsuarioViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public final TextView nome;
    public final TextView nomeBrowser;
    private int usuarioId;
    public final UsuarioAdapter adapter;


    public UsuarioViewHolder(final View view, final UsuarioAdapter adapter) {

        super(view);
        this.adapter = adapter;
        view.setOnClickListener(this);

        nome = (TextView) view.findViewById(R.id.tvNome);
        nomeBrowser = (TextView) view.findViewById(R.id.tvNomeBrowser);
    }

    public void preencher(Usuario usuario) {
        usuarioId = usuario.getId();
        nome.setText(usuario.getNome());
        nomeBrowser.setText(usuario.getBrowser().getNome());
    }

    @Override
    public void onClick(View v) {

        PopupMenu popup = new PopupMenu(v.getContext(), v);
        popup.getMenuInflater().inflate(R.menu.menu_usuario, popup.getMenu());

        final Activity context = (Activity) v.getContext();

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.menuEditar:

                        final Intent intent = new Intent(context, NovoUsuarioActivity.class);
                        intent.putExtra("usuarioId", usuarioId);
                        context.startActivityForResult(intent, NovoUsuarioActivity.CODE_NOVO_USUARIO);

                        break;

                    case R.id.menuDeletar:
                        UsuarioDAO usuarioDAO = new UsuarioDAO(context);
                        usuarioDAO.remover(usuarioId);
                        deletarUsuario();
                        break;

                }

                return true;
            }
        });

        popup.show();
    }

    public void deletarUsuario() {
        adapter.remove(getAdapterPosition());
    }

}
