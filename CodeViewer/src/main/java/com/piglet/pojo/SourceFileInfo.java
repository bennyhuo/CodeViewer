package com.piglet.pojo;

import net.tsz.afinal.annotation.sqlite.Id;

import com.piglet.config.Lang;

public class SourceFileInfo {
	
//	private int id;
	
	@Id
	private String path;
	private String lang;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getLang() {
		return lang;
	}

	public Lang lang() {
		return Lang.valueOf(lang);
	}

	public void setLang(String lang) {
		this.lang = lang;
	}
}
