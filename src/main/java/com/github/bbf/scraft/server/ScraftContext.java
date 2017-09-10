package com.github.bbf.scraft.server;

import java.io.File;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.eclipsesource.v8.NodeJS;
import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Function;
import com.eclipsesource.v8.V8Object;
import com.eclipsesource.v8.V8ScriptExecutionException;
import com.github.bbf.scraft.Scraft;

public class ScraftContext implements Runnable {

	private static final String ON_EVENT_FUNCTION = "onEvent";
	private final Thread executableThread;
	private final File bios;
	private final Queue<ScraftContextEvent> events;

	private NodeJS nodeJS;
	private boolean keepRunning;
	private V8Function onEvent;
	private UUID playerId;

	public ScraftContext(File bios, UUID playerId) {
		this.bios = bios;
		this.playerId = playerId;
		events = new ConcurrentLinkedQueue<>();
		executableThread = new Thread(this);
		// executableThread.setDaemon(true);
		keepRunning = true;
		executableThread.start();
	}

	@Override
	public void run() {
		nodeJS = NodeJS.createNodeJS(bios);
		V8 runtime = nodeJS.getRuntime();

		ScraftContextUtils.inject(runtime);

		while (keepRunning) {
			if (nodeJS.isRunning()) {
				nodeJS.handleMessage();
			} else {
				Scraft.getLogger().error("nodeJS is not running???");
				keepRunning = false;
			}

			ScraftContextEvent event = events.poll();
			if (event != null) {
				onEvent(runtime, event);
			}

		}

		Scraft.getLogger().debug("Closing runtime.");

		if (onEvent != null) {
			onEvent.release();
		}
		if (nodeJS != null) {
			nodeJS.release();
		}
	}

	private void onEvent(V8 runtime, ScraftContextEvent event) {
		V8Array args = null;
		try {
			if (onEvent == null) {
				V8Object onEventObj = runtime.getObject(ON_EVENT_FUNCTION);
				if (onEventObj != null && onEventObj instanceof V8Function) {
					onEvent = (V8Function) onEventObj;
				} else {
					StringBuilder sb = new StringBuilder(
							"Could not load BIOS function: onEvent\nFound these keys on global context:\n");
					for (String key : runtime.getKeys()) {
						sb.append(key);
						sb.append("\n");
					}
					final String error = sb.toString();
					Scraft.getLogger().warn(error);
					throw new IllegalStateException(error);
				}

			}

			args = event.getArgs(runtime);
			onEvent.call(null, args);

		} catch (V8ScriptExecutionException e) {
			Scraft.getLogger().warn("Could not execute onEvent():\n{}", e);

		} finally {
			if (args != null) {
				args.release();
			}
		}
	}

	public void destroy() {
		keepRunning = false;
	}

	public void on(String event, String... eventArgs) {
		events.add(new ScraftContextEvent(event, eventArgs));
	}

	void testFinish() throws InterruptedException {
		Thread.sleep(1000);
		keepRunning = false;
		executableThread.join();
	}
}
