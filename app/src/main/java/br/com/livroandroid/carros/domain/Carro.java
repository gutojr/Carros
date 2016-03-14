package br.com.livroandroid.carros.domain;

import java.io.Serializable;

/**
 * Created by Gustavo on 28/02/2016.
 */
public class Carro implements Serializable {
    private static final long serialVersionUID = 6601006766832473959L;
    public long id;
    public String tipo;
    public String nome;
    public String desc;
    public String urlFoto;
    public String urlInfo;
    public String urlVideo;
    public String latitude;
    public String longitude;
    public boolean selected;

    @Override
    public String toString() {
        return "Carro{" + "nome='" + nome + '\'' + ", desc='" + desc + '\'' + '}';
    }
}
