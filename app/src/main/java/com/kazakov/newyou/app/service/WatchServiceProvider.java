package com.kazakov.newyou.app.service;

public class WatchServiceProvider {

    private WatchConnectionService watchConnectionService;

    public synchronized void setWatchConnectionService(WatchConnectionService watchConnectionService) {
        this.watchConnectionService = watchConnectionService;
    }

    public synchronized WatchConnectionService getWatchConnectionService() {
        return watchConnectionService;
    }
}
