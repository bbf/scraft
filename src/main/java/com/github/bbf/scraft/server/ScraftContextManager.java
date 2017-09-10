package com.github.bbf.scraft.server;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.github.bbf.scraft.Scraft;
import com.github.bbf.scraft.blocks.TileEntityNexus;
import com.github.bbf.scraft.resources.ResourceUtils;

public final class ScraftContextManager {

	private static final ScraftContextManager INSTANCE = new ScraftContextManager();

	private final Map<UUID, ScraftContext> contextByPlayerId;
	private final Map<String, UUID> playerByNexusId;
	private final File bios;

	private ScraftContextManager() {
		contextByPlayerId = new HashMap<>();
		playerByNexusId = new HashMap<>();
		bios = setupBios();
	}

	public static ScraftContextManager get() {
		return INSTANCE;
	}

	private File setupBios() {
		Path tempPath = FileSystems.getDefault().getPath("resource-temp");
		try {
			Files.createDirectories(tempPath);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}

		ResourceUtils.setTempPath(tempPath.toString());
		return ResourceUtils.getTempFileFromJarResources("/bios.js");
	}

	private ScraftContext createInitialContextForPlayer(UUID playerId) {
		return new ScraftContext(bios, playerId);
	}

	private void destroyContextForPlayer(UUID playerId) {
		ScraftContext context = contextByPlayerId.get(playerId);
		contextByPlayerId.remove(playerId);
		context.destroy();
	}

	public ScraftContext getContextForPlayer(UUID playerId) {
		if (contextByPlayerId.containsKey(playerId)) {
			return contextByPlayerId.get(playerId);
		}

		final ScraftContext result = createInitialContextForPlayer(playerId);
		contextByPlayerId.put(playerId, result);
		return result;
	}

	public void notifyNexusChange(TileEntityNexus nexus) {
		String nexusId = nexus.getUniqueId();
		UUID nexusOwner = nexus.getOwner();

		if (nexusOwner == null) {
			Scraft.getLogger().info("Ignoring unowned Nexus");
			return;
		}

		if (playerByNexusId.containsKey(nexusId)) {
			UUID orgPlayer = playerByNexusId.get(nexusId);
			if (orgPlayer.equals(nexusOwner) == false) {
				removeNexus(nexusId);
			} else {
				return;
			}
		}

		playerByNexusId.put(nexusId, nexusOwner);

		ScraftContext context = getContextForPlayer(nexusOwner);
		context.on("newNexus");

	}

	private void removeNexus(String nexusId) {
		UUID playerId = playerByNexusId.get(nexusId);
		playerByNexusId.remove(nexusId);

		if (playerByNexusId.values().contains(playerId) == false) {
			destroyContextForPlayer(playerId);
		}
	}

}
