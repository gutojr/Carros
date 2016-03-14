package br.com.livroandroid.carros.fragments;

import android.app.backup.BackupManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.livroandroid.carros.R;
import br.com.livroandroid.carros.adapter.TabsAdapter;
import livroandroid.lib.utils.Prefs;

/**
 * Created by Gustavo on 28/02/2016.
 */
public class CarrosTabFragment extends BaseFragment implements TabLayout.OnTabSelectedListener {
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private BackupManager backupManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carros_tab, container, false);
        backupManager = new BackupManager(getContext());
        // ViewPager
        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
        // Faz com que o ViewPager matenha na memória duas tabs a mais do que o que ele esta visualizando
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(new TabsAdapter(getContext(), getChildFragmentManager()));
        // Tabs
        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        int cor = ContextCompat.getColor(getContext(), R.color.white);
        // Cor branca no texto (o fundo azul foi definido no layout)
        tabLayout.setTabTextColors(cor, cor);
        // Adiciona as tabs
        tabLayout.addTab(tabLayout.newTab().setText(R.string.classicos));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.esportivos));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.luxo));
        // Listener para tratar eventos de clique na tab
        tabLayout.setOnTabSelectedListener(this);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Prefs.setInteger(getContext(), "tabIdx", mViewPager.getCurrentItem());
                backupManager.dataChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        // Se mudar o ViewPager atualiza a tab selecionada
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        // Ao criar a view, mostra a última tab selecionada
        int tabIdx = Prefs.getInteger(getContext(), "tabIdx");
        mViewPager.setCurrentItem(tabIdx);
        return view;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        // Se alterar a tab, atualiza o ViewPager
        mViewPager.setCurrentItem(tab.getPosition());
        // Salva o índice da página/tab selecionada
        Prefs.setInteger(getContext(), "tabIdx", mViewPager.getCurrentItem());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {}

    @Override
    public void onTabReselected(TabLayout.Tab tab) {}

}
