package me.mindwind.my.test.misc;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

public class TestFastjson {
	
	@Test
	public void test() {
		TestObject to = new TestObject();
		to.add("test1");
		to.add("test2");
		String text = JSON.toJSONString(to);
		System.out.println(text);
//		JSONObject jo = JSON.parseObject(text);
//		to = JSON.toJavaObject(jo, TestObject.class);
		to = JSON.parseObject(text, TestObject.class);
	}
	
	public static class TestObject {
		
		private Set<String> set = new HashSet<String>(0);

		public Set<String> getSet() {
			return set;
		}

		public void setSet(Set<String> set) {
			this.set = set;
		}
		
		public void add(String str) {
			set.add(str);
		}
	}
	
}
