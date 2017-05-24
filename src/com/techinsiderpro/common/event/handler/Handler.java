package com.techinsiderpro.common.event.handler;

import com.techinsiderpro.common.event.Event;

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
