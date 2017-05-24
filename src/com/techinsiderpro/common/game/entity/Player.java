package com.techinsiderpro.common.game.entity;

import com.techinsiderpro.common.game.entity.component.DirectionComponent;
import com.techinsiderpro.common.game.entity.component.EaterComponent;
import com.techinsiderpro.common.game.entity.component.PositionComponent;

public class Player extends Entity
{
	public Player(PositionComponent positionComponent, DirectionComponent directionComponent)
	{
		putComponent(positionComponent);
		putComponent(directionComponent);
		putComponent(new EaterComponent()
		{
			@Override
			public boolean canEat(Entity entity)
			{
				return false;
			}
		});
	}
}
