package me.mindwind.my.test.misc;

import java.nio.charset.Charset;

/**
 * @author mindwind
 * @version 1.0, May 13, 2014
 */
public class StringToHexString {
	
	public static void main(String[] args) {
		String in = "中文";
		byte[] bytes = in.getBytes(Charset.forName("utf-8"));
		for (int i = 0; i < bytes.length; i++) { 
			String hex = Integer.toHexString(bytes[i] & 0xFF); 
			if (hex.length() == 1) { 
				hex = "0" + hex; 
			} 
			System.out.print(hex.toUpperCase() + " ");
		} 
	}
	
}
