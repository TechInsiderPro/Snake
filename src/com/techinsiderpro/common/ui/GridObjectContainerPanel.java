package com.techinsiderpro.common.ui;

import com.techinsiderpro.common.game.Position;
import com.techinsiderpro.common.game.objects.containers.GridObjectContainer;

import javax.swing.*;
import java.awt.*;

public class GridObjectContainerPanel extends JPanel
{
	private GridObjectContainer gridObjectContainer;

	public GridObjectContainerPanel(GridObjectContainer gridObjectContainer)
	{
		this.gridObjectContainer = gridObjectContainer;
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		double xScale = getWidth() / gridObjectContainer.getWidth(), yScale = getHeight() / gridObjectContainer.getHeight();

		for (int r = 0; r < gridObjectContainer.getHeight(); r++)
		{
			for (int c = 0; c < gridObjectContainer.getWidth(); c++)
			{
				if (!gridObjectContainer.isEmptyAt(new Position(c, r)))
					g.fillRect((int) (c * xScale), (int) (r * yScale), (int) xScale, (int) yScale);
			}
		}
	}
}
