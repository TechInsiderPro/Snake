package com.techinsiderpro.common.events;

public interface Handler<E extends Event>
{
    void dispatch(E event);
}
