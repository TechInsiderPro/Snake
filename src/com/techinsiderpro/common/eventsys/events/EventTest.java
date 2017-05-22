package com.techinsiderpro.common.eventsys.events;

import com.techinsiderpro.common.eventsys.Dispatcher;
import com.techinsiderpro.common.eventsys.handlers.DirectionChangeRequestHandler;
import com.techinsiderpro.common.game.Direction;
import com.techinsiderpro.common.game.Position;
import com.techinsiderpro.common.game.objects.GridObject;

public class EventTest
{
	public static void main(String[] args)
	{
		Dispatcher dispatcher = new Dispatcher();
		dispatcher.registerHandler(new DirectionChangeRequestHandler());

		GridObject gridObject = new GridObject(new Position(0, 0), Direction.UP);

		dispatcher.dispatch(new DirectionChangeRequestEvent(Direction.DOWN, gridObject));

		System.out.println(gridObject.getDirection());
	}
}
