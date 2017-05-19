package com.techinsiderpro.common.game;

import java.io.Serializable;

public enum Direction implements Serializable
{
	UP(0, -1), DOWN(0, 1), LEFT(-1, 0), RIGHT(1, 0);

	private int xShift, yShift;

	Direction(int xShift, int yShift)
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

	public static Direction getOpposite(Direction direction)
	{
		if (direction == UP)
			return DOWN;
		else if (direction == DOWN)
			return UP;
		else if (direction == LEFT)
			return RIGHT;
		else
			return LEFT;
	}
}
