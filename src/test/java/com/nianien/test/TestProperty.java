package com.nianien.test;

import org.junit.Test;

import com.nianien.idea.properties.Properties;

public class TestProperty {

	@Test
	public void test() {
		
		Properties pp=new Properties("properties.xml");
		String sql=pp.getProperty("delete");
		System.out.println(sql);
		sql=pp.defaultPackage("users").getProperty("update");
		System.out.println(sql);
	}
}
