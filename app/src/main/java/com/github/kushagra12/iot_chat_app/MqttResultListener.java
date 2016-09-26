package com.github.kushagra12.iot_chat_app;

/**
 * Created by Kushagra Vaish on 9/26/2016.
 */

public interface MqttResultListener {
    void onSuccess();
    void onFailure();
}
