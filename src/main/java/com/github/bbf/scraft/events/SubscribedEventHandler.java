package com.github.bbf.scraft.events;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.github.bbf.scraft.Scraft;
import com.github.bbf.scraft.ScraftMod;
import com.github.bbf.scraft.blocks.Blocks;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public final class SubscribedEventHandler {

	private SubscribedEventHandler() {
	}

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		final List<Block> blocks = new ArrayList<>();
		final List<Class<? extends TileEntity>> tileEntityClasses = new ArrayList<>();

		final Field[] fields = Blocks.class.getDeclaredFields();

		for (final Field field : fields) {
			if (Block.class.isAssignableFrom(field.getType())) {
				try {
					Block block = (Block) field.get(Blocks.class);
					blocks.add(block);

					if (ITileEntityProvider.class.isAssignableFrom(block.getClass())) {
						ITileEntityProvider provider = (ITileEntityProvider) block;
						final TileEntity tileEntity = provider.createNewTileEntity(null, 0);
						tileEntityClasses.add(tileEntity.getClass());
					}

				} catch (IllegalArgumentException | IllegalAccessException e) {
					Scraft.getLogger().error("Could not retrieve Block from BlockRegistry: " + field.getName(), e);
				}

			}
		}
		Blocks.ALL_BLOCKS = blocks.toArray(new Block[0]);
		event.getRegistry().registerAll(Blocks.ALL_BLOCKS);

		for (Class<? extends TileEntity> tileEntityClass : tileEntityClasses) {
			final String id = String.format("%s:%s", ScraftMod.MODID, tileEntityClass.getSimpleName());
			Scraft.getLogger().debug("Registering TileEntity: {} | {}", id, tileEntityClass);
			GameRegistry.registerTileEntity(tileEntityClass, id);
		}

	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		final List<Item> itemBlocks = new ArrayList<>();

		for (final Block block : Blocks.ALL_BLOCKS) {
			// For now, register every block as an item (KISS)
			final ItemBlock itemBlock = new ItemBlock(block);
			itemBlock.setRegistryName(block.getRegistryName());
			itemBlocks.add(itemBlock);
		}

		Blocks.ALL_ITEM_BLOCKS = itemBlocks.toArray(new Item[0]);
		event.getRegistry().registerAll(Blocks.ALL_ITEM_BLOCKS);
	}
}
