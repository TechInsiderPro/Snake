package com.techinsiderpro.common.events.dispatchers;

import com.techinsiderpro.common.events.ConsumableEvent;
import com.techinsiderpro.common.events.Event;
import com.techinsiderpro.common.events.handlers.Handler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class Dispatcher
{
	private Map<Class<? extends Event>, Collection<Object>> handlers;

	public Dispatcher()
	{
		handlers = new HashMap<>();
	}

	public void registerHandler(Object handler)
	{
		for (Method method : handler.getClass().getMethods())
		{
			if (method.isAnnotationPresent(Handler.class))
			{
				Class<? extends Event> eventType = method.getAnnotation(Handler.class).eventType();

				if (handlers.containsKey(eventType))
				{
					handlers.get(eventType).add(handler);
				}
				else
				{
					handlers.put(eventType, new ArrayList<>(Collections.singletonList(handler)));
				}
			}
		}
	}

	public void dispatch(Event event)
	{
		if (handlers.containsKey(event.getClass()))
		{
			for (Object handler : handlers.get(event.getClass()))
			{
				if (event instanceof ConsumableEvent && ((ConsumableEvent) event).isConsumed())
				{
					return;
				}
				else
				{
					try
					{
						getHandlerMethod(handler, event.getClass()).invoke(handler, event);
					}
					catch (IllegalAccessException | InvocationTargetException e)
					{
						e.printStackTrace();
					}
				}
			}
		}
	}

	private Method getHandlerMethod(Object handler, Class<? extends Event> eventType)
	{
		for (Method method : handler.getClass().getMethods())
		{
			if (method.isAnnotationPresent(Handler.class) &&
			    method.getAnnotation(Handler.class).eventType() == eventType)
			{
				return method;
			}
		}

		return null;
	}
}
