package com.techinsiderpro.common.event;

import com.techinsiderpro.common.game.entity.component.DirectionComponent;
import com.techinsiderpro.common.game.entity.Entity;

public class DirectionChangeRequestEvent extends ConsumableEvent
{
	private DirectionComponent requestedDirectionComponent;
	private Entity target;

	public DirectionChangeRequestEvent(DirectionComponent requestedDirectionComponent, Entity target)
	{
		this.requestedDirectionComponent = requestedDirectionComponent;
		this.target = target;
	}

	public DirectionComponent getRequestedDirectionComponent()
	{
		return requestedDirectionComponent;
	}

	public Entity getTarget()
	{
		return target;
	}

	public void setRequestedDirectionComponent(DirectionComponent requestedDirectionComponent)
	{
		this.requestedDirectionComponent = requestedDirectionComponent;
	}

	public void setTarget(Entity target)
	{
		this.target = target;
	}
}
