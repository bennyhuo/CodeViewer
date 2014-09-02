package com.piglet.core;

import java.io.File;
import java.io.FileFilter;

public class DirectoryFilter implements FileFilter {

	public boolean accept(File file) {
		return file.isDirectory()&&!file.isHidden();
	}
}
