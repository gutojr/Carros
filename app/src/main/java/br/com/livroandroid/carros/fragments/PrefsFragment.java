package br.com.livroandroid.carros.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import br.com.livroandroid.carros.R;

/**
 * Created by Gustavo on 02/03/2016.
 */
public class PrefsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
