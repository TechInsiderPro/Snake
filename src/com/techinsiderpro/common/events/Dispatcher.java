package com.techinsiderpro.common.events;

import java.util.*;

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
            handlers.put(eventType, new ArrayList<Handler>(Collections.singletonList(handler)));
    }

    @Override
    public void dispatch(Event event)
    {
        if (handlers.containsKey(event.getClass()))
            for (Handler handler : handlers.get(event.getClass()))
                if (event instanceof ConsumableEvent && ((ConsumableEvent) event).isConsumed())
                    return;
                else
                    handler.dispatch(event);
    }
}
