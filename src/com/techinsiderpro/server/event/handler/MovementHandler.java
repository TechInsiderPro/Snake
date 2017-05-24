package com.techinsiderpro.server.event.handler;

import com.techinsiderpro.common.event.handler.Handler;
import com.techinsiderpro.server.event.CollisionEvent;
import com.techinsiderpro.common.event.DirectionChangeRequestEvent;
import com.techinsiderpro.server.event.GridObjectOutOfBoundsEvent;
import com.techinsiderpro.server.event.PositionChangeRequestEvent;
import com.techinsiderpro.common.event.dispatcher.Dispatcher;
import com.techinsiderpro.common.game.entity.component.DirectionComponent;
import com.techinsiderpro.common.game.entity.component.PositionComponent;
import com.techinsiderpro.common.game.entity.container.EntityContainer;

public class MovementHandler
{
	private EntityContainer entityContainer;
	private Dispatcher dispatcher;

	public MovementHandler(EntityContainer entityContainer,
	                       Dispatcher dispatcher)
	{
		this.entityContainer = entityContainer;
		this.dispatcher = dispatcher;
	}

	@Handler(eventType = DirectionChangeRequestEvent.class)
	public void handleDirectionChangeRequestEvent(DirectionChangeRequestEvent event)
	{
		if (event.getRequestedDirectionComponent() != DirectionComponent
				.getOpposite(event.getTarget().getComponent(DirectionComponent.class)))
		{
			event.getTarget().putComponent(event.getRequestedDirectionComponent());
		}
		else
		{
			dispatcher.dispatch(new CollisionEvent(event.getTarget(), event.getTarget()));
		}
	}

	@Handler(eventType = PositionChangeRequestEvent.class)
	public void handlePositionChangeRequestEvent(PositionChangeRequestEvent event)
	{
		if (isAdjacent(event.getTarget().getComponent(PositionComponent.class), event.getRequestedPositionComponent()))
		{
			if (entityContainer.isWithinBounds(event.getRequestedPositionComponent()))
			{
				if (entityContainer.isEmptyAt(event.getRequestedPositionComponent()))
				{
					entityContainer.remove(event.getTarget());
					event.getTarget().putComponent(event.getRequestedPositionComponent());
					entityContainer.add(event.getTarget());

					event.consume();
				}
				else
				{
					dispatcher.dispatch(new CollisionEvent(event.getTarget(), entityContainer
							.getGridObjectAt(event.getRequestedPositionComponent())));
				}
			}
			else
			{
				dispatcher.dispatch(new GridObjectOutOfBoundsEvent(event.getTarget()));
			}
		}
	}

	private boolean isAdjacent(PositionComponent positionComponent1, PositionComponent positionComponent2)
	{
		return positionComponent1.getX() <= positionComponent2.getX() + 1 && positionComponent1.getX() >= positionComponent2
				                                                                                                  .getX() - 1 &&
		       positionComponent1.getY() <= positionComponent2.getY() + 1 && positionComponent1.getY() >= positionComponent2
				                                                                                                  .getY() - 1 &&
		       positionComponent1 != positionComponent2;
	}
}
