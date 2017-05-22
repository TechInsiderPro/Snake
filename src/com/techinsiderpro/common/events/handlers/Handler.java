package com.techinsiderpro.common.events.handlers;

import com.techinsiderpro.common.events.Event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Handler
{
	Class<? extends Event> eventType();
}
