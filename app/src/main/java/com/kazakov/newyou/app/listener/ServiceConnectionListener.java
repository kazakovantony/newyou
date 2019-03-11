package com.kazakov.newyou.app.listener;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.kazakov.newyou.app.service.WatchConnectionService;
import com.kazakov.newyou.app.service.WatchServiceHolder;


public class ServiceConnectionListener implements ServiceConnection {

    WatchServiceHolder watchServiceHolder;

    @Override
    public void onServiceConnected(ComponentName className, IBinder service) {
        watchServiceHolder
                .setWatchConnectionService(((WatchConnectionService.LocalBinder) service).getThis());

    }

    @Override
    public void onServiceDisconnected(ComponentName className) {
     /*   mConsumerService = null;
        mIsBound = false;
        updateTextView("onServiceDisconnected");
  */
    }

    public synchronized void setWatchServiceHolder(WatchServiceHolder watchServiceHolder) {
        this.watchServiceHolder = watchServiceHolder;
    }
}
