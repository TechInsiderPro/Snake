package com.techinsiderpro.game;

import com.techinsiderpro.events.Dispatcher;
import com.techinsiderpro.events.Handler;

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
