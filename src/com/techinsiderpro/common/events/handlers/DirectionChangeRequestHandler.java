package com.techinsiderpro.common.events.handlers;

import com.techinsiderpro.common.events.DirectionChangeRequestEvent;
import com.techinsiderpro.common.game.Direction;

public class DirectionChangeRequestHandler
{
	@Handler(eventType = DirectionChangeRequestEvent.class)
	public void dispatch(DirectionChangeRequestEvent event)
	{
		if (event.getRequestedDirection() != Direction.getOpposite(event.getTarget().getDirection()))
		{
			event.getTarget().setDirection(event.getRequestedDirection());
			event.consume();
		}
	}
}
