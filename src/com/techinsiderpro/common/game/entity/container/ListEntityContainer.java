package com.techinsiderpro.common.game.entity.container;

import com.techinsiderpro.common.game.entity.component.PositionComponent;
import com.techinsiderpro.common.game.entity.Entity;

import java.util.concurrent.CopyOnWriteArrayList;

public class ListEntityContainer extends CopyOnWriteArrayList<Entity> implements EntityContainer
{
	private int width, height;

	public ListEntityContainer(int width, int height)
	{
		this.width = width;
		this.height = height;
	}

	@Override
	public boolean add(Entity entity)
	{
		return isWithinBounds(entity.getComponent(PositionComponent.class)) && super.add(entity);
	}

	@Override
	public void setPositionTo(Entity entity)
	{
		removeGridObjectAt(entity.getComponent(PositionComponent.class));
		add(entity);
	}

	@Override
	public Entity getGridObjectAt(PositionComponent positionComponent)
	{
		for(Entity entity : this)
		{
			if(entity.getComponent(PositionComponent.class).equals(positionComponent))
			{
				return entity;
			}
		}

		return null;
	}

	@Override
	public void removeGridObjectAt(PositionComponent positionComponent)
	{
		remove(getGridObjectAt(positionComponent));
	}

	@Override
	public boolean isEmptyAt(PositionComponent positionComponent)
	{
		return getGridObjectAt(positionComponent) == null;
	}

	@Override
	public int getWidth()
	{
		return width;
	}

	@Override
	public int getHeight()
	{
		return height;
	}

	@Override
	public boolean isWithinBounds(PositionComponent positionComponent)
	{
		return positionComponent.getX() < width && positionComponent.getX() >= 0 && positionComponent.getY() < height && positionComponent
				                                                                                                                 .getY() >= 0;
	}
}
