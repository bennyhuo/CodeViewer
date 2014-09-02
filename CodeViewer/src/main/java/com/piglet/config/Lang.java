package com.piglet.config;

import java.util.Locale;

public enum Lang {
	APPLESCRIPT("shBrushAppleScript.js"), AS3("shBrushAS3.js"), BASH("shBrushBash.js"), COLDFUSION("shBrushColdFusion.js"), CPP("shBrushCpp.js"), CSHARP(
			"shBrushCSharp.js"), CSS("shBrushCss.js"), DELPHI("shBrushDelphi.js"), DIFF("shBrushDiff.js"), ERLANG("shBrushErlang.js"), GROOVY(
			"shBrushGroovy.js"), JAVA("shBrushJava.js"), JAVAFX("shBrushJavaFX.js"), JSCRIPT("shBrushJScript.js"), PERL("shBrushPerl.js"), PHP("shBrushPhp.js"), PLAIN(
			"shBrushPlain.js"), POWERSHELL("shBrushPowerShell.js"), PYTHON("shBrushPython.js"), RUBY("shBrushRuby.js"), SASS("shBrushSass.js"), SCALA(
			"shBrushScala.js"), SQL("shBrushSql.js"), VB("shBrushVb.js"), XML("shBrushXml.js");

	private String filename;

	private Lang(String filename) {
		this.filename = filename;
	}

	public String getFileName() {
		return filename;
	}
	
	public String getBrushName(){
		return name().toLowerCase(Locale.US);
	}
}
