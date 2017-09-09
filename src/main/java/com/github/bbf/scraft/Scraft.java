package com.github.bbf.scraft;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.spi.ExtendedLogger;

import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public final class Scraft {
	private Scraft() {
	}

	private static ASMDataTable asmDataTable;
	private static final Logger logger;
	static {
		// TODO figure out what condition we can lookup from GradleStart
		if (true) {
			logger = LogManager.getLogger("#########|" + ScraftMod.MODID + "|#########");
		}
	}

	static void preInit(FMLPreInitializationEvent event) {
		Scraft.asmDataTable = event.getAsmData();
	}

	public static ASMDataTable getASMDataTable() {
		return asmDataTable;
	}

	public static Logger getLogger() {
		return logger;
	}

}
