package com.alexsazhko.chatclient;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

public class ContactActivity extends AppCompatActivity {

    Context context;
    private String name;
    private ListView contactList;
    private ContactAdapter contactAdapter;
    private ArrayList<Contact> contacts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        context = getApplicationContext();
        initContactList();
        initPreference();
        initView();
        initAdapter();
    }

    private void initContactList() {
        contacts = new ArrayList<Contact>();
        contacts.add(new Contact("John"));
        contacts.add(new Contact("Mark"));
        contacts.add(new Contact("Bob"));
    }


    private void initView() {
        getSupportActionBar().setTitle(name);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_contact);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        contactList = (ListView)findViewById(R.id.lv_contact_list);
    }

    private void initPreference() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        name = preferences.getString("name", "");
        //editor = preferences.edit();
    }

    private void initAdapter() {
        contactAdapter = new ContactAdapter(contacts, context);
        contactList.setAdapter(contactAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
