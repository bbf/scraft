package com.github.bbf.scraft.blocks;

import java.util.UUID;

import com.github.bbf.scraft.Scraft;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityNexus extends TileEntity implements ITickable {

	public UUID uniq;

	protected TileEntityNexus() {
		uniq = UUID.randomUUID();

	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		Scraft.getLogger().debug("TileEntityNexus.writeToNBT() uniq[{}]", uniq);
		super.writeToNBT(compound);
		compound.setUniqueId("uniq", uniq);
		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		Scraft.getLogger().debug("TileEntityNexus.readFromNBT() uniq[{}]", uniq);
		super.readFromNBT(compound);
		this.uniq = compound.getUniqueId("uniq");
	}

	// private int tickCount = 0;

	@Override
	public void update() {
		// if (tickCount % 20 == 0) {
		// System.out.println("Tick-tock");
		// }
		// tickCount++;
	}
}
