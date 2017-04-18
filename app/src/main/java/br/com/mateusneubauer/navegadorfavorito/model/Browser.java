package br.com.mateusneubauer.navegadorfavorito.model;

/**
 * Created by Mateus on 06/04/2017.
 */

public class Browser {

    private int id;
    private String nome;

    public Browser() {
    }
    public Browser(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }
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
    @Override
    public String toString() {
        return nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Browser browser = (Browser) o;

        if (id != browser.id) return false;
        return nome != null ? nome.equals(browser.nome) : browser.nome == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (nome != null ? nome.hashCode() : 0);
        return result;
    }
}
