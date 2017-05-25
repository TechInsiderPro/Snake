package com.techinsiderpro.server.event.handler;

import com.techinsiderpro.common.event.handler.Handler;
import com.techinsiderpro.common.game.entity.container.EntityContainer;
import com.techinsiderpro.server.event.EntityRemoveRequestEvent;

public class EntityRemovalHandler
{
	private EntityContainer entityContainer;

	public EntityRemovalHandler(EntityContainer entityContainer)
	{
		this.entityContainer = entityContainer;
	}

	@Handler(eventType = EntityRemoveRequestEvent.class)
	public void handleEntityRemoveRequestEvent(EntityRemoveRequestEvent entityRemoveRequestEvent)
	{
		entityContainer.remove(entityRemoveRequestEvent.getTarget());
	}
}
