package br.com.mateusneubauer.navegadorfavorito.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;
import java.util.List;

import br.com.mateusneubauer.navegadorfavorito.model.Browser;
import br.com.mateusneubauer.navegadorfavorito.model.Usuario;

/**
 * Created by Mateus on 06/04/2017.
 */

public class UsuarioDAO {

    private SQLiteDatabase db;
    private DBOpenHelper banco;

    public UsuarioDAO(Context context) {
        banco = new DBOpenHelper(context);
    }

    private static final String TABELA_USUARIO = "usuario";
    private static final String COLUNA_ID = "id";
    private static final String COLUNA_NOME = "nome";
    private static final String COLUNA_BROWSER_ID = "browser_id";

    //private static final String[] COLUMNS = {COLUNA_ID, COLUNA_NOME,COLUNA_CLUBE_ID};
    public String add(Usuario usuario) {
        long resultado;
        SQLiteDatabase db = banco.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUNA_NOME, usuario.getNome());
        values.put(COLUNA_BROWSER_ID, usuario.getBrowser().getId());
        resultado = db.insert(TABELA_USUARIO,
                null,
                values);
        db.close();
        if (resultado == -1) {
            return "Erro ao inserir registro";
        } else {
            return "Registro inserido com sucesso";
        }
    }

    public String update(Usuario usuario, Integer id) {

        long resultado;
        SQLiteDatabase db = banco.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUNA_NOME, usuario.getNome());
        values.put(COLUNA_BROWSER_ID, usuario.getBrowser().getId());
        resultado = db.update(UsuarioDAO.TABELA_USUARIO, values, UsuarioDAO.COLUNA_ID + "=" + id, null);

        if (resultado == -1) {
            return "Erro ao inserir registro";
        } else {
            return "Registro inserido com sucesso";
        }
    }

    public List<Usuario> getAll() {
        List<Usuario> usuarios = new LinkedList<>();
        String rawQuery = "SELECT u.*, b.nome FROM " +
                UsuarioDAO.TABELA_USUARIO + " u INNER JOIN " +
                BrowserDAO.TABELA_BROWSERS
                + " b ON u." + UsuarioDAO.COLUNA_BROWSER_ID + " = b." +
                BrowserDAO.COLUNA_ID +
                " ORDER BY " + UsuarioDAO.COLUNA_NOME + " ASC";
        SQLiteDatabase db = banco.getReadableDatabase();
        Cursor cursor = db.rawQuery(rawQuery, null);
        Usuario usuario = null;
        if (cursor.moveToFirst()) {
            do {
                usuario = new Usuario();
                usuario.setId(cursor.getInt(0));
                usuario.setNome(cursor.getString(2));
                usuario.setBrowser(new Browser(cursor.getInt(1),
                        cursor.getString(3)));
                usuarios.add(usuario);
            } while (cursor.moveToNext());
        }
        return usuarios;
    }

    public Usuario getById(Integer id) {
        SQLiteDatabase db = banco.getWritableDatabase();
        String rawQuery = "SELECT u.*, b.nome FROM " +
                UsuarioDAO.TABELA_USUARIO + " u INNER JOIN " +
                BrowserDAO.TABELA_BROWSERS
                + " b ON u." + UsuarioDAO.COLUNA_BROWSER_ID + " = b." +
                BrowserDAO.COLUNA_ID + " WHERE u.id = ?";
        Cursor cursor = db.rawQuery(rawQuery, new String[]{String.valueOf(id)});
        cursor.moveToFirst();
        Usuario usuario = new Usuario();
        usuario.setId(cursor.getInt(0));
        usuario.setNome(cursor.getString(2));
        usuario.setBrowser(new Browser(cursor.getInt(1),
                cursor.getString(3)));
        db.close();
        return usuario;
    }

    public void remover(int id) {
        String where = COLUNA_ID + "=" + id;
        SQLiteDatabase db = banco.getWritableDatabase();
        db.delete(UsuarioDAO.TABELA_USUARIO, where, null);
    }
}
