package br.com.livroandroid.carros;

import android.app.Application;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gustavo on 27/02/2016.
 */
public class CarrosApplication extends Application {
    private static final String TAG = "CarrosApplication";
    private static CarrosApplication instance = null;
    private Map<String, Boolean> mapUpdate = new HashMap<String, Boolean>();

    public static CarrosApplication getInstance() {
        return instance; // Singleton
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "CarrosApplication.onCreate()");
        instance = this;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d(TAG, "CarrosApplication.onTerminate()");
    }

    public void setPrecisaAtualizar(String tipo, boolean b) {
        this.mapUpdate.put(tipo, b);
    }

    public boolean isPrecisaAtualizar(String tipo) {
        if(mapUpdate.containsKey(tipo)) {
            boolean b = mapUpdate.remove(tipo);
            return b;
        }
        return false;
    }
}
