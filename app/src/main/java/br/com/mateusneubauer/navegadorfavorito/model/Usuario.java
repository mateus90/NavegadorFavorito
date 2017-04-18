package br.com.mateusneubauer.navegadorfavorito.model;

/**
 * Created by Mateus on 06/04/2017.
 */

public class Usuario {

    private int id;
    private String nome;
    private Browser browser;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Browser getBrowser() {
        return browser;
    }

    public void setBrowser(Browser browser) {
        this.browser = browser;
    }

}
