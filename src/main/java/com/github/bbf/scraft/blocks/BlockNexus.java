package com.github.bbf.scraft.blocks;

import javax.annotation.Nullable;

import com.github.bbf.scraft.Scraft;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockNexus extends BlockContainer {

	final String name = "nexus";

	protected BlockNexus() {
		super(Material.ROCK);
		setRegistryName(name);
		setUnlocalizedName(name);
		setCreativeTab(CreativeTabs.MISC);
	}

	/**
	 * Called when the block is right clicked by a player.
	 */
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

		if (worldIn.isRemote) {
			return true;
		}

		Scraft.getLogger().info("BlockNexus.onBlockActivated()");

		TileEntity tileEntity = worldIn.getTileEntity(pos);
		if (tileEntity instanceof TileEntityNexus) {

			TileEntityNexus teNexus = (TileEntityNexus) tileEntity;
			// teNexus.
			// playerIn.displayGuiCommandBlock((TileEntityCommandBlock)
			// tileentity);
			return true;
		}

		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		Scraft.getLogger().info("BlockNexus.createNewTileEntity()");
		return new TileEntityNexus();
	}
	
	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		super.onBlockAdded(worldIn, pos, state);
		Scraft.getLogger().info("BlockNexus.onBlockAdded()");
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		super.breakBlock(worldIn, pos, state);
		Scraft.getLogger().debug("BlockNexus.breakBlock()");
		// TODO
	}

	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state,
			@Nullable TileEntity te, ItemStack stack) {
		super.harvestBlock(worldIn, player, pos, state, te, stack);
		Scraft.getLogger().debug("BlockNexus.harvestBlock()");
	}

	public boolean eventReceived(IBlockState state, World worldIn, BlockPos pos, int id, int param) {
		Scraft.getLogger().debug("BlockNexus.eventReceived()");
		return super.eventReceived(state, worldIn, pos, id, param);
	}

}
