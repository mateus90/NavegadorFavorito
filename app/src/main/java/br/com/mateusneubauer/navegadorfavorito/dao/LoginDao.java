package br.com.mateusneubauer.navegadorfavorito.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import br.com.mateusneubauer.navegadorfavorito.model.Browser;
import br.com.mateusneubauer.navegadorfavorito.model.Login;
import br.com.mateusneubauer.navegadorfavorito.model.Usuario;

/**
 * Created by Mateus on 14/04/2017.
 */

public class LoginDao {

    private SQLiteDatabase db;
    private DBOpenHelper banco;

    public LoginDao(Context context) {
        banco = new DBOpenHelper(context);
    }

    private static final String TABELA_LOGIN = "login";
    private static final String COLUNA_ID = "id";
    private static final String COLUNA_USUARIO = "usuario";
    private static final String COLUNA_SENHA= "senha";

    public String add(Login login) {
        long resultado;
        SQLiteDatabase db = banco.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUNA_USUARIO, login.getUsuario());
        values.put(COLUNA_SENHA, login.getSenha());
        resultado = db.insert(TABELA_LOGIN,
                null,
                values);
        db.close();
        if (resultado == -1) {
            return "Erro ao inserir registro";
        } else {
            return "Registro inserido com sucesso";
        }
    }

    public String update(Login login, Integer id) {

        long resultado;
        SQLiteDatabase db = banco.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUNA_USUARIO, login.getUsuario());
        values.put(COLUNA_SENHA, login.getSenha());
        resultado = db.update(LoginDao.TABELA_LOGIN, values, LoginDao.COLUNA_ID + "=" + id, null);

        if (resultado == -1) {
            return "Erro ao atualizar registro";
        } else {
            return "Registro atualizado com sucesso";
        }
    }

    public Login getByUsuario(String usuario, String senha) {
        SQLiteDatabase db = banco.getWritableDatabase();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT l.* FROM " +
                LoginDao.TABELA_LOGIN + " l WHERE l.usuario = ?");
        if(senha != null)
            sql.append("and l.senha = ?");

        String[] params = senha != null ?
                new String[]{String.valueOf(usuario), String.valueOf(senha)} : new String[]{String.valueOf(usuario)};

        Cursor cursor = db.rawQuery(sql.toString(), params);
        if(cursor.moveToFirst()) {
            Login login = new Login();
            login.setId(cursor.getInt(0));
            login.setUsuario(cursor.getString(1));
            login.setSenha(cursor.getString(2));
            db.close();
            return login;
        }
        else
            return null;
    }

    public Login getByUsuario(String usuario)
    {
        return getByUsuario(usuario, null);
    }
}
