package com.techinsiderpro.game;

import java.io.Serializable;

public class GridObject implements Serializable
{
	private Position position;
	private Direction direction;

	public GridObject(Position position, Direction direction)
	{
		this.position = position;
		this.direction = direction;
	}

	public Position getPosition()
	{
		return position;
	}

	public void setPosition(Position position)
	{
		this.position = position;
	}

	public Direction getDirection()
	{
		return direction;
	}

	public void setDirection(Direction direction)
	{
		this.direction = direction;
	}
}
