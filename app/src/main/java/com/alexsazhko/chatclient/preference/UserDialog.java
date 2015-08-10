package com.alexsazhko.chatclient.preference;


import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.alexsazhko.chatclient.R;

public class UserDialog extends DialogFragment {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    EditText login;
    EditText password;
    Button button;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = preferences.edit();
        getDialog().setTitle(getActivity().getResources().getString(R.string.set_account_title));
        View v = inflater.inflate(R.layout.dialoglogin_fragment, null);
        v.findViewById(R.id.btnSet).setOnClickListener(new MyListener());
        login = (EditText) v.findViewById(R.id.etLogin);
        password = (EditText) v.findViewById(R.id.etPassword);
        return v;
    }

    private class MyListener implements android.view.View.OnClickListener{

        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.btnSet:
                    editor.putString("login", String.valueOf(login.getText()));
                    editor.putString("password", String.valueOf(password.getText()));
                    dismiss();
                    break;
            }

        }

    }
}
