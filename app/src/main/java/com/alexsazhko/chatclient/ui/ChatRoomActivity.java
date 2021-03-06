package com.alexsazhko.chatclient.ui;

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

import com.alexsazhko.chatclient.BaseMessage;
import com.alexsazhko.chatclient.R;
import com.alexsazhko.chatclient.ReceiveMessageCallBack;
import com.alexsazhko.chatclient.ServerConnection;
import com.alexsazhko.chatclient.adapter.MessageListAdapter;
import com.alexsazhko.chatclient.entity.ChatMessage;
import com.alexsazhko.chatclient.entity.Contact;
import com.alexsazhko.chatclient.entity.MessageState;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatRoomActivity extends AppCompatActivity implements ReceiveMessageCallBack {

    Context context;
    private EditText etInputMessage;

    private String name;
    private String toUserName;

    private List<ChatMessage> messagesItems;
    private Contact contact;
    private MessageListAdapter adapter;
    private ListView lvMessageList;

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
            toUserName = contact.getName();
        }

        initView();
        initServerConnection();

        /*ChatMessage chatMessage;
        chatMessage = composeMessage(MessageState.NEW);
        serverConnection.setMessageToSend(chatMessage);*/
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
        serverConnection = ServerConnection.getInstance();
        serverConnection.setCallBack(this);
        //threadPool = Executors.newSingleThreadExecutor();
        //threadPool.submit(serverConnection);
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

        adapter.notifyDataSetChanged();
    }

    private class ButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id) {
                case R.id.btnSend:
                    if(!String.valueOf(etInputMessage.getText()).isEmpty()) {
                        ChatMessage chatMessage;
                        chatMessage = composeMessage(MessageState.MESSAGE);
                            messagesItems.add(chatMessage);
                            serverConnection.setMessageToSend(chatMessage);
                            refreshAdapter();
                        etInputMessage.setText("");

                        hideKeyboard();
                    }
                    break;
            }
        }
    }

    private ChatMessage composeMessage(MessageState messageState){
        ChatMessage chatMsg = new ChatMessage();
        String messageContent = String.valueOf(etInputMessage.getText());

        chatMsg.setSendTime(System.currentTimeMillis());
        chatMsg.setUserName(name);
        chatMsg.setToUserName(toUserName);
        if(!messageContent.equals(""))
            chatMsg.setMsgContent(messageContent);
        chatMsg.setMessageFlag(messageState.name());
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

    public void receiveMessage(final BaseMessage msg)
    {
        //final String msg = msg ;
        handler.post(new Runnable() {

            @Override
            public void run() {
                synchronized (this){

                            messagesItems.add((ChatMessage)msg);
                    refreshAdapter();
                }

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
        //serverConnection.setMessageToSend(composeMessage(MessageState.END));
        this.finish();
        super.onBackPressed();
    }

}
