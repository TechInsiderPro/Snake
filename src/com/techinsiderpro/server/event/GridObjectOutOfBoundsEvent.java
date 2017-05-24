package com.techinsiderpro.server.event;

import com.techinsiderpro.common.event.Event;
import com.techinsiderpro.common.game.entity.Entity;

public class GridObjectOutOfBoundsEvent implements Event
{
	private Entity entity;

	public GridObjectOutOfBoundsEvent(Entity entity)
	{
		this.entity = entity;
	}

	public Entity getEntity()
	{
		return entity;
	}

	public void setEntity(Entity entity)
	{
		this.entity = entity;
	}
}
