package com.techinsiderpro.common.game.entity.container;

import com.techinsiderpro.common.game.entity.Entity;
import com.techinsiderpro.common.game.entity.component.PositionComponent;

import java.util.Collection;
import java.util.Iterator;

public class ArrayEntityContainer implements EntityContainer
{
	private Entity[][] grid;

	public ArrayEntityContainer(int width, int height)
	{
		this.grid = new Entity[height][width];
	}

	@Override
	public void setPositionTo(Entity entity)
	{
		remove(entity.getComponent(PositionComponent.class));
		add(entity);
	}

	@Override
	public Entity getGridObjectAt(PositionComponent positionComponent)
	{
		return grid[positionComponent.getY()][positionComponent.getX()];
	}

	@Override
	public void removeGridObjectAt(PositionComponent positionComponent)
	{
		grid[positionComponent.getY()][positionComponent.getX()] = null;
	}

	@Override
	public boolean isEmptyAt(PositionComponent positionComponent)
	{
		return getGridObjectAt(positionComponent) == null;
	}

	@Override
	public void clear()
	{
		grid = new Entity[getHeight()][getWidth()];
	}

	@Override
	public int size()
	{
		return getWidth() * getHeight();
	}

	@Override
	public boolean isEmpty()
	{
		for (Entity[] row : grid)
		{
			for (Entity entity : row)
			{
				if (entity != null)
				{
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public boolean contains(Object o)
	{
		for (Entity[] row : grid)
		{
			for (Entity entity : row)
			{
				if (entity.equals(o))
				{
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public Iterator<Entity> iterator()
	{
		return new Iterator<Entity>()
		{
			private int c, r;

			@Override
			public boolean hasNext()
			{
				for (; r < getHeight(); r++)
				{
					for (; c < getWidth(); c++)
					{
						if (!isEmptyAt(new PositionComponent(c, r)))
						{
							return true;
						}
					}
				}

				return false;
			}

			@Override
			public Entity next()
			{
				for (; r < getHeight(); r++)
				{
					for (; c < getWidth(); c++)
					{
						if (!isEmptyAt(new PositionComponent(c, r)))
						{
							return getGridObjectAt(new PositionComponent(c, r));
						}
					}
				}

				return null;
			}

			@Override
			public void remove()
			{
				removeGridObjectAt(new PositionComponent(c, r));
			}
		};
	}

	@Override
	public Object[] toArray()
	{
		Object[] objectArray = new Object[size()];

		for (int r = 0; r < getHeight(); r++)
		{
			for (int c = 0; c < getWidth(); c++)
			{
				objectArray[r * getWidth() + c] = grid[r][c];
			}
		}

		return objectArray;
	}

	@Override
	public <T> T[] toArray(T[] a)
	{
		for (int r = 0; r < getHeight(); r++)
		{
			for (int c = 0; c < getWidth(); c++)
			{
				a[r * getWidth() + c] = (T) grid[r][c];
			}
		}

		return a;
	}

	@Override
	public boolean add(Entity entity)
	{
		if (isEmptyAt(entity.getComponent(PositionComponent.class)))
		{
			grid[entity.getComponent(PositionComponent.class).getY()][entity.getComponent(PositionComponent.class).getX()] = entity;
			return true;
		}

		return false;
	}

	@Override
	public boolean remove(Object o)
	{
		removeGridObjectAt(((Entity) o).getComponent(PositionComponent.class));
		return true;
	}

	@Override
	public boolean containsAll(Collection<?> c)
	{
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends Entity> c)
	{
		for (Entity entity : c)
		{
			add(entity);
		}

		return true;
	}

	@Override
	public boolean removeAll(Collection<?> c)
	{
		for (Object gridObject : c)
		{
			remove(gridObject);
		}

		return true;
	}

	@Override
	public boolean retainAll(Collection<?> collection)
	{
		for (int r = 0; r < getHeight(); r++)
		{
			for (int c = 0; c < getWidth(); c++)
			{
				if (collection.contains(getGridObjectAt(new PositionComponent(c, r))))
				{
					removeGridObjectAt(new PositionComponent(c, r));
				}
			}
		}

		return true;
	}

	@Override
	public int getWidth()
	{
		return grid[0].length;
	}

	@Override
	public int getHeight()
	{
		return grid.length;
	}

	@Override
	public boolean isWithinBounds(PositionComponent positionComponent)
	{
		return positionComponent.getX() < getWidth() && positionComponent.getX() >= 0 && positionComponent.getY() < getHeight() &&
		       positionComponent.getY() >= 0;
	}
}
