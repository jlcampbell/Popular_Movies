package com.campbell.jess.movies;

import android.os.Bundle;
import android.preference.PreferenceFragment;
//TODO finish settingsfragment class to complete settings UI
public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //load preferences
        addPreferencesFromResource(R.xml.preferences);
    }


}
