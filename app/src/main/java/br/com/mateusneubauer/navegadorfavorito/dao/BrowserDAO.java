package br.com.mateusneubauer.navegadorfavorito.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;
import java.util.List;

import br.com.mateusneubauer.navegadorfavorito.model.Browser;

/**
 * Created by Mateus on 06/04/2017.
 */

public class BrowserDAO {

    private DBOpenHelper banco;

    public BrowserDAO(Context context) {
        banco = new DBOpenHelper(context);
    }
    public static final String TABELA_BROWSERS = "browser";
    public static final String COLUNA_ID = "id";
    public static final String COLUNA_NOME = "nome";
    public List<Browser> getAll() {
        List<Browser> browsers = new LinkedList<>();
        String query = "SELECT * FROM " + TABELA_BROWSERS;
        SQLiteDatabase db = banco.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Browser browser = null;

        if (cursor.moveToFirst()) {
            do {
                browser = new Browser();
                browser.setId(cursor.getInt(cursor.getColumnIndex(COLUNA_ID)));

                browser.setNome(cursor.getString(cursor.getColumnIndex(COLUNA_NOME)));
                browsers.add(browser);
            } while (cursor.moveToNext());
        }
        return browsers;
    }
    public Browser getBy(int id) {
        SQLiteDatabase db = banco.getReadableDatabase();
        String colunas[] = { COLUNA_ID, COLUNA_NOME};
        String where = "id = " + id;
        Cursor cursor = db.query(true, TABELA_BROWSERS, colunas, where, null, null,
                null, null, null);
        Browser browser = null;
        if(cursor != null)
        {
            cursor.moveToFirst();
            browser = new Browser();
            browser.setNome(cursor.getString(cursor.getColumnIndex(COLUNA_NOME)));
            browser.setId(cursor.getInt(cursor.getColumnIndex(COLUNA_ID)));
        }
        return browser;
    }
}
