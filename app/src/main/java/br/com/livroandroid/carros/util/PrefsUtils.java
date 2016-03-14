package br.com.livroandroid.carros.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Gustavo on 02/03/2016.
 */
public class PrefsUtils {
    private static final String PREF_CHECK_PUSH = "PREF_CHECK_PUSH";

    // Verifica se o usuário marcou o checkbox de Push ON nas configurações
    public static boolean isCheckPushOn(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(PREF_CHECK_PUSH, false);
    }
}
