package com.techinsiderpro.common.game.objects.containers;

import com.techinsiderpro.common.game.Position;
import com.techinsiderpro.common.game.objects.GridObject;

import java.util.Collection;
import java.util.Iterator;

public interface GridObjectContainer extends Collection<GridObject>
{
	void setPositionTo(GridObject gridObject);

	GridObject getGridObjectAt(Position position);

	void removeGridObjectAt(Position position);

	boolean isEmptyAt(Position position);

	@Override
	void clear();

	@Override
	int size();

	@Override
	boolean isEmpty();

	@Override
	boolean contains(Object o);

	@Override
	Iterator<GridObject> iterator();

	@Override
	Object[] toArray();

	@Override
	<T> T[] toArray(T[] a);

	@Override
	boolean add(GridObject gridObject);

	@Override
	boolean remove(Object o);

	@Override
	boolean containsAll(Collection<?> c);

	@Override
	boolean addAll(Collection<? extends GridObject> c);

	@Override
	boolean removeAll(Collection<?> c);

	@Override
	boolean retainAll(Collection<?> c);
}
