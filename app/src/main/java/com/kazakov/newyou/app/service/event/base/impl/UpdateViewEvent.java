package com.kazakov.newyou.app.service.event.base.impl;

import com.kazakov.newyou.app.service.event.base.Event;

public class UpdateViewEvent implements Event {

    private final String updateText;

    public UpdateViewEvent(String updateText) {
        this.updateText = updateText;
    }

    public String getUpdateText() {
        return updateText;
    }
}
