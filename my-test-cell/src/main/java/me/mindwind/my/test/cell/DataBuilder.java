package me.mindwind.my.test.cell;

/**
 * @author mindwind
 * @version 1.0, Mar 20, 2013
 */
public class DataBuilder {
	
	public static String buildString(int len) {
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < len; i++) {
			buf.append(1);
		}
		return buf.toString();
	}
	
}
