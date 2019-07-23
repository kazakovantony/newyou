package com.kazakov.newyou.app.service.event;

import com.kazakov.newyou.app.service.event.base.Event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class EventService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventService.class);

    private Map<Class, Consumer> handlers = new HashMap<>();

    public void triggerEvent(Event event) {
        LOGGER.debug("Event triggered: {}", event.getClass().getName());
        handlers.get(event.getClass()).accept(event);
    }

    public <EventType> void addEventHandler(Consumer<EventType> handler, Class<EventType> type) {
        handlers.put(type, handler);
    }
}
