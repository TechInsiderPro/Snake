package com.techinsiderpro.common.game;

import com.techinsiderpro.common.eventsys.Dispatcher;
import com.techinsiderpro.common.game.objects.GridObject;
import com.techinsiderpro.common.game.objects.containers.GridObjectContainer;
import com.techinsiderpro.common.game.objects.containers.ListGridObjectContainer;

import java.util.ArrayList;
import java.util.List;

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
