package com.ipjmc.solr.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextHelper {

	public static boolean isEmpty(String str) {
		return (str == null || str.equals(""));
	}
	
	public static boolean isBlank(String str) {
		if (isEmpty(str)) {
			return true;
		}
		
		Pattern pattern = Pattern.compile("^\\s*$");
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
}
