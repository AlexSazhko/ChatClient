package com.alexsazhko.chatclient.preference;

import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.alexsazhko.chatclient.R;

public class PrefActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_pref);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            //addPreferencesFromResource(R.xml.pref);
        } else {
            getFragmentManager().beginTransaction()
                    .replace(android.R.id.content, new FragmentPreference()).commit();
        }


    }
}
