package com.techinsiderpro.common.ui;

import javax.swing.*;

public class Window extends JFrame
{
	public Window(int width, int height)
	{
		super();

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(width, height);
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
