package com.github.bbf.scraft.client;

import com.github.bbf.scraft.events.AbstractFMLEventHandler;

import net.minecraftforge.common.MinecraftForge;

public class ClientEventHandlers extends AbstractFMLEventHandler {

	@Override
	protected void registerEventBus(Class<?> clz) {
		if (clz.getName().startsWith("com.github.bbf.scraft.server") == false) {
			MinecraftForge.EVENT_BUS.register(clz);
		}
	}

}
