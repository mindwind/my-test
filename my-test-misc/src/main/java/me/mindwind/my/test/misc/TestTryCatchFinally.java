package me.mindwind.my.test.misc;

import junit.framework.Assert;

import org.junit.Test;

/**
 * @author mindwind
 * @version 1.0, Jan 14, 2014
 */
public class TestTryCatchFinally {
	
	@Test
	public void test() {
		int x = x();
		Assert.assertEquals(1, x);
		Integer y = y();
		Assert.assertEquals(1, y.intValue());
	}
	
	private int x() {
		int x;
		try {
			x = 1;
			return x;
		} catch (Exception e) {
			x = 2;
			return x;
		} finally {
			x = 3;
		}
	}
	
	private Integer y() {
		Integer y;
		try {
			y = new Integer(1);
			return y;
		} catch (Exception e) {
			y = new Integer(2);
			return y;
		} finally {
			y = new Integer(3);
		}
	}
}
