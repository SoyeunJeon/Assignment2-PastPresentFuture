package com.example.pastpresentfuture;

/**
 * Created by s3622567 on 1/12/2017.
 */

// The structure of data to be stored in database

public class TodoMessage {
    String text;

    public TodoMessage() {}

    public TodoMessage(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
