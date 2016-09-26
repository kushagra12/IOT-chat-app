package com.github.kushagra12.iot_chat_app;

/**
 * Created by Kushagra Vaish on 9/26/2016.
 */
public class DataStore {
    private static DataStore ourInstance = new DataStore();

    private MyMqttClient client;

    public static DataStore getInstance() {
        return ourInstance;
    }

    private DataStore() {

    }

    public MyMqttClient getClient() {
        return client;
    }

    public void setClient(MyMqttClient client) {
        this.client = client;
    }
}
