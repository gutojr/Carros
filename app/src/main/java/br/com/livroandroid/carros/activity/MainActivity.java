package br.com.livroandroid.carros.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import java.util.List;

import br.com.livroandroid.carros.R;
import br.com.livroandroid.carros.activity.BaseActivity;
import br.com.livroandroid.carros.adapter.NavDrawerMenuAdapter;
import br.com.livroandroid.carros.adapter.NavDrawerMenuItem;
import br.com.livroandroid.carros.fragments.AboutDialog;
import br.com.livroandroid.carros.fragments.CarrosFragment;
import br.com.livroandroid.carros.fragments.CarrosTabFragment;
import br.com.livroandroid.carros.fragments.SiteLivroFragment;
import livroandroid.lib.fragment.NavigationDrawerFragment;

public class MainActivity extends BaseActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {
    private static final String TAG = "livroandroid";
    private NavigationDrawerFragment mNavDrawerFragment;
    private NavDrawerMenuAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpToolbar();
        // Navigation Drawer
        mNavDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_drawer_fragment);
        // Configura o drawer layout
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavDrawerFragment.setUp(drawerLayout);
        // Fab
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public NavigationDrawerFragment.NavDrawerListView getNavDrawerView(NavigationDrawerFragment navDrawerFrag,
                                                                       LayoutInflater layoutInflater, ViewGroup container) {
        // Deve retornar a view e o identificador do ListView
        View view = layoutInflater.inflate(R.layout.nav_drawer_listview, container, false);
        // Preenche o cabeçalho com a foto, nome e e-mail
        navDrawerFrag.setHeaderValues(view, R.id.listViewContainer, R.drawable.nav_drawer_header,
                R.drawable.ic_logo_user, R.string.nav_drawer_username, R.string.nav_drawer_email);
        return new NavigationDrawerFragment.NavDrawerListView(view, R.id.listView);
    }

    @Override
    public ListAdapter getNavDrawerListAdapter(NavigationDrawerFragment navDrawerFrag) {
        // Este método deve retornar o adapter que vai preencher o ListView
        List<NavDrawerMenuItem> list = NavDrawerMenuItem.getList();
        // Deixa o primeiro item selecionado
        list.get(0).selected = true;
        this.listAdapter = new NavDrawerMenuAdapter(this, list);
        return listAdapter;
    }

    @Override
    public void onNavDrawerItemSelected(NavigationDrawerFragment navDrawerFrag, int position) {
        // Método chamado ao selecionar um item do ListView
        List<NavDrawerMenuItem> list = NavDrawerMenuItem.getList();
        NavDrawerMenuItem selectedItem = list.get(position);
        // Selecione a linha
        this.listAdapter.setSelected(position, true);
        if (position == 0) {
            replaceFragment(new CarrosTabFragment());
        } else if (position == 1) {
            replaceFragment(new SiteLivroFragment());
        } else if (position == 2) {
            toast("Configurações");
        } else {
            Log.e(TAG, "Item de menu inválido");
        }
    }

    // Adiciona o fragment ao centro da tela
    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_drawer_container, fragment, "TAG").commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            // Mostra o dialog com as informações do aplicativo
            AboutDialog.showAbout(getSupportFragmentManager());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
