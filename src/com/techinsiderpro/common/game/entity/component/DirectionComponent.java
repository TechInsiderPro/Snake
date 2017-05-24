package com.techinsiderpro.common.game.entity.component;

public enum DirectionComponent implements Component
{
	UP(0, -1),
	DOWN(0, 1),
	LEFT(-1, 0),
	RIGHT(1, 0),
	NONE(0, 0);

	private int xShift, yShift;

	DirectionComponent(int xShift, int yShift)
	{
		this.xShift = xShift;
		this.yShift = yShift;
	}

	public int getxShift()
	{
		return xShift;
	}

	public int getyShift()
	{
		return yShift;
	}

	public static DirectionComponent getOpposite(DirectionComponent directionComponent)
	{
		if (directionComponent == UP)
		{
			return DOWN;
		}
		else if (directionComponent == DOWN)
		{
			return UP;
		}
		else if (directionComponent == LEFT)
		{
			return RIGHT;
		}
		else
		{
			return LEFT;
		}
	}
}
