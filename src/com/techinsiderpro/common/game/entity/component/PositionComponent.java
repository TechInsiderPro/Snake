package com.techinsiderpro.common.game.entity.component;

public class PositionComponent implements Component
{
	private int x, y;

	public PositionComponent(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	@Override
	public boolean equals(Object obj)
	{
		return obj instanceof PositionComponent && this.y == ((PositionComponent) obj).y && this.x == ((PositionComponent) obj).x;
	}
}
