package com.techinsiderpro.client.event;

import com.techinsiderpro.common.event.Event;

public class KeyPressedEvent implements Event
{
	private int keyCode;

	public KeyPressedEvent(int keyCode)
	{
		this.keyCode = keyCode;
	}

	public int getKeyCode()
	{
		return keyCode;
	}

	public void setKeyCode(int keyCode)
	{
		this.keyCode = keyCode;
	}
}
