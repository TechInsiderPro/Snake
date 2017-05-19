package com.techinsiderpro.common.game;

import java.util.Collection;
import java.util.Iterator;

public abstract class GridObjectContainer implements Collection<GridObject>
{
	abstract void setPositionTo(GridObject gridObject);

	abstract GridObject getGridObjectAt(Position position);

	abstract void removeGridObjectAt(Position position);

	boolean isEmptyAt(Position position)
	{
		return getGridObjectAt(position) == null;
	}

	@Override
	public abstract void clear();

	@Override
	public abstract int size();

	@Override
	public abstract boolean isEmpty();

	@Override
	public abstract boolean contains(Object o);

	@Override
	public abstract Iterator<GridObject> iterator();

	@Override
	public abstract Object[] toArray();

	@Override
	public abstract <T> T[] toArray(T[] a);

	@Override
	public abstract boolean add(GridObject gridObject);

	@Override
	public abstract boolean remove(Object o);

	@Override
	public boolean containsAll(Collection<?> c)
	{
		for (Object gridObject : c)
		{
			if (!contains(gridObject))
				return false;
		}

		return true;
	}

	@Override
	public boolean addAll(Collection<? extends GridObject> c)
	{
		boolean status = true;

		for(GridObject gridObject : c)
		{
			if(!add(gridObject))
				status = false;
		}

		return status;
	}

	@Override
	public boolean removeAll(Collection<?> c)
	{
		boolean status = true;

		for(Object gridObject : c)
		{
			if(!remove(gridObject))
				status = false;
		}

		return status;
	}

	@Override
	public boolean retainAll(Collection<?> c)
	{
		clear();
		return addAll((Collection<GridObject>)c);
	}
}
