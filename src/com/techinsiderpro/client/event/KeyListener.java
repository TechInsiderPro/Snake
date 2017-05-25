package com.techinsiderpro.client.event;

import com.techinsiderpro.common.event.dispatcher.Dispatcher;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyListener extends KeyAdapter
{
	private Dispatcher dispatcher;

	public KeyListener(Dispatcher dispatcher)
	{
		this.dispatcher = dispatcher;
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		super.keyPressed(e);

		dispatcher.dispatch(new KeyPressedEvent(KeyStroke.getKeyStroke(e.getKeyChar(), 0).getKeyCode()));
	}
}
