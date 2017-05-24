package com.techinsiderpro.common.game.entity;

import com.techinsiderpro.common.game.entity.component.PositionComponent;

public class Apple extends Entity
{
	public Apple(PositionComponent positionComponent)
	{
		putComponent(positionComponent);
	}
}
