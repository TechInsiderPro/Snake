package com.techinsiderpro.common.game.entity;

import com.techinsiderpro.common.game.entity.component.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.UUID;

public class Entity implements Serializable
{
	private UUID uuid;
	private HashMap<Class<? extends Component>, Component> componentMap;

	public Entity()
	{
		this.uuid = UUID.randomUUID();
		this.componentMap = new LinkedHashMap<>();
	}

	public <T extends Component> T getComponent(Class<T> componentClass)
	{
		return (T)componentMap.get(componentClass);
	}

	public Component removeComponent(Class<? extends Component> componentClass)
	{
		return componentMap.remove(componentClass);
	}

	public Component putComponent(Component component)
	{
		return componentMap.put(component.getClass(), component);
	}

	public boolean equals(Object object)
	{
		return object instanceof Entity && ((Entity) object).uuid.equals(this.uuid);
	}
}
