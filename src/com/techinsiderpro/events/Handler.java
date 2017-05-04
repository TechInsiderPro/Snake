package com.techinsiderpro.events;

public interface Handler<E extends Event>
{
    void dispatch(E event);
}
