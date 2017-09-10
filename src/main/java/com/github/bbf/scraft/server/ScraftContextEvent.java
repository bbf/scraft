package com.github.bbf.scraft.server;

import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Array;

public class ScraftContextEvent {

	private final String name;
	private final String[] args;

	public ScraftContextEvent(String eventName, String... args) {
		this.name = eventName;
		this.args = args;
	}

	public String getEventName() {
		return name;
	}

	public V8Array getArgs(V8 runtime) {
		V8Array executeArgs = new V8Array(runtime);
		executeArgs.push(name);
		for (String arg : args) {
			executeArgs.push(arg);
		}
		if (executeArgs.length() == 1) {
			executeArgs.push("");
		}
		return executeArgs;
	}

}
