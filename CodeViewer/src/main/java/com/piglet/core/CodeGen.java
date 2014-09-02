package com.piglet.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;

import com.piglet.config.CodeStyle;
import com.piglet.config.Lang;

public class CodeGen {
	public static final String BASE_URL = "file:///android_asset/";
	public static final String MIME_TYPE = "text/html";
	public static final String DEFAULT_ENCODING = "utf-8";
	
	private static CodeGen gen;
	private String template;

	public static CodeGen getCodeGen(Context context) {
		if (gen == null) {
			gen = new CodeGen(context);
		}
		return gen;
	}

	private CodeGen(Context context) {
		try {
			InputStream is = context.getAssets().open("template");
			template = readFile(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String readFile(InputStream is) throws IOException {
		InputStreamReader isr = new InputStreamReader(is);
		char[] buffer = new char[1024];
		StringBuilder sb = new StringBuilder();
		int len;
		while ((len = isr.read(buffer)) != -1) {
			sb.append(new String(buffer, 0, len));
		}
		return sb.toString();
	}

	public String generate(String filepath, CodeStyle style, Lang lang) throws FileNotFoundException {
		File file = new File(filepath);
		if (file.exists() && file.canRead()) {
			String langType = lang.getFileName();
			String brushName = lang.getBrushName();
			String codeStyle = style.getFileName();
			String content = null;
			try {
				content = readFile(new FileInputStream(file));
			} catch (IOException e) {
				e.printStackTrace();
				content = "Error reading file.";
			}

			StringBuffer sb = new StringBuffer(template);
			easyReplace(sb, "#{ langtype }", langType);
			easyReplace(sb, "#{ codestyle }", codeStyle);
			easyReplace(sb, "#{ brushname }", brushName);
			easyReplace(sb, "#{ content }", content);
			return sb.toString();
		} else {
			throw new FileNotFoundException();
		}
	}

	private void easyReplace(StringBuffer target, String oldStr, String newStr) {
		int start = target.indexOf(oldStr);
		int end = start + oldStr.length();
		target.replace(start, end, newStr);
	}
}
