package com.kazakov.newyou.app.service;

import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import com.kazakov.newyou.app.R;
import com.kazakov.newyou.app.listener.SocketListener;
import com.kazakov.newyou.app.service.event.EventService;
import com.kazakov.newyou.app.service.event.base.impl.UpdateViewEvent;
import com.samsung.android.sdk.accessory.SA;
import com.samsung.android.sdk.accessory.SAAgent;
import com.samsung.android.sdk.accessory.SAPeerAgent;
import com.samsung.android.sdk.accessory.SASocket;

import java.io.IOException;

public class WatchConnectionService extends SAAgent {

    private static final String TAG = "NewYou";

    SocketListener socketListener;
    EventService eventService;
    private Handler handler;
    private LocalBinder localBinder;

    public WatchConnectionService() {
        super(TAG, SocketListener.class);
        handler = new Handler();
        localBinder = new LocalBinder();

    }

    public class LocalBinder extends Binder {
        public WatchConnectionService getThis() {
           return WatchConnectionService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SA mAccessory = new SA();
        try {
            mAccessory.initialize(this);
        } catch (Exception e1) {
            e1.printStackTrace();
            /*
             * Your application can not use Samsung Accessory SDK. Your application should work smoothly
             * without using this SDK, or you may want to notify user and close your application gracefully
             * (release resources, stop Service threads, close UI thread, etc.)
             */
            stopSelf();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return  localBinder;
    }

    public boolean sendData(String data) {
        boolean result = true;
        try {
            socketListener.send(getServiceChannelId(0), data.getBytes());
        } catch (IOException e) {
            result = false;
        }
        return result;
    }

    @Override
    protected void onFindPeerAgentsResponse(SAPeerAgent[] peerAgents, int result) {
        if ((result == SAAgent.PEER_AGENT_FOUND) && (peerAgents != null)) {
            for (SAPeerAgent peerAgent : peerAgents)
                requestServiceConnection(peerAgent);
        } else if (result == SAAgent.FINDPEER_DEVICE_NOT_CONNECTED) {
            Toast.makeText(getApplicationContext(), "FINDPEER_DEVICE_NOT_CONNECTED", Toast.LENGTH_LONG).show();
            updateTextView("Disconnected");
        } else {
            Toast.makeText(getApplicationContext(), R.string.NoPeersFound, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onServiceConnectionRequested(SAPeerAgent peerAgent) {
        if (peerAgent != null) {
            acceptServiceConnectionRequest(peerAgent);
        }
    }

    @Override
    protected void onServiceConnectionResponse(SAPeerAgent peerAgent, SASocket socket, int result) {

        if (result == SAAgent.CONNECTION_SUCCESS) {
            if(socketListener == null) {
                socketListener = (SocketListener) socket;
                socketListener.setEventService(eventService);
                socketListener.setHandler(handler);
            }
            updateTextView("Connected");
        } else if (result == SAAgent.CONNECTION_ALREADY_EXIST) {
            updateTextView("Connected");
            Toast.makeText(getBaseContext(), "CONNECTION_ALREADY_EXIST", Toast.LENGTH_LONG).show();
        } else if (result == SAAgent.CONNECTION_DUPLICATE_REQUEST) {
            Toast.makeText(getBaseContext(), "CONNECTION_DUPLICATE_REQUEST", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getBaseContext(), R.string.ConnectionFailure, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onError(SAPeerAgent peerAgent, String errorMessage, int errorCode) {
        super.onError(peerAgent, errorMessage, errorCode);
    }

    @Override
    protected void onPeerAgentsUpdated(SAPeerAgent[] peerAgents, int result) {
        final SAPeerAgent[] peers = peerAgents;
        final int status = result;
        handler.post(() -> {
            if (peers != null) {
                if (status == SAAgent.PEER_AGENT_AVAILABLE) {
                    Toast.makeText(getApplicationContext(), "PEER_AGENT_AVAILABLE", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "PEER_AGENT_UNAVAILABLE", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void updateTextView(final String message) {
        handler.post(() -> eventService.triggerEvent(new UpdateViewEvent(message)));
    }

    public void findPeers() {
        findPeerAgents();
    }

    public synchronized boolean closeConnection() {
        if (socketListener.isConnected()) {
            socketListener.close();
            return true;
        }
        return false;
    }

    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }
}
