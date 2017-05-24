package com.techinsiderpro.server.event;

import com.techinsiderpro.common.event.Event;
import com.techinsiderpro.common.game.entity.Entity;

public class CollisionEvent implements Event
{
	private Entity instigator;
	private Entity target;

	public CollisionEvent(Entity instigator, Entity target)
	{
		this.instigator = instigator;
		this.target = target;
	}

	public Entity getInstigator()
	{
		return instigator;
	}

	public void setInstigator(Entity instigator)
	{
		this.instigator = instigator;
	}

	public Entity getTarget()
	{
		return target;
	}

	public void setTarget(Entity target)
	{
		this.target = target;
	}
}
