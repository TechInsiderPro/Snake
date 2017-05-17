package com.techinsiderpro.game;

import com.techinsiderpro.events.Dispatcher;
import com.techinsiderpro.events.Event;
import com.techinsiderpro.events.Handler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class Test
{
	private ArrayList<GridObject> gridObjects;

	public Test()
	{
		gridObjects = new ArrayList<>();

		Dispatcher dispatcher = new Dispatcher();
		dispatcher.registerHandler(DirectionChangeRequestEvent.class, new Handler<DirectionChangeRequestEvent>()
		{
			@Override
			public void dispatch(DirectionChangeRequestEvent event)
			{
				if (event.getRequestedDirection() != Direction.getOpposite(event.getRequestedDirection()))
				{
					event.getTarget().setDirection(event.getRequestedDirection());
				}
			}
		});

		GridObject objectLocal = new GridObject(new Position(1, 1), Direction.DOWN);

		GridObject objectRemote = new GridObject(new Position(1, 1), Direction.DOWN);

		gridObjects.add(new GridObject(new Position(1, 1), Direction.DOWN));

		System.out.println(gridObjects.get(0).toString());

		Event remoteEvent = new DirectionChangeRequestEvent(Direction.UP, new GridObject(new Position(1, 1), Direction.DOWN));
		convertToLocalEvent(remoteEvent);

		dispatcher.dispatch(remoteEvent);

		System.out.println(gridObjects.get(0).getDirection());
	}

	private void convertToLocalEvent(Event event)
	{
		try
		{
			for (Method method : event.getClass().getMethods())
			{
				if (method.getReturnType().equals(GridObject.class))
				{
					System.out.println(method.invoke(event).toString());
					System.out.println(method.getName());
					event.getClass().getMethod("set" + method.getName().substring(3, method.getName().length()), GridObject.class).invoke(event, getLocalGridObject((GridObject) method.invoke(event)));
					System.out.println(method.invoke(event).toString());
				}
			}
		} catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e)
		{
			e.printStackTrace();
		}
	}

	private GridObject getLocalGridObject(GridObject remoteGridObject)
	{
		for (GridObject gridObject : gridObjects)
		{
			if (gridObject.equals(remoteGridObject))
				return gridObject;
		}

		return null;
	}

	public static void main(String[] args)
	{
		new Test();
	}
}
