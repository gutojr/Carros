package br.com.livroandroid.carros.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import br.com.livroandroid.carros.R;
import br.com.livroandroid.carros.domain.Carro;

/**
 * Created by Gustavo on 28/02/2016.
 */
public class CarroFragment extends BaseFragment {
    private Carro carro;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carro, container, false);
        return view;
    }

    // Método público chamado pela activity para atualizar a lista de carros
    public void setCarro(Carro carro) {
        if (carro != null) {
            this.carro = carro;
            setTextString(R.id.tDesc, carro.desc);
            final ImageView imageView = (ImageView) getView().findViewById(R.id.img);
            Picasso.with(getContext()).load(carro.urlFoto).fit().into(imageView);
        }
    }
}
