package com.github.bbf.scraft.server;

import com.github.bbf.scraft.events.AbstractFMLEventHandler;

import net.minecraftforge.common.MinecraftForge;

public class ServerEventHandlers extends AbstractFMLEventHandler {

	@Override
	protected void registerEventBus(Class<?> clz) {
		if( clz.getName().startsWith("com.github.bbf.scraft.client") == false) {
			MinecraftForge.EVENT_BUS.register(clz);
		}
	}
	
}
