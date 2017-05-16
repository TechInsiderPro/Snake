package com.techinsiderpro.game;

import com.techinsiderpro.events.Event;

import java.io.Serializable;

public class DirectionChangeRequestEvent implements Event, Serializable
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
