package com.github.bbf.scraft;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.Logger;

import com.eclipsesource.v8.V8;
import com.github.bbf.scraft.annotations.ModBlock;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
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
import net.minecraftforge.registries.IForgeRegistry;

@Mod(modid = ScraftMod.MODID, version = ScraftMod.VERSION)
public class ScraftMod {
	public static final String MODID = "scraft";
	public static final String VERSION = "1.0.0";

	private static V8 v8;
	private ASMDataTable asmData;
	private Logger logger;
	private List<Block> registeredBlocks;

	public ScraftMod() {
		MinecraftForge.EVENT_BUS.register(this);
		registeredBlocks = new ArrayList<>();
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		// TODO:
		// Setup Logging
		// Load config
		// Get version.properties
		// Setup metadata
		// Register blocks and items
		// Discover parts by using annotation search

		logger = event.getModLog();
		// File suggestedConfigurationFile =
		// event.getSuggestedConfigurationFile();
		// Properties versionProperties = event.getVersionProperties();
		// ModMetadata modMetadata = event.getModMetadata();
		asmData = event.getAsmData();
	}

	@SubscribeEvent
	public void registerBlocks(RegistryEvent.Register<Block> event) {
		IForgeRegistry<Block> blockRegistry = event.getRegistry();

		Set<ASMData> blocks = asmData.getAll(ModBlock.class.getName());
		for (ASMData block : blocks) {
			try {
				@SuppressWarnings("unchecked")
				Class<? extends Block> blockClass = (Class<? extends Block>) Class.forName(block.getClassName());
				Block blockInstance = blockClass.newInstance();
				blockRegistry.registerAll(blockInstance);
				registeredBlocks.add(blockInstance);
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				logger.info("Found ModBlock annotation, but could not register block: " + block.getClassName(), e);
			}
		}
	}

	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> itemRegistry = event.getRegistry();

		for (Block block : registeredBlocks) {
			ItemBlock itemBlock = new ItemBlock(block);
			itemBlock.setRegistryName(block.getRegistryName());
			itemRegistry.register(itemBlock);
		}
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		// TODO:
		// Register recipes / ore dictionary
		// Message other mods

		// some example code
		logger.info("############# SCRAFT:");

		v8 = V8.createV8Runtime();
		String result = v8.executeStringScript("'Hello World';");
		logger.info("############# V8 Result: {}", result);
	}

	@EventHandler
	public void pendingIMC(IMCEvent event) {

	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {

	}

	@EventHandler
	public void stop(FMLServerStoppedEvent event) {
		if (v8 != null) {
			v8.release(true);
			v8 = null;
		}
	}

	@EventHandler
	public void invalidFingerprint(FMLFingerprintViolationEvent event) {

	}

	@EventHandler
	public void serverAboutToStart(FMLServerAboutToStartEvent event) {

	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event) {
		// TODO:
		// Register commands
		// MinecraftServer server = event.getServer();

	}

	@EventHandler
	public void serverStarted(FMLServerStartedEvent event) {

	}

	@EventHandler
	public void serverStopping(FMLServerStoppingEvent event) {

	}

	@EventHandler
	public void serverStopped(FMLServerStoppedEvent event) {

	}

}
