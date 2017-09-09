package com.github.bbf.scraft;

import com.github.bbf.scraft.events.AbstractFMLEventHandler;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.InstanceFactory;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLFingerprintViolationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms.IMCEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;

@Mod(modid = ScraftMod.MODID, version = ScraftMod.VERSION)
public final class ScraftMod {

	public static final String MODID = "scraft";
	public static final String VERSION = "1.0.0";

	private static final ScraftMod THIS = new ScraftMod();

	@SidedProxy(clientSide = "com.github.bbf.scraft.client.ClientEventHandlers", serverSide = "com.github.bbf.scraft.server.ServerEventHandlers")
	private static AbstractFMLEventHandler handlers;

	@InstanceFactory
	public static ScraftMod getMod() {
		return THIS;
	}

	/**
	 * Private constructor since we want to handle on our own this object
	 * life-cycle.
	 */
	private ScraftMod() {
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		Scraft.preInit(event);
		if (handlers == null) {
			throw new IllegalStateException("Forge should have injected the proxy for EventHandlers, but it didn't.");
		}
		handlers.preInit(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		handlers.init(event);
	}

	@EventHandler
	public void pendingIMC(IMCEvent event) {
		handlers.pendingIMC(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		handlers.postInit(event);
	}

	@EventHandler
	public void stop(FMLServerStoppedEvent event) {
		handlers.stop(event);
	}

	@EventHandler
	public void invalidFingerprint(FMLFingerprintViolationEvent event) {
		handlers.invalidFingerprint(event);
	}

	@EventHandler
	public void serverAboutToStart(FMLServerAboutToStartEvent event) {
		handlers.serverAboutToStart(event);
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event) {
		handlers.serverStarting(event);
	}

	@EventHandler
	public void serverStarted(FMLServerStartedEvent event) {
		handlers.serverStarted(event);
	}

	@EventHandler
	public void serverStopping(FMLServerStoppingEvent event) {
		handlers.serverStopping(event);
	}

	@EventHandler
	public void serverStopped(FMLServerStoppedEvent event) {
		handlers.serverStopped(event);
	}
}
