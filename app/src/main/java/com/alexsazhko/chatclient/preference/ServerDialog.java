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

public class ServerDialog extends DialogFragment {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    EditText ip;
    EditText port;
    Button button;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = preferences.edit();
        getDialog().setTitle(getActivity().getResources().getString(R.string.set_chat_server));
        View v = inflater.inflate(R.layout.dialogipport_fragment, null);
        v.findViewById(R.id.btnSet).setOnClickListener(new MyListener());
        ip = (EditText) v.findViewById(R.id.etAdress);
        port = (EditText) v.findViewById(R.id.etPort);
        return v;
    }

    private class MyListener implements android.view.View.OnClickListener{

        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.btnSet:
                    editor.putString("adress", String.valueOf(ip.getText()));
                    editor.putString("port", String.valueOf(port.getText()));
                    dismiss();
                    break;
            }

        }

    }
}
