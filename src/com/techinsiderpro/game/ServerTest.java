package com.techinsiderpro.game;

public class ServerTest
{
	public static void main(String[] args)
	{
		Game game = new Game();

		GridObject gridObject = new GridObject(new Position(1, 1), Direction.DOWN);


		while (true)
		{
			try
			{
				Thread.sleep(500);

				game.send(gridObject);

				System.out.println(gridObject.getDirection());
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
}
