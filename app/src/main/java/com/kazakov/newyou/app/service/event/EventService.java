package com.kazakov.newyou.app.service.event;

import com.kazakov.newyou.app.service.event.base.Event;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class EventService {

    private Map<Class, Consumer> handlers = new HashMap<>();

    public void triggerEvent(Event event) {
        handlers.get(event.getClass()).accept(event);
    }

    public <EventType> void addEventHandler(Consumer<EventType> handler, Class<EventType> type) {
        handlers.put(type, handler);
    }
}
