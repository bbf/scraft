package com.github.bbf.scraft.blocks;

import java.util.UUID;

import com.github.bbf.scraft.Scraft;
import com.github.bbf.scraft.server.ScraftContextManager;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;

public class TileEntityNexus extends TileEntity implements ITickable {

	private static final String TAG_NEXUS_OWNER = "nexus_owner";

	private UUID owner;
	private World tileWorld;

	public TileEntityNexus() {
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		if (owner != null) {
			getTileData().setUniqueId(TAG_NEXUS_OWNER, owner);
		} else {
			getTileData().removeTag(TAG_NEXUS_OWNER);
		}
		super.writeToNBT(compound);
		Scraft.getLogger().info("Saved NBT for Nexus [{}] [{}]", owner, compound);
		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		UUID tagId = getTileData().getUniqueId(TAG_NEXUS_OWNER);
		if (tagId.getLeastSignificantBits() != 0 || tagId.getMostSignificantBits() != 0) {
			owner = tagId;
		} else {
			Scraft.getLogger().info("No owner for this Nexus");
		}
		Scraft.getLogger().info("Loaded NBT for Nexus [{}] [{}]", owner, compound);
		setOwner(owner);
	}

	@Override
	protected void setWorldCreate(World worldIn) {
		super.setWorldCreate(worldIn);
		if (worldIn == null) {
			Scraft.getLogger().warn("Trying to set world to null");
			return;
		}
		tileWorld = worldIn;
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		Scraft.getLogger().info("getUpdatePacket()");
		return new SPacketUpdateTileEntity(pos, 0, getUpdateTag());
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		Scraft.getLogger().info("getUpdateTag()");
		return super.getUpdateTag();
	}

	@Override
	public void update() {
	}

	public String getUniqueId() {
		if (tileWorld == null) {
			throw new IllegalStateException("Cannot get unique id before world is set.");
		}
		final int dimension = tileWorld.provider.getDimension();
		return String.format("%d:%d:%d:%d", dimension, pos.getX(), pos.getY(), pos.getZ());
	}

	public void setOwner(UUID uniqueID) {

		Scraft.getLogger().info("setOwner UUID[{}] thread[{}] tileWorld[{}] isRemote[{}]", uniqueID,
				Thread.currentThread().getName(), tileWorld, tileWorld == null ? "<null>" : tileWorld.isRemote);

		if (tileWorld == null || tileWorld.isRemote) {
			Scraft.getLogger().info("UUID[{}] thread[{}] tileWorld[{}] isRemote[{}]", uniqueID,
					Thread.currentThread().getName(), tileWorld, tileWorld == null ? "<null>" : tileWorld.isRemote);
			return;
		}

		if ((uniqueID != null && uniqueID.equals(owner) == false)
				|| (owner != null && owner.equals(uniqueID) == false)) {
			owner = uniqueID;
			this.markDirty();
		}

		ScraftContextManager.get().notifyNexusChange(this);
	}

	public UUID getOwner() {
		return owner;
	}
}
