package com.kazakov.newyou.app.service.event.base.impl;

import com.kazakov.newyou.app.service.event.base.Event;

public class DataReceiveEvent implements Event {

    private final String message;

    public DataReceiveEvent(byte[] message) {
        this.message = new String(message);
    }

    public String getMessage() {
        return message;
    }
}
