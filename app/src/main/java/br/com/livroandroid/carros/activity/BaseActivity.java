package br.com.livroandroid.carros.activity;




import android.support.v7.widget.Toolbar;

import br.com.livroandroid.carros.R;

/**
 * Created by Gustavo on 26/02/2016.
 */
public class BaseActivity extends livroandroid.lib.activity.BaseActivity {
    protected void setUpToolbar() {
        android.support.v7.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }
}
