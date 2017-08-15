package com.github.bbf.scraft.blocks;

import com.github.bbf.scraft.annotations.ModBlock;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

//@ModBlock
public class Nexus extends BlockContainer {

	final String name = "nexus";

	public Nexus() {
		super(Material.ROCK);
		setRegistryName(name);
		setUnlocalizedName(name);
		setCreativeTab(CreativeTabs.MISC);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return null;
	}
}
