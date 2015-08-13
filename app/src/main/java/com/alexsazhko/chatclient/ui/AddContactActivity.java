package com.alexsazhko.chatclient.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.alexsazhko.chatclient.R;
import com.alexsazhko.chatclient.ReceiveMessageCallBack;
import com.alexsazhko.chatclient.ServerConnection;
import com.alexsazhko.chatclient.adapter.AddContactListAdapter;
import com.alexsazhko.chatclient.adapter.MessageListAdapter;
import com.alexsazhko.chatclient.entity.ChatMessage;
import com.alexsazhko.chatclient.entity.Contact;
import com.alexsazhko.chatclient.entity.MessageState;

import java.util.ArrayList;
import java.util.List;

public class AddContactActivity extends AppCompatActivity implements ReceiveMessageCallBack{

    private EditText etInputSearchName;
    private ListView lvFindedList;
    private ArrayList<Contact> contacts;
    AddContactListAdapter adapter;
    private Handler handler;

    private ServerConnection serverConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        contacts = new ArrayList<Contact>();
        handler = new Handler();
        //initContactList();
        initView();

        serverConnection = ServerConnection.getInstance();
        serverConnection.setCallBack(this);

    }

    private void initView(){
        etInputSearchName = (EditText) findViewById(R.id.etInputSearchName);
        lvFindedList = (ListView) findViewById(R.id.lvFindedContactList);
        lvFindedList.setOnItemClickListener(new ListListener());
        adapter = new AddContactListAdapter(contacts);
        lvFindedList.setAdapter(adapter);
        Button btnSearch = (Button) findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new ButtonListener());

    }
    private void initContactList() {
        contacts = new ArrayList<Contact>();
        contacts.add(new Contact(1, "John"));
        contacts.add(new Contact(2, "Mark"));
        contacts.add(new Contact(3, "Bob"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_contact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class ButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id) {
                case R.id.btnSearch:
                    if (!String.valueOf(etInputSearchName.getText()).isEmpty()) {
                        hideKeyboard();
                        serverConnection.setMessageToSend(composeMessage(MessageState.SEARCH));
                    }
                    break;
            }
        }
    }

    private ChatMessage composeMessage(MessageState messageState){
        ChatMessage chatMsg = new ChatMessage();
        String messageContent = String.valueOf(etInputSearchName.getText());

        chatMsg.setSendTime(System.currentTimeMillis());
        if(!messageContent.equals(""))
            chatMsg.setMsgContent(messageContent);
        chatMsg.setMessageFlag(messageState.name());
        chatMsg.setOwnMessage(true);

        return chatMsg;
    }

    private class ListListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Contact contact = contacts.get(position);
            Intent resultIntent = new Intent();
            resultIntent.putExtra("contact", contact);
            setResult(RESULT_OK, resultIntent);
            //serverConnection.setMessageToSend(composeMessage(MessageState.END));
            finish();
        }
    }


    @Override
    public void receiveMessage(final ChatMessage message) {
        handler.post(new Runnable() {

            @Override
            public void run() {
                //Log.i("DEBUG:", "message in handler: " + msg.getMsgContent());
                //synchronized (this){

                String name = message.getMsgContent();
                if(!name.equals("")){
                    Log.i("DEBUG:", "find name: " + name);
                    Contact contact = new Contact();
                    contact.setName(name);
                    contacts.add(contact);

                    adapter.notifyDataSetChanged();
                }
                //}

                //Log.d("","hi");
            }
        });


    }

    private void hideKeyboard() {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
