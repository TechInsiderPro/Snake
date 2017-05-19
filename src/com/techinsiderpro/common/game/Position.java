package com.techinsiderpro.common.game;

import java.io.Serializable;

public class Position implements Serializable
{
	private int x, y;

	public Position(int x, int y)
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
		return obj instanceof Position && this.y == ((Position) obj).y && this.x == ((Position) obj).x;
	}
}
