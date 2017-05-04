package com.techinsiderpro.events;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Dispatcher implements Handler
{
    private Map<Class<? extends Event>, Collection<Handler>> handlers;

    public Dispatcher()
    {
        handlers = new HashMap<>();
    }

    public void registerHandler(Class<? extends Event> eventType, Handler<? extends Event> handler)
    {
        if (handlers.containsKey(eventType))
            handlers.get(eventType).add(handler);
        else
            handlers.put(eventType, Arrays.asList(handler));
    }

    @Override
    public void dispatch(Event event)
    {
        if (handlers.containsKey(event.getClass()))
            for (Handler handler : handlers.get(event.getClass()))
                handler.dispatch(event);
    }
}
