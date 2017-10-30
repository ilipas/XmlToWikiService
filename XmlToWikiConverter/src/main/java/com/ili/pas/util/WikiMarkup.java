package com.ili.pas.util;

public class WikiMarkup {
	
	public static final String ITALIC = "''";
	public static final String BOLD = "'''";
	
	private WikiMarkup() {
	}

	public static String getHeadingLevel(int key) {
		
		String headingLevel = "";
		
		switch (key) {
		case 1:
			headingLevel = "=";
			break;
		case 2:
			headingLevel = "==";
			break;
		case 3:
			headingLevel = "===";
			break;
		case 4:
			headingLevel = "====";
			break;
		default:
			headingLevel = "======";
			break;
		}
		return headingLevel;
	}

}
