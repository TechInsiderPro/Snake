package com.techinsiderpro.common.game.objects.containers;

import com.techinsiderpro.common.game.Position;
import com.techinsiderpro.common.game.objects.GridObject;

import java.util.Collection;
import java.util.Iterator;

public class ArrayGridObjectContainer implements GridObjectContainer
{
	private GridObject[][] grid;

	public ArrayGridObjectContainer(int width, int height)
	{
		this.grid = new GridObject[height][width];
	}

	@Override
	public void setPositionTo(GridObject gridObject)
	{
		remove(gridObject.getPosition());
		add(gridObject);
	}

	@Override
	public GridObject getGridObjectAt(Position position)
	{
		return grid[position.getY()][position.getX()];
	}

	@Override
	public void removeGridObjectAt(Position position)
	{
		grid[position.getY()][position.getX()] = null;
	}

	@Override
	public boolean isEmptyAt(Position position)
	{
		return getGridObjectAt(position) == null;
	}

	@Override
	public void clear()
	{
		grid = new GridObject[getHeight()][getWidth()];
	}

	@Override
	public int size()
	{
		return getWidth() * getHeight();
	}

	@Override
	public boolean isEmpty()
	{
		for (GridObject[] row : grid)
		{
			for (GridObject gridObject : row)
			{
				if (gridObject != null)
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
		for (GridObject[] row : grid)
		{
			for (GridObject gridObject : row)
			{
				if (gridObject.equals(o))
				{
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public Iterator<GridObject> iterator()
	{
		return new Iterator<GridObject>()
		{
			private int c, r;

			@Override
			public boolean hasNext()
			{
				for (; r < getHeight(); r++)
				{
					for (; c < getWidth(); c++)
					{
						if (!isEmptyAt(new Position(c, r)))
						{
							return true;
						}
					}
				}

				return false;
			}

			@Override
			public GridObject next()
			{
				for (; r < getHeight(); r++)
				{
					for (; c < getWidth(); c++)
					{
						if (!isEmptyAt(new Position(c, r)))
						{
							return getGridObjectAt(new Position(c, r));
						}
					}
				}

				return null;
			}

			@Override
			public void remove()
			{
				removeGridObjectAt(new Position(c, r));
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
	public boolean add(GridObject gridObject)
	{
		if (isEmptyAt(gridObject.getPosition()))
		{
			grid[gridObject.getPosition().getY()][gridObject.getPosition().getX()] = gridObject;
			return true;
		}

		return false;
	}

	@Override
	public boolean remove(Object o)
	{
		removeGridObjectAt(((GridObject) o).getPosition());
		return true;
	}

	@Override
	public boolean containsAll(Collection<?> c)
	{
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends GridObject> c)
	{
		for (GridObject gridObject : c)
		{
			add(gridObject);
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
				if (collection.contains(getGridObjectAt(new Position(c, r))))
				{
					removeGridObjectAt(new Position(c, r));
				}
			}
		}

		return true;
	}

	public int getWidth()
	{
		return grid[0].length;
	}

	public int getHeight()
	{
		return grid.length;
	}
}
