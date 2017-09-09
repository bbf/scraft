package com.github.bbf.scraft.resources;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;

public abstract class ResourceUtils {

	static Class<? extends Object> clz = ResourceUtils.class;
	private static Map<String, File> fileCache = new HashMap<>();
	static File tempPath;

	public static void setTempPath(String path) {
		File tmp = new File(path);
		if (tmp.exists() && tmp.isDirectory() && tmp.canWrite()) {
			tempPath = tmp;
		}
	}

	public static File getTempFileFromJarResources(String resourcePath) {

		File cachedFile = fileCache.get(resourcePath);
		if (cachedFile != null) {
			return cachedFile;
		}

		InputStream stream = clz.getResourceAsStream(resourcePath);
		if (stream == null) {
			return null;
		}

		File temp;
		try {
			String resourceName = new File(resourcePath).getName();
			if (tempPath != null) {
				temp = File.createTempFile(resourceName, ".tmp", tempPath);
			} else {
				temp = File.createTempFile(resourceName, ".tmp");
			}
			temp.deleteOnExit();
			FileWriter fileWriter = new FileWriter(temp);
			IOUtils.copy(stream, fileWriter, StandardCharsets.UTF_8);
			stream.close();
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		fileCache.put(resourcePath, temp);

		return temp;
	}
}
