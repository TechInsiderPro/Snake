package com.techinsiderpro.server.event;

import com.techinsiderpro.common.event.ConsumableEvent;
import com.techinsiderpro.common.game.entity.component.PositionComponent;
import com.techinsiderpro.common.game.entity.Entity;

public class PositionChangeRequestEvent extends ConsumableEvent
{
	private Entity target;

	private PositionComponent requestedPositionComponent;

	public PositionChangeRequestEvent(Entity target, PositionComponent requestedPositionComponent)
	{
		this.target = target;
		this.requestedPositionComponent = requestedPositionComponent;
	}

	public Entity getTarget()
	{
		return target;
	}

	public void setTarget(Entity target)
	{
		this.target = target;
	}

	public PositionComponent getRequestedPositionComponent()
	{
		return requestedPositionComponent;
	}

	public void setRequestedPositionComponent(PositionComponent requestedPositionComponent)
	{
		this.requestedPositionComponent = requestedPositionComponent;
	}
}
