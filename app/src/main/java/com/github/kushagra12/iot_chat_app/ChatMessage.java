package com.github.kushagra12.iot_chat_app;

/**
 * Created by Kushagra Vaish on 9/26/2016.
 */

public class ChatMessage {
    private String content;
    private boolean isMine;

    public ChatMessage(String content, boolean isMine) {
        this.content = content;
        this.isMine = isMine;
    }

    public String getContent() {
        return content;
    }

    public boolean isMine() {
        return isMine;
    }
}
