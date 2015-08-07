package com.alexsazhko.chatclient;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.alexsazhko.chatclient.adapter.MessageListAdapter;
import com.alexsazhko.chatclient.entity.ChatMessage;
import com.alexsazhko.chatclient.entity.Contact;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomActivity extends AppCompatActivity {

    Context context;
    private ListView lvMessageList;
    private EditText etInputMessage;
    private Button btnSend;

    private String name;
    private String toUserName;

    private List<ChatMessage> messagesItems;
    private Contact contact;
    private MessageListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        context = getApplicationContext();
        messagesItems = new ArrayList<ChatMessage>();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            name = extras.getString("userName");
            contact = extras.getParcelable("contact");
            //contact = (Contact)extras.getSerializable("contact");
           // messagesItems = contact.getMessagesList();
           // Log.i("DEBUG:", "size" + messagesItems.size());
        }
        initView();

    }

    private void initView() {
        getSupportActionBar().setTitle(contact.getName());
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_contact);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        etInputMessage = (EditText) findViewById(R.id.etInputMsg);
        btnSend = (Button) findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new ButtonListener());
        lvMessageList = (ListView)findViewById(R.id.lvMessageList);
        adapter = new MessageListAdapter(messagesItems, context);
        lvMessageList.setAdapter(adapter);
    }

    protected void onStart() {
        super.onStart();
        if (!contact.getMessagesList().isEmpty()) {
            messagesItems.addAll(contact.getMessagesList());
            contact.getMessagesList().clear();
            refreshAdapter();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chat_room, menu);
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

    private void refreshAdapter(){

        adapter.notifyDataSetChanged();
    }

    private class ButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id) {
                case R.id.btnSend:
                    if(!String.valueOf(etInputMessage.getText()).isEmpty()) {
                        messagesItems.add(composeMessage("MESSAGE"));
                        etInputMessage.setText("");
                        hideKeyboard();
                        refreshAdapter();
                    }
                    break;
            }
        }
    }

    private ChatMessage composeMessage(String flagMessage){
        ChatMessage chatMsg = new ChatMessage();
        chatMsg.setSendTime(System.currentTimeMillis());
        chatMsg.setUserName(name);
        chatMsg.setMsgContent(String.valueOf(etInputMessage.getText()));
        chatMsg.setOwnMessage(true);
        return chatMsg;
    }

    private void hideKeyboard() {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onPause() {
        super.onPause();


    }

    @Override
    public void onBackPressed() {
        Log.i("DEBUG:", "size" + messagesItems.size());
        contact.setMessagesList(messagesItems);
        Intent resultIntent = new Intent();
        resultIntent.putExtra("contact", contact);
        setResult(RESULT_OK, resultIntent);
        this.finish();
        super.onBackPressed();
    }

}
