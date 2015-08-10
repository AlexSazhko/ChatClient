package com.alexsazhko.chatclient;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alexsazhko.chatclient.adapter.ContactListAdapter;
import com.alexsazhko.chatclient.entity.Contact;
import com.alexsazhko.chatclient.preference.PrefActivity;

import java.util.ArrayList;

public class ContactActivity extends AppCompatActivity {

    //final int REQUEST_CODE_ADD_CONVERS = 1;

    Context context;
    private String name;
    private ListView lvContactList;
    private ContactListAdapter contactAdapter;
    private ArrayList<Contact> contacts;
    private int contactPosition;


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
        contacts.add(new Contact(1, "John"));
        contacts.add(new Contact(2, "Mark"));
        contacts.add(new Contact(3, "Bob"));
    }


    private void initView() {
        getSupportActionBar().setTitle(name);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_contact);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        lvContactList = (ListView)findViewById(R.id.lv_contact_list);
        lvContactList.setOnItemClickListener(new ListListener());
    }

    private void initPreference() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        name = preferences.getString("name", "");
        //editor = preferences.edit();
    }

    private void initAdapter() {
        contactAdapter = new ContactListAdapter(contacts, context);
        lvContactList.setAdapter(contactAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            if (id == R.id.action_settings) {
                Intent intent = new Intent(this, PrefActivity.class);
                startActivity(intent);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class ListListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Contact contact = contacts.get(position);
            contactPosition = position;
            Intent intent = new Intent(ContactActivity.this, ChatRoomActivity.class);
            intent.putExtra("userName", name);
            intent.putExtra("contact", contact);
            startActivityForResult(intent, contactPosition);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            Contact contactRequest = (Contact)data.getExtras().getParcelable("contact");
            contacts.get(requestCode).setMessagesList(contactRequest.getMessagesList());
        }

    }
}
