package br.com.livroandroid.carros;

import android.app.Application;
import android.util.Log;

/**
 * Created by Gustavo on 27/02/2016.
 */
public class CarrosApplication extends Application {
    private static final String TAG = "CarrosApplication";
    private static CarrosApplication instance = null;

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
}
