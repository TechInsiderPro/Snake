package com.techinsiderpro.server.event.handler;

import com.techinsiderpro.common.event.handler.Handler;
import com.techinsiderpro.server.event.CollisionEvent;
import com.techinsiderpro.server.event.EntityRemoveRequestEvent;
import com.techinsiderpro.common.event.dispatcher.Dispatcher;
import com.techinsiderpro.common.game.entity.component.EaterComponent;

public class CollisionHandler
{
	private Dispatcher dispatcher;

	public CollisionHandler(Dispatcher dispatcher)
	{
		this.dispatcher = dispatcher;
	}

	@Handler(eventType = CollisionEvent.class)
	public void handleCollisionEvent(CollisionEvent collisionEvent)
	{
		EaterComponent eaterComponent = collisionEvent.getInstigator().getComponent(EaterComponent.class);

		if(eaterComponent != null && eaterComponent.canEat(collisionEvent.getTarget()))
		{
			dispatcher.dispatch(new EntityRemoveRequestEvent(collisionEvent.getTarget()));
		}
		else
		{
			dispatcher.dispatch(new EntityRemoveRequestEvent(collisionEvent.getInstigator()));
		}
	}
}
