package com.techinsiderpro.common.game.entity.component;

import com.techinsiderpro.common.game.entity.Entity;

public abstract class EaterComponent implements Component
{
	public abstract boolean canEat(Entity entity);
}
