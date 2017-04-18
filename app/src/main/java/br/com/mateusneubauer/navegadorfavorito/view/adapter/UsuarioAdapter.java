package br.com.mateusneubauer.navegadorfavorito.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.mateusneubauer.navegadorfavorito.R;
import br.com.mateusneubauer.navegadorfavorito.model.Usuario;
import br.com.mateusneubauer.navegadorfavorito.view.holder.UsuarioViewHolder;

/**
 * Created by Mateus on 07/04/2017.
 */

public class UsuarioAdapter extends RecyclerView.Adapter  {

    private List<Usuario> usuarios;
    private Context context;

    public UsuarioAdapter(List<Usuario> usuarios, Context context) {
        this.usuarios = usuarios;
        this.context = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.usuario_item_lista, parent, false);
        UsuarioViewHolder holder = new UsuarioViewHolder(view, this);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        UsuarioViewHolder viewHolder = (UsuarioViewHolder) holder;

        Usuario usuario  = usuarios.get(position);

        ((UsuarioViewHolder) holder).preencher(usuario);


    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    public void remove(int position) {
        usuarios.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }
}
