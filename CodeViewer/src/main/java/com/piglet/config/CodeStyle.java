package com.piglet.config;

public enum CodeStyle {
	DEFAULT("shCoreDefault.css"), DJANGO("shCoreDjango.css"), ECLIPSE("shCoreEclipse.css"), EMACS("shCoreEmacs.css"), FADETOGREY("shCoreFadeToGrey.css"), MDULTRA(
			"shCoreMDUltra.css"), MIDNIGHT("shCoreMidnight.css"), RDARK("shCoreRDark.css");

	private String filename;

	private CodeStyle(String filename) {
		this.filename = filename;
	}

	public String getFileName() {
		return filename;
	}

}
