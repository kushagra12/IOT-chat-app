package com.github.kushagra12.iot_chat_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        DataStore.getInstance().setClient(new MyMqttClient(this.getApplicationContext()));

        DataStore.getInstance().getClient().connect(new MqttResultListener() {
            @Override
            public void onSuccess() {
                DataStore.getInstance().getClient().subscribe(new MqttResultListener() {
                    @Override
                    public void onSuccess() {
                        Intent i = new Intent(SplashActivity.this, ChatActivity.class);
                        startActivity(i);
                    }

                    @Override
                    public void onFailure() {

                    }
                });
            }

            @Override
            public void onFailure() {

            }
        });
    }

}
