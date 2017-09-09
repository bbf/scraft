package com.github.bbf.scraft.events;

import java.util.Set;

import org.apache.logging.log4j.Logger;

import com.github.bbf.scraft.Scraft;

import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.discovery.ASMDataTable.ASMData;
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
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public abstract class AbstractFMLEventHandler {

	public void preInit(FMLPreInitializationEvent event) {
		// TODO:
		// Setup Logging
		// Load config
		// Get version.properties
		// Setup metadata
		// Register blocks and items
		// Discover parts by using annotation search

		// File suggestedConfigurationFile =
		// event.getSuggestedConfigurationFile();
		// Properties versionProperties = event.getVersionProperties();
		// ModMetadata modMetadata = event.getModMetadata();

		ASMDataTable asmDataTable = Scraft.getASMDataTable();
		Logger logger = Scraft.getLogger();

		Set<ASMData> eventHandlers = asmDataTable.getAll(SubscribeEvent.class.getName());
		for (ASMData asmData : eventHandlers) {
			String handlerClassName = asmData.getClassName();
			if (handlerClassName.startsWith("com.github.bbf.scraft")) {
				try {
					Class<?> clz = Class.forName(handlerClassName);
					registerEventBus(clz);
				} catch (ClassNotFoundException e) {
					logger.error("Found @SubscribeEvent, but could not register class: " + handlerClassName, e);
				}
			}
		}
	}

	protected abstract void registerEventBus(Class<?> clz);

	public void init(FMLInitializationEvent event) {
		// TODO:
		// Register recipes / ore dictionary
		// Message other mods
		// GameRegistry.addShapedRecipe(name, group, output, params);
	}

	public void pendingIMC(IMCEvent event) {

	}

	public void postInit(FMLPostInitializationEvent event) {

	}

	public void stop(FMLServerStoppedEvent event) {
	}

	public void invalidFingerprint(FMLFingerprintViolationEvent event) {

	}

	public void serverAboutToStart(FMLServerAboutToStartEvent event) {

	}

	public void serverStarting(FMLServerStartingEvent event) {
		// TODO:
		// Register commands
		// MinecraftServer server = event.getServer();

	}

	public void serverStarted(FMLServerStartedEvent event) {

	}

	public void serverStopping(FMLServerStoppingEvent event) {

	}

	public void serverStopped(FMLServerStoppedEvent event) {

	}
}
