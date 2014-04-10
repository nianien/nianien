package com.nianien.test;

import com.nianien.idea.properties.Properties;

public class TestProperty {

	public static void main(String[] args){
		
		Properties pp=new Properties("properties.xml");
		String sql=pp.getProperty("delete");
		System.out.println(sql);
		sql=pp.defaultPackage("users").getProperty("update");
		System.out.println(sql);
	}
}
