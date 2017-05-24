package com.techinsiderpro.server.event;

import com.techinsiderpro.common.event.Event;
import com.techinsiderpro.common.game.entity.Entity;

public class EntityRemoveRequestEvent implements Event
{
	private Entity target;

	public EntityRemoveRequestEvent(Entity target)
	{
		this.target = target;
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
