package com.techinsiderpro.common.game;

import com.techinsiderpro.common.events.dispatchers.Dispatcher;
import com.techinsiderpro.common.game.objects.containers.GridObjectContainer;
import com.techinsiderpro.common.game.objects.containers.ListGridObjectContainer;

public class Game
{

	private Dispatcher dispatcher;
	private GridObjectContainer gridObjectContainer;

	public Game(int width, int height)
	{
		dispatcher = new Dispatcher();
		gridObjectContainer = new ListGridObjectContainer();
	}

	public Dispatcher getDispatcher()
	{
		return dispatcher;
	}

	public GridObjectContainer getGridObjectContainer()
	{
		return gridObjectContainer;
	}
}
