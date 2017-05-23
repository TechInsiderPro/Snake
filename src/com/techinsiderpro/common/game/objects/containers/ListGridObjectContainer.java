package com.techinsiderpro.common.game.objects.containers;

import com.techinsiderpro.common.game.Position;
import com.techinsiderpro.common.game.objects.GridObject;

import java.util.LinkedList;

public class ListGridObjectContainer extends LinkedList<GridObject> implements GridObjectContainer
{
	private int width, height;

	public ListGridObjectContainer(int width, int height)
	{
		this.width = width;
		this.height = height;
	}

	@Override
	public void setPositionTo(GridObject gridObject)
	{
		removeGridObjectAt(gridObject.getPosition());
		add(gridObject);
	}

	@Override
	public GridObject getGridObjectAt(Position position)
	{
		for(GridObject gridObject : this)
		{
			if(gridObject.getPosition().equals(position))
			{
				return gridObject;
			}
		}

		return null;
	}

	@Override
	public void removeGridObjectAt(Position position)
	{
		remove(getGridObjectAt(position));
	}

	@Override
	public boolean isEmptyAt(Position position)
	{
		return getGridObjectAt(position) == null;
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
}
