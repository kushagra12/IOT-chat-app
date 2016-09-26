package com.github.kushagra12.iot_chat_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private ListView listView;
    private View btnSend;
    private EditText editText;
    private List<ChatMessage> chatMessages;
    private ArrayAdapter<ChatMessage> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        chatMessages = new ArrayList<>();

        DataStore.getInstance().getClient().setMessageCallBack(new MqttMessageListener() {
            @Override
            public void onMessage(byte[] message) {
                String str = null;
                try {
                    str = new String(message, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if(str != null){
                    try {
                        JSONObject obj = new JSONObject(str);
                        if(obj.getString("client").equals("node"))
                            chatMessages.add(new ChatMessage(obj.getString("message"), true));
                        else
                            chatMessages.add(new ChatMessage(obj.getString("message"), false));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });

        listView = (ListView) findViewById(R.id.list_msg);
        btnSend = findViewById(R.id.btn_chat_send);
        editText = (EditText) findViewById(R.id.msg_type);

        //set ListView adapter first
        adapter = new MessageAdapter(this, R.layout.chat_left, chatMessages);
        listView.setAdapter(adapter);

        //event for button SEND
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().trim().equals("")) {
                    Toast.makeText(ChatActivity.this, "Please input some text...", Toast.LENGTH_SHORT).show();
                } else {
                    //add message to list
                    DataStore.getInstance().getClient().sendMessage(editText.getText().toString());
                    editText.setText("");
                }
            }
        });
    }
}

