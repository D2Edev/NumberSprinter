package org.home.d2e.numbersprinter;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import org.home.d2e.numbersprinter.Core.DataRetainFragment;
import org.home.d2e.numbersprinter.Core.OnUIModeChangeListener;
import org.home.d2e.numbersprinter.Core.PrefKeys;


public class PrefFragment extends android.preference.PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    public static final String TAG = "TAG" + MainActivity.PREF_FRAGMENT_TAG;
    private DataRetainFragment dataRetainFragment;
    OnUIModeChangeListener onUIModeChangeListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_glb);

    }

    @Override
    public void onStart() {
        super.onStart();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        onUIModeChangeListener= (OnUIModeChangeListener) getActivity();
    }

    @Override
    public void onStop() {
        super.onStop();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        onUIModeChangeListener= null;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(PrefKeys.NIGHT_MODE)) {
            //boolean isNightMode = sharedPreferences.getBoolean(key, false);
            //if (isNightMode) {
            //    Toast.makeText(getActivity(), "NIGHT", Toast.LENGTH_SHORT).show();
            //} else {
            //    Toast.makeText(getActivity(), "DAY", Toast.LENGTH_SHORT).show();
            //}
            onUIModeChangeListener.setUIMode();
        }
    }
}
