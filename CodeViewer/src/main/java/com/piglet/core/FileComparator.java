package com.piglet.core;

import java.io.File;
import java.util.Comparator;

public class FileComparator implements Comparator<File> {

	public int compare(File file1, File file2) {
		String name0=file1.getName();
		String name1=file2.getName();
		return name0.compareTo(name1);
	}
}
