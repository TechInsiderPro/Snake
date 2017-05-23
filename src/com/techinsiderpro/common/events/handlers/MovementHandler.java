package com.techinsiderpro.common.events.handlers;

import com.techinsiderpro.common.events.DirectionChangeRequestEvent;
import com.techinsiderpro.common.events.PositionChangeRequestEvent;
import com.techinsiderpro.common.game.Direction;
import com.techinsiderpro.common.game.Position;
import com.techinsiderpro.common.game.objects.containers.GridObjectContainer;

public class MovementHandler
{
	private GridObjectContainer gridObjectContainer;

	public MovementHandler(GridObjectContainer gridObjectContainer)
	{
		this.gridObjectContainer = gridObjectContainer;
	}

	@Handler(eventType = DirectionChangeRequestEvent.class)
	public void handleDirectionChangeRequestEvent(DirectionChangeRequestEvent event)
	{
		if (event.getRequestedDirection() != Direction.getOpposite(event.getTarget().getDirection()))
		{
			event.getTarget().setDirection(event.getRequestedDirection());
		}
	}

	@Handler(eventType = PositionChangeRequestEvent.class)
	public void handlePositionChangeRequestEvent(PositionChangeRequestEvent event)
	{
		if (isAdjacent(event.getTarget().getPosition(), event.getRequestedPosition()) &&
				gridObjectContainer.isEmptyAt(event.getRequestedPosition()))
		{
			event.getTarget().setPosition(event.getRequestedPosition());
			event.consume();
		}
	}

	private boolean isAdjacent(Position position1, Position position2)
	{
		return position1.getX() <= position2.getX() + 1 && position1.getX() >= position2.getX() - 1 &&
				position1.getY() <= position2.getY() + 1 && position1.getY() >= position2.getY() - 1 &&
				position1 != position2;
	}
}
