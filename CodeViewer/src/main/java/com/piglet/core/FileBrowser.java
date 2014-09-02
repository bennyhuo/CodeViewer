package com.piglet.core;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import android.content.Context;
import android.os.Environment;

import com.piglet.data.Constant;

public class FileBrowser {
	private static FileBrowser instance;
	private Context context;
	private File currentDir;

	private static HashMap<String, String> fileTypeMap;

	static {
		fileTypeMap = new HashMap<String, String>();
		fileTypeMap.put("java", "1");
		fileTypeMap.put("cpp", "2");
		fileTypeMap.put("h", "3");
		fileTypeMap.put("hpp", "4");
		fileTypeMap.put("cs", "5");
	}

	private FileBrowser(Context context) {
		this.context = context;
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			this.currentDir = Environment.getExternalStorageDirectory();
		} else {
			this.currentDir = Environment.getRootDirectory();
		}
	}

	public void setCurrentDir(String dir) {
		File f = new File(dir);
		if (f.exists() && f.isDirectory()) {
			this.currentDir = f;
		} else {
			throw new RuntimeException();
		}
	}

	// not thread safe.
	public static FileBrowser getFileBrowser(Context context) {
		if (instance == null) {
			instance = new FileBrowser(context);
		}
		return instance;
	}

	public ArrayList<HashMap<String, String>> goToRoot() {
		return getChildNames(Environment.getRootDirectory());
	}

	public ArrayList<HashMap<String, String>> goToSdCard() {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			return getChildNames(Environment.getExternalStorageDirectory());
		} else {
			return new ArrayList<HashMap<String, String>>(0);
		}
	}

	public ArrayList<HashMap<String, String>> goToDir(String filename) {
		File f = new File(filename);
		if (f.exists() && f.isDirectory()) {
			return getChildNames(f);
		} else {
			return new ArrayList<HashMap<String, String>>(0);
		}
	}

	public ArrayList<HashMap<String, String>> goToChildDir(String filename) {
		File f = new File(currentDir.getAbsolutePath() + File.separator + filename);
		if (f.exists() && f.isDirectory()) {
			return getChildNames(f);
		} else {
			return new ArrayList<HashMap<String, String>>(0);
		}
	}

	public ArrayList<HashMap<String, String>> goToParent() {
		if (currentDir.getParentFile() != null) {
			return getChildNames(currentDir.getParentFile());
		} else {
			return new ArrayList<HashMap<String, String>>(0);
		}
	}

	// 获取子文件
	private ArrayList<HashMap<String, String>> getChildNames(File file) {
		ArrayList<HashMap<String, String>> childList = new ArrayList<HashMap<String, String>>();
		this.currentDir = file;

		File[] childDirs = file.listFiles(new DirectoryFilter());
		File[] childFiles = file.listFiles(new SourceFileFilter());
		Arrays.sort(childDirs, new FileComparator());
		Arrays.sort(childFiles, new FileComparator());

		// ..
		if (currentDir.getParentFile() != null) {
			HashMap<String, String> parentDirInfo = new HashMap<String, String>();
			parentDirInfo.put(Constant.FileTypeKey.FILENAME, "上一级目录");
			parentDirInfo.put(Constant.FileTypeKey.FILETYPE, "-2");
			parentDirInfo.put(Constant.FileTypeKey.FILEINFO, "");
			childList.add(parentDirInfo);
		}

		for (File fileTemp : childDirs) {
			HashMap<String, String> childInfo = new HashMap<String, String>();
			childInfo.put(Constant.FileTypeKey.FILENAME, fileTemp.getName());
			childInfo.put(Constant.FileTypeKey.FILETYPE, "0");
			childInfo.put(Constant.FileTypeKey.FILEINFO, String.valueOf(fileTemp.getUsableSpace()));
			childList.add(childInfo);
		}
		for (File fileTemp : childFiles) {
			HashMap<String, String> childInfo = new HashMap<String, String>();

			String filename = fileTemp.getName();
			childInfo.put(Constant.FileTypeKey.FILENAME, filename);

			String endix = filename.substring(filename.lastIndexOf('.') + 1);
			String type = fileTypeMap.get(endix);
			childInfo.put(Constant.FileTypeKey.FILETYPE, type == null ? "-1" : type);
			childInfo.put(Constant.FileTypeKey.FILEINFO, String.valueOf(fileTemp.getUsableSpace()));
			childList.add(childInfo);
		}

		return childList;
	}

	public String getFullPath(String filename) {
		return currentDir.getAbsolutePath() + File.separator + filename;
	}

}
