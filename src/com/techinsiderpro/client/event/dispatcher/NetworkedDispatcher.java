package com.techinsiderpro.client.event.dispatcher;

import com.techinsiderpro.common.event.Event;
import com.techinsiderpro.common.event.dispatcher.Dispatcher;
import com.techinsiderpro.common.net.Connection;

public class NetworkedDispatcher extends Dispatcher
{
	private Connection connection;

	public NetworkedDispatcher(Connection connection)
	{
		this.connection = connection;
	}

	@Override
	public void dispatch(Event event)
	{
		connection.write(event);
	}
}
