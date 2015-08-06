package com.alexsazhko.chatclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.alexsazhko.chatclient.entity.ChatMessage;

import java.util.List;

public class ChatRoomActivity extends AppCompatActivity {

    private ListView lvMessageList;
    private TextView tvInputMessage;
    private Button btnSend;

    private String name;
    private String toUserName;

    private List<ChatMessage> messagesItems;
    private Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            name = extras.getString("userName");
            contact = extras.getParcelable("contact");

        }
        initView();
        refreshMessageList();
    }

    private void initView() {
        getSupportActionBar().setTitle(contact.getName());
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_contact);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        lvMessageList = (ListView)findViewById(R.id.lvMessageList);
        tvInputMessage = (TextView) findViewById(R.id.etInputMsg);
        btnSend = (Button) findViewById(R.id.btnSend);
    }

    protected void onStart() {
        super.onStart();
        if (!contact.getMessagesList().isEmpty()) {
            messagesItems.addAll(contact.getMessagesList());
            contact.getMessagesList().clear();
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

    private void refreshMessageList(){

    }

    private class ButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id) {
                case R.id.btnSend:
                    messagesItems.add(composeMessage("MESSAGE"));
                    break;
            }
        }
    }

    private ChatMessage composeMessage(String flagMessage){
        ChatMessage chatMsg = new ChatMessage();
        chatMsg.setSendTime(System.currentTimeMillis());
        chatMsg.setUserName(name);
        return chatMsg;
    }

}
