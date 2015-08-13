package com.alexsazhko.chatclient.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alexsazhko.chatclient.R;
import com.alexsazhko.chatclient.ReceiveMessageCallBack;
import com.alexsazhko.chatclient.ServerConnection;
import com.alexsazhko.chatclient.adapter.ContactListAdapter;
import com.alexsazhko.chatclient.entity.ChatMessage;
import com.alexsazhko.chatclient.entity.Contact;
import com.alexsazhko.chatclient.entity.MessageState;
import com.alexsazhko.chatclient.preference.PrefActivity;
import com.alexsazhko.chatclient.utils.NetworkUtils;
import com.alexsazhko.chatclient.utils.Utils;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ContactActivity extends AppCompatActivity implements ReceiveMessageCallBack {

    //final int REQUEST_CODE_ADD_CONVERS = 1;
    final int REQUEST_CODE_CHAT = 1;
    final int REQUEST_CODE_ADD_CONTACT = 2;

    Context context;
    private String name;
    private ListView lvContactList;
    private ArrayList<Contact> contacts;
    private int contactPosition;
    ContactListAdapter contactAdapter;

    private ExecutorService threadPool;
    private ServerConnection serverConnection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        context = getApplicationContext();
        initContactList();
        initPreference();
        initView();
        initAdapter();
        initServerConnection();

        ChatMessage chatMessage;
        chatMessage = composeMessage(MessageState.NEW);
        serverConnection.setMessageToSend(chatMessage);
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

    private void refreshAdapter(){

        contactAdapter.notifyDataSetChanged();
    }

    private void initServerConnection(){
        serverConnection = ServerConnection.getInstance();
        serverConnection.setCallBack(this);
        threadPool = Executors.newSingleThreadExecutor();
        threadPool.submit(serverConnection);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

            if (id == R.id.action_settings) {
                Intent intent = new Intent(this, PrefActivity.class);
                startActivity(intent);
                return true;
            }

            if (id == R.id.action_add_contact) {
                Intent intent = new Intent(this, AddContactActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_CONTACT);
                return true;
            }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void receiveMessage(ChatMessage message) {

    }

    private class ListListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //if(NetworkUtils.isOnline() | (NetworkUtils.isWiFiEnabled() && NetworkUtils.isWifiConnect()) ) {
                Contact contact = contacts.get(position);
                contactPosition = position;
                Intent intent = new Intent(ContactActivity.this, ChatRoomActivity.class);
                intent.putExtra("userName", name);
                intent.putExtra("contact", contact);
                startActivityForResult(intent, REQUEST_CODE_CHAT);
            /*}else{
                Utils.showShortToast(R.string.message_no_network);
            }*/
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            switch (requestCode){
                case REQUEST_CODE_CHAT:
                    Contact contactRequest = (Contact)data.getExtras().getParcelable("contact");
                    contacts.get(requestCode).setMessagesList(contactRequest.getMessagesList());
                    break;
                case REQUEST_CODE_ADD_CONTACT:
                    Contact addedContact = (Contact)data.getExtras().getParcelable("contact");
                    contacts.add(addedContact);
                    refreshAdapter();
                    break;
            }

        }

    }

    private ChatMessage composeMessage(MessageState messageState){
        ChatMessage chatMsg = new ChatMessage();;

        chatMsg.setSendTime(System.currentTimeMillis());
        chatMsg.setUserName(name);
        chatMsg.setMessageFlag(messageState.name());
        chatMsg.setOwnMessage(true);

        return chatMsg;
    }

    @Override
    public void onBackPressed() {
        serverConnection.setMessageToSend(composeMessage(MessageState.END));
        this.finish();
        super.onBackPressed();
    }
}
