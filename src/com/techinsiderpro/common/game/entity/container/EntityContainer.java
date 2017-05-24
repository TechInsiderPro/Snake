package com.techinsiderpro.common.game.entity.container;

import com.techinsiderpro.common.game.entity.component.PositionComponent;
import com.techinsiderpro.common.game.entity.Entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

public interface EntityContainer extends Collection<Entity>, Serializable
{
	void setPositionTo(Entity entity);

	Entity getGridObjectAt(PositionComponent positionComponent);

	void removeGridObjectAt(PositionComponent positionComponent);

	boolean isEmptyAt(PositionComponent positionComponent);

	int getWidth();

	int getHeight();

	boolean isWithinBounds(PositionComponent positionComponent);

	@Override
	void clear();

	@Override
	int size();

	@Override
	boolean isEmpty();

	@Override
	boolean contains(Object o);

	@Override
	Iterator<Entity> iterator();

	@Override
	Object[] toArray();

	@Override
	<T> T[] toArray(T[] a);

	@Override
	boolean add(Entity entity);

	@Override
	boolean remove(Object o);

	@Override
	boolean containsAll(Collection<?> c);

	@Override
	boolean addAll(Collection<? extends Entity> c);

	@Override
	boolean removeAll(Collection<?> c);

	@Override
	boolean retainAll(Collection<?> c);
}
