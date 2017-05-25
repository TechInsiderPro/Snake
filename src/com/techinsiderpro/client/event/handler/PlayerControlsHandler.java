package com.techinsiderpro.client.event.handler;

import com.techinsiderpro.client.event.KeyPressedEvent;
import com.techinsiderpro.common.event.DirectionChangeRequestEvent;
import com.techinsiderpro.common.event.dispatcher.Dispatcher;
import com.techinsiderpro.common.event.handler.Handler;
import com.techinsiderpro.common.game.entity.Entity;
import com.techinsiderpro.common.game.entity.component.DirectionComponent;

import javax.swing.*;

public class PlayerControlsHandler
{
	private int upKey, downKey, leftKey, rightKey;
	private Dispatcher dispatcher;
	private Entity playerEntity;

	public PlayerControlsHandler(int upKey, int downKey, int leftKey, int rightKey,
	                             Dispatcher dispatcher)
	{
		this.upKey = upKey;
		this.downKey = downKey;
		this.leftKey = leftKey;
		this.rightKey = rightKey;
		this.dispatcher = dispatcher;
	}

	@Handler(eventType = KeyPressedEvent.class)
	public void handleKeyPressedEvent(KeyPressedEvent keyPressedEvent)
	{
		if (playerEntity != null)
		{
			if (keyPressedEvent.getKeyCode() == upKey)
			{
				dispatcher.dispatch(new DirectionChangeRequestEvent(DirectionComponent.UP, playerEntity));
			}
			else if (keyPressedEvent.getKeyCode() == downKey)
			{
				dispatcher.dispatch(new DirectionChangeRequestEvent(DirectionComponent.DOWN, playerEntity));
			}
			else if (keyPressedEvent.getKeyCode() == leftKey)
			{
				dispatcher.dispatch(new DirectionChangeRequestEvent(DirectionComponent.LEFT, playerEntity));
			}
			else if (keyPressedEvent.getKeyCode() == rightKey)
			{
				dispatcher.dispatch(new DirectionChangeRequestEvent(DirectionComponent.RIGHT, playerEntity));
			}
		}
	}

	public void setPlayerEntity(Entity entity)
	{
		playerEntity = entity;
	}
}
