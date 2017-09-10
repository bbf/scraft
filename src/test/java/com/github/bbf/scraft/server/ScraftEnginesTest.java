package com.github.bbf.scraft.server;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import org.junit.Test;

public class ScraftEnginesTest {

	private static final UUID playerId = UUID.randomUUID();

	@Test
	public void singletonReturnsNonNull() {
		ScraftContextManager engines = ScraftContextManager.get();
		assertNotNull(engines);
	}

	@Test
	public void returnsContextForPlayer() {
		ScraftContextManager engines = ScraftContextManager.get();
		assertNotNull(engines);

		ScraftContext context = engines.getContextForPlayer(playerId);
		assertNotNull(context);
	}

	@Test
	public void receivesNewNexusEvent() throws IOException, InterruptedException {
		ScraftContextManager engines = ScraftContextManager.get();
		assertNotNull(engines);

		ScraftContext nodeJS = engines.getContextForPlayer(playerId);
		assertNotNull(nodeJS);

		nodeJS.on("newNexus");
		nodeJS.testFinish();

		System.out.println("Ran test: " + new Date());
	}

}
