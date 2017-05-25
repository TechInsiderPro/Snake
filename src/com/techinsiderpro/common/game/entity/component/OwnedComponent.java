package com.techinsiderpro.common.game.entity.component;

import java.net.InetAddress;

public class OwnedComponent implements Component
{
	private InetAddress ownerAddress;

	public OwnedComponent(InetAddress ownerAddress)
	{
		this.ownerAddress = ownerAddress;
	}

	public InetAddress getOwnerAddress()
	{
		return ownerAddress;
	}
}
