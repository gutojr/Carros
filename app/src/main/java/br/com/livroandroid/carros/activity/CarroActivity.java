package br.com.livroandroid.carros.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import br.com.livroandroid.carros.R;
import br.com.livroandroid.carros.domain.Carro;
import br.com.livroandroid.carros.fragments.CarroFragment;
import br.com.livroandroid.carros.fragments.CarrosFragment;

public class CarroActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carro);
        // Configura a Toolbar como a action bar
        setUpToolbar();
        // Atualiza o carro no fragment
        CarroFragment cf = (CarroFragment) getSupportFragmentManager().findFragmentById(R.id.CarroFragment);
        Carro c = (Carro) getIntent().getSerializableExtra("carro");
        cf.setCarro(c);
        // Título da toolbar e botão up navigation
        getSupportActionBar().setTitle(c.nome);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /* Troca da animação de saída do back button
    @Override
    public void finish() {
        super.finish();
        // Customiza a animação ao fechar a activity
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }*/

    /* Troca da animação de saída do up navigation
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch(itemId){
            case android.R.id.home:
                super.onOptionsItemSelected(item);
                this.finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            default:
                break;
        }
        return true;
    }*/
}
