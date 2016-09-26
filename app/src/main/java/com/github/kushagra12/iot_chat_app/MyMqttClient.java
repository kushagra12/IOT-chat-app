package com.github.kushagra12.iot_chat_app;

import android.content.Context;
import android.util.Log;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONException;
import org.json.JSONObject;


public class MyMqttClient {
    private MqttAndroidClient mqttAndroidClient;
    private MqttMessageListener listener;
    private String topic = "iot_proj_vit/test";
    private int qos = 1;

    public MyMqttClient(Context c){
        String clientId = MqttClient.generateClientId();
        mqttAndroidClient =
                new MqttAndroidClient(c, "tcp://broker.hivemq.com:1883",
                        clientId);
    }

    public void connect(final MqttResultListener listener){

        try {
            IMqttToken token = mqttAndroidClient.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Log.d("MOB_CONNECT", "SUCCESS_CONNECTION");
                    listener.onSuccess();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Log.d("MOB_CONNECT", "FAILURE_CONNECTION");

                    listener.onFailure();
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void subscribe(final MqttResultListener listener){
        try {
            IMqttToken subToken = mqttAndroidClient.subscribe(topic, qos);
            subToken.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.d("MOB_CONNECT", "SUCCESS_SUBSCRIPTION");
                    listener.onSuccess();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken,
                                      Throwable exception) {
                    listener.onFailure();
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message){
        JSONObject obj = new JSONObject();
        try {
            obj.put("client", "android");
            obj.put("message", message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            mqttAndroidClient.publish(topic, new MqttMessage(obj.toString().getBytes()));
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    public void setMessageCallBack(final MqttMessageListener l){
        mqttAndroidClient.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                l.onMessage(message.getPayload());
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
    }
}
