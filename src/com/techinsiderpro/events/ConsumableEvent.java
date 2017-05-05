package com.techinsiderpro.events;

public class ConsumableEvent implements Event
{
    private boolean consumed;

    public void consume()
    {
        consumed = true;
    }

    public boolean isConsumed()
    {
        return consumed;
    }
}
