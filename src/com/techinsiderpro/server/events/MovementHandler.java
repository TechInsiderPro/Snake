package com.techinsiderpro.server.events;

import com.techinsiderpro.common.game.Direction;
import com.techinsiderpro.common.events.DirectionChangeRequestEvent;
import com.techinsiderpro.common.events.Dispatcher;
import com.techinsiderpro.common.events.Handler;

public class MovementHandler
{
	public MovementHandler(Dispatcher dispatcher)
	{
		// DirectionChangeRequestEvent Handler
		dispatcher.registerHandler(DirectionChangeRequestEvent.class, new Handler<DirectionChangeRequestEvent>()
		{
			@Override
			public void dispatch(DirectionChangeRequestEvent event)
			{
				if (event.getRequestedDirection() != Direction.getOpposite(event.getTarget().getDirection()))
				{
					event.getTarget().setDirection(event.getRequestedDirection());
				}
			}
		});
	}
}
