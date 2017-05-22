package com.techinsiderpro.common.events;

import com.techinsiderpro.common.game.Position;
import com.techinsiderpro.common.game.objects.GridObject;

public class PositionChangeRequestEvent extends ConsumableEvent
{
	private GridObject target;

	private Position requestedPosition;

	public PositionChangeRequestEvent(GridObject target, Position requestedPosition)
	{
		this.target = target;
		this.requestedPosition = requestedPosition;
	}

	public GridObject getTarget()
	{
		return target;
	}

	public void setTarget(GridObject target)
	{
		this.target = target;
	}

	public Position getRequestedPosition()
	{
		return requestedPosition;
	}

	public void setRequestedPosition(Position requestedPosition)
	{
		this.requestedPosition = requestedPosition;
	}
}
