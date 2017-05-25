package com.techinsiderpro.common.ui;

import com.techinsiderpro.common.game.entity.component.PositionComponent;
import com.techinsiderpro.common.game.entity.container.EntityContainer;

import javax.swing.*;
import java.awt.*;

public class EntityContainerPanel extends JPanel
{
	private EntityContainer entityContainer;

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		if (entityContainer != null)
		{
			double xScale = (double) getWidth() / entityContainer.getWidth(), yScale =
					(double) getHeight() / entityContainer.getHeight();


			for (int r = 0; r < entityContainer.getHeight(); r++)
			{
				for (int c = 0; c < entityContainer.getWidth(); c++)
				{
					if (!entityContainer.isEmptyAt(new PositionComponent(c, r)))
					{
						g.setColor(Color.red);
						g.fillRect((int) (c * xScale), (int) (r * yScale), (int) xScale, (int) yScale);
					}
					else
					{
						g.setColor(Color.black);
						g.drawRect((int) (c * xScale), (int) (r * yScale), (int) xScale, (int) yScale);
					}
				}
			}
		}
	}

	public void setEntityContainer(EntityContainer entityContainer)
	{
		this.entityContainer = entityContainer;
	}
}
