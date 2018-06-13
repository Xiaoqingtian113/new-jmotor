package com.zhucq.mobile.util;

import java.util.HashSet;
import java.util.Set;

public class StringUtils {
	
	public static String removeDuplication(String str) {
		Set<Character> tmpSet = new HashSet<Character>();
		for (char ch : str.toCharArray()) {
			tmpSet.add(ch);
		}
		StringBuffer sb = new StringBuffer();
		for (char ch : tmpSet) {
			sb.append(ch);
		}
		return sb.toString();
	}
}
