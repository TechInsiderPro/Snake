package com.techinsiderpro.net;

import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

public class BroadcastAddressFinder
{
	public static void main(String[] args) throws Exception
	{
		Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
		while (en.hasMoreElements())
		{
			NetworkInterface ni = en.nextElement();
			System.out.println(" Display Name = " + ni.getDisplayName());

			List<InterfaceAddress> list = ni.getInterfaceAddresses();
			Iterator<InterfaceAddress> it = list.iterator();

			while (it.hasNext())
			{
				InterfaceAddress ia = it.next();
				System.out.println(" Broadcast = " + ia.getBroadcast());
			}
		}
	}
}
