package com.techinsiderpro.server.event.handler;

import com.techinsiderpro.common.event.handler.Handler;
import com.techinsiderpro.server.event.PositionChangeRequestEvent;
import com.techinsiderpro.server.event.UpdateEvent;
import com.techinsiderpro.common.event.dispatcher.Dispatcher;
import com.techinsiderpro.common.game.entity.Entity;
import com.techinsiderpro.common.game.entity.component.DirectionComponent;
import com.techinsiderpro.common.game.entity.component.PositionComponent;
import com.techinsiderpro.common.game.entity.container.EntityContainer;

public class UpdateHandler
{
	private EntityContainer entityContainer;
	private Dispatcher dispatcher;

	public UpdateHandler(EntityContainer entityContainer,
	                     Dispatcher dispatcher)
	{
		this.entityContainer = entityContainer;
		this.dispatcher = dispatcher;
	}

	@Handler(eventType = UpdateEvent.class)
	public void update(UpdateEvent updateEvent)
	{
		for (Entity entity : entityContainer)
		{
			PositionChangeRequestEvent positionChangeRequestEvent = new PositionChangeRequestEvent(entity,
			                                                                                       new PositionComponent(
					                                                                                       entity
							                                                                                       .getComponent(
									                                                                                       PositionComponent.class)
							                                                                                       .getX() +
					                                                                                       entity
							                                                                                       .getComponent(
									                                                                                       DirectionComponent.class)
							                                                                                       .getxShift(),
					                                                                                       entity
							                                                                                       .getComponent(
									                                                                                       PositionComponent.class)
							                                                                                       .getY() +
					                                                                                       entity
							                                                                                       .getComponent(
									                                                                                       DirectionComponent.class)
							                                                                                       .getyShift()));
			dispatcher.dispatch(positionChangeRequestEvent);
			System.out.println("Dispatched " + positionChangeRequestEvent.toString());
		}
	}
}
