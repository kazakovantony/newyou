package com.itbulls.newyou.app.listener;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.itbulls.newyou.app.service.WatchConnectionService;
import com.itbulls.newyou.app.service.WatchServiceProvider;


public class ServiceConnectionListener implements ServiceConnection {

    WatchServiceProvider watchServiceProvider;

    @Override
    public void onServiceConnected(ComponentName className, IBinder service) {
        watchServiceProvider
                .setWatchConnectionService(((WatchConnectionService.LocalBinder) service).getThis());

    }

    @Override
    public void onServiceDisconnected(ComponentName className) {
     /*   mConsumerService = null;
        mIsBound = false;
        updateTextView("onServiceDisconnected");
  */
    }

    public synchronized void setWatchServiceProvider(WatchServiceProvider watchServiceProvider) {
        this.watchServiceProvider = watchServiceProvider;
    }
}
