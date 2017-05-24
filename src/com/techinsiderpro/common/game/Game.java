package com.techinsiderpro.common.game;

import com.techinsiderpro.common.event.dispatcher.Dispatcher;
import com.techinsiderpro.common.game.entity.container.EntityContainer;
import com.techinsiderpro.common.game.entity.container.ListEntityContainer;

import java.io.Serializable;

public class Game implements Serializable
{

	private Dispatcher dispatcher;
	private EntityContainer entityContainer;

	public Game(int width, int height)
	{
		dispatcher = new Dispatcher();
		entityContainer = new ListEntityContainer(width, height);
	}

	public Dispatcher getDispatcher()
	{
		return dispatcher;
	}

	public EntityContainer getEntityContainer()
	{
		return entityContainer;
	}
}
