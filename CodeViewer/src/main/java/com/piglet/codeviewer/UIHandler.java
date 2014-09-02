package com.piglet.codeviewer;

import com.piglet.config.CodeStyle;
import com.piglet.config.Lang;

public interface UIHandler {
	public void openSourceFile(String path, CodeStyle style, Lang lang);
	
	public void toggleMenu();
	
	public void close(String path, String candidatePath);
}
