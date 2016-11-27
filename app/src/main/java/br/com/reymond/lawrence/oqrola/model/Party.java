package br.com.reymond.lawrence.oqrola.model;

/**
 * Created by ian on 25-Nov-16.
 */

public class Party {
    private String nome;
    private String produtora;
    private int foto;
    private String local;


    public Party(){}

    public Party(String n, String p, int f, String l){
        nome = n;
        produtora = p;
        foto = f;
        local = l;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getProdutora() {
        return produtora;
    }

    public void setProdutora(String produtora) {
        this.produtora = produtora;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }
}