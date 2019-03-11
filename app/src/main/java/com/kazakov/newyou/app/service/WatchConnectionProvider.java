package com.kazakov.newyou.app.service;

import java.util.function.Supplier;

public class WatchConnectionProvider {

    Supplier<Class> supplier;

    public WatchConnectionProvider(Supplier<Class> supplier) {
        this.supplier = supplier;
    }

    public Class<?> take() {
        return supplier.get();
    }
}
