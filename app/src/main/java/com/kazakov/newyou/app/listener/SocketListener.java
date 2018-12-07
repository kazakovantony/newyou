package com.kazakov.newyou.app.listener;

import android.os.Handler;

import com.kazakov.newyou.app.service.event.EventService;
import com.kazakov.newyou.app.service.event.base.impl.DataReceiveEvent;
import com.samsung.android.sdk.accessory.SASocket;

public class SocketListener extends SASocket {

    private EventService eventService;
    private Handler handler;

    public SocketListener() {
        super(SocketListener.class.getName());
    }

    @Override
    public void onError(int channelId, String errorMessage, int errorCode) {
    }

    @Override
    public void onReceive(int channelId, byte[] data) {
        handler.post(() -> eventService.triggerEvent(new DataReceiveEvent(data)));
    }

    @Override
    protected void onServiceConnectionLost(int reason) {
        //      updateTextView("Disconnected");
           // closeConnection();
    }

    public synchronized void setEventService(EventService eventService) {
        this.eventService = eventService;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}
