package com.alexsazhko.chatclient;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatRoomActivity extends AppCompatActivity implements ReceiveMessageCallBack{

    Context context;
    private EditText etInputMessage;

    private String name;
    private String toUserName;

    private List<ChatMessage> messagesItems;
    private Contact contact;
    private MessageListAdapter adapter;
    ListView lvMessageList;

    private ExecutorService threadPool;
    private ServerConnection serverConnection;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        context = getApplicationContext();
        handler = new Handler();

        messagesItems = new ArrayList<ChatMessage>();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            name = extras.getString("userName");
            contact = extras.getParcelable("contact");
        }

        initView();
        initServerConnection();
    }

    private void initView() {
        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle(contact.getName());
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_contact);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        etInputMessage = (EditText) findViewById(R.id.etInputMsg);
        Button btnSend = (Button) findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new ButtonListener());
         lvMessageList = (ListView)findViewById(R.id.lvMessageList);
        adapter = new MessageListAdapter(messagesItems, context);
        lvMessageList.setAdapter(adapter);
    }

    private void initServerConnection() {
        serverConnection = new ServerConnection(context);
        serverConnection.setCallBack(this);
        threadPool = Executors.newSingleThreadExecutor();
        threadPool.submit(serverConnection);
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
        adapter = new MessageListAdapter(messagesItems, context);
        lvMessageList.setAdapter(adapter);
        //adapter.notifyDataSetChanged();
        int i = 0;
        for(ChatMessage msg: messagesItems){
            Log.i("DEBUG:", "message array " + String.valueOf(i++) + msg.isOwnMessage());
        }
    }

    private class ButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id) {
                case R.id.btnSend:
                    if(!String.valueOf(etInputMessage.getText()).isEmpty()) {
                        ChatMessage chatMessage = new ChatMessage();
                        chatMessage = composeMessage("MESSAGE");
                        synchronized(this){
                            messagesItems.add(chatMessage);
                            //chatMessage.setOwnMessage(false);
                            serverConnection.setMessageToSend(chatMessage);
                            //chatMessage.setOwnMessage(true);
                            refreshAdapter();
                        }
                        etInputMessage.setText("");

                        hideKeyboard();
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
        chatMsg.setMessageFlag(flagMessage);
        chatMsg.setOwnMessage(true);
        Log.i("DEBUG:", "message i compose " +  chatMsg.isOwnMessage());
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

    public void receiveMessage(final ChatMessage msg)
    {
        //final String msg = msg ;
        handler.post(new Runnable() {

            @Override
            public void run() {

                synchronized (this){
                    messagesItems.add(msg);
                    refreshAdapter();
                }

                //Log.d("","hi");
            }
        });

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
        contact.setMessagesList(messagesItems);
        Intent resultIntent = new Intent();
        resultIntent.putExtra("contact", contact);
        setResult(RESULT_OK, resultIntent);
        this.finish();
        super.onBackPressed();
    }

}
