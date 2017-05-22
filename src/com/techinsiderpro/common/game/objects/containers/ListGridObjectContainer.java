package com.techinsiderpro.common.game.objects.containers;

import com.techinsiderpro.common.game.Position;
import com.techinsiderpro.common.game.objects.GridObject;

import java.util.LinkedList;

public class ListGridObjectContainer extends LinkedList<GridObject> implements GridObjectContainer
{
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
}
