package com.github.bbf.scraft.resources;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class ResourceUtilsTest {

	private static final String BIOS_RESOURCE = "/bios.js";

	@Test
	public void changeTempPath() throws IOException {
		ResourceUtils.tempPath = null;
		File tmp = File.createTempFile("test", ".tmp");
		tmp.deleteOnExit();
		ResourceUtils.setTempPath(tmp.getParentFile().getAbsolutePath());
		assertNotNull(ResourceUtils.tempPath);
	}
	
	@Test
	public void test() {
		File file = ResourceUtils.getTempFileFromJarResources(BIOS_RESOURCE);
		assertNotNull(file);
		System.out.println(file);
	}

}
