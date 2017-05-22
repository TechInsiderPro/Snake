package com.techinsiderpro.common.events;

import com.techinsiderpro.common.game.Direction;
import com.techinsiderpro.common.game.objects.GridObject;

public class DirectionChangeRequestEvent extends ConsumableEvent
{
	private Direction requestedDirection;
	private GridObject target;

	public DirectionChangeRequestEvent(Direction requestedDirection, GridObject target)
	{
		this.requestedDirection = requestedDirection;
		this.target = target;
	}

	public Direction getRequestedDirection()
	{
		return requestedDirection;
	}

	public GridObject getTarget()
	{
		return target;
	}

	public void setRequestedDirection(Direction requestedDirection)
	{
		this.requestedDirection = requestedDirection;
	}

	public void setTarget(GridObject target)
	{
		this.target = target;
	}
}
