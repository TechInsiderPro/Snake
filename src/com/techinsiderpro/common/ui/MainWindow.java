package com.techinsiderpro.common.ui;

import javax.swing.*;

public class MainWindow extends JFrame
{
	public MainWindow(int width, int height)
	{
		super();

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(width, height);
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
