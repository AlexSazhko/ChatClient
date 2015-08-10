package com.alexsazhko.chatclient.preference;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;

import com.alexsazhko.chatclient.R;

public class FragmentPreference extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

    private PreferenceClickListener preferenceClickListener = new PreferenceClickListener();
    private UserDialog userDialog;
    private ServerDialog serverDialog;
    private SharedPreferences preferences;
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getActivity();
        userDialog = new UserDialog();
        serverDialog = new ServerDialog();
       // getPreferenceValue();
        setPreferenceScreen(createPreferences());
    }

    //1.Create PreferenceScreen
    private PreferenceScreen createPreferences() {
        PreferenceScreen root = getPreferenceManager().createPreferenceScreen(getActivity());
        addCategory(root);
        return root;
    }

    //2.Add PreferenceCategory
    private PreferenceCategory addCategory(PreferenceScreen root) {
        PreferenceCategory rootPreferenceCategoryNetwork = new PreferenceCategory(getActivity());
        PreferenceCategory rootPreferenceCategoryAccount = new PreferenceCategory(getActivity());
        rootPreferenceCategoryNetwork.setTitle(R.string.pref_category_title_network);
        rootPreferenceCategoryAccount.setTitle(R.string.pref_category_title_account);
        root.addPreference(rootPreferenceCategoryNetwork);
        root.addPreference(rootPreferenceCategoryAccount);
        addListNetworkPreference(rootPreferenceCategoryNetwork);
        addListAccountPreference(rootPreferenceCategoryAccount);

        return rootPreferenceCategoryNetwork;
    }

    private void addListNetworkPreference(PreferenceCategory rootPreferenceCategory){
        rootPreferenceCategory.addPreference(createNetworkPreference());
    }

    private void addListAccountPreference(PreferenceCategory rootPreferenceCategory){
        rootPreferenceCategory.addPreference(createAccountPreference());
    }

    private Preference createNetworkPreference() {
        Preference preference = new Preference(getActivity());
        preference.setTitle(R.string.pref_category_title_network);
        preference.setOnPreferenceClickListener(preferenceClickListener);

        preference.setKey(getResources().getString(R.string.chat_server_key));

        return preference;
    }

    private Preference createAccountPreference() {
        Preference preference = new Preference(getActivity());
        preference.setTitle(R.string.pref_category_title_account);
        preference.setOnPreferenceClickListener(preferenceClickListener);

        preference.setKey(getResources().getString(R.string.account_key));

        return preference;
    }

    private class PreferenceClickListener implements Preference.OnPreferenceClickListener {

        @Override
        public boolean onPreferenceClick(Preference preference) {
            String key = preference.getKey();
            if (key.equals(getResources().getString(R.string.chat_server_key))) {

                serverDialog.show(getFragmentManager(), "serverDialog");

                return true;
            }
            if (key.equals(getResources().getString(R.string.account_key))) {

                userDialog.show(getFragmentManager(), "userDialog");

                return true;
            }

            return false;
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        return false;
    }
}
