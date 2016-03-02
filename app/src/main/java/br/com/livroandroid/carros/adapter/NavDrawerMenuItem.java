package br.com.livroandroid.carros.adapter;

import java.util.ArrayList;
import java.util.List;

import br.com.livroandroid.carros.R;

/**
 * Created by Gustavo on 27/02/2016.
 */
public class NavDrawerMenuItem {
    public int title; // Título: R.string.xxx
    public int img;   // Figura: R.drawable.yyy
    public boolean selected; // Para colocar um fundo cinza quando a linha esta selecionada

    public NavDrawerMenuItem(int title, int img) {
        this.title = title;
        this.img = img;
    }

    // Cria a lista com os itens de menu
    public static List<NavDrawerMenuItem> getList() {
        List<NavDrawerMenuItem> list = new ArrayList<NavDrawerMenuItem>();
        list.add(new NavDrawerMenuItem(R.string.carros, R.drawable.ic_drawer_carro));
        list.add(new NavDrawerMenuItem(R.string.site_livro, R.drawable.ic_drawer_site_livro));
        list.add(new NavDrawerMenuItem(R.string.configuracoes, R.drawable.ic_drawer_settings));
        return list;
    }
}
