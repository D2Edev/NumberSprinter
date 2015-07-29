package org.home.d2e.numbersprinter;


import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import org.home.d2e.numbersprinter.Core.DataRetainFragment;
import org.home.d2e.numbersprinter.Core.PrefKeys;


public class PrefFragment extends android.preference.PreferenceFragment {
    public static final String TAG="TAG"+MainActivity.PREF_FRAGMENT_TAG;
    private DataRetainFragment dataRetainFragment;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_glb);

    }

}
