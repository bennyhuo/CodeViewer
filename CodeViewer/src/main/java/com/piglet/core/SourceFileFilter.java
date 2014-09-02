package com.piglet.core;

import java.io.File;
import java.io.FileFilter;

public class SourceFileFilter implements FileFilter {
	public boolean accept(File pathname) {
		return pathname.isFile();
	}
}
