package com.nianien.test;

import java.io.IOException;

import com.nianien.core.loader.FilePathClassLoader;

/**
 * @author skyfalling
 * 
 */
public class TestLoader {

	/**
	 * @param args
	 * @throws java.io.IOException
	 * @throws Exception
	 */
	public static void main(String[] args) throws IOException, Exception {
		String str = "D:\\Programming\\Java\\Workspace\\NiaNienTest\\bin;";
		ClassLoader loader = new FilePathClassLoader(str);
		Class<?> cl = loader.loadClass("com.nianien.test.TestLoader");
		cl.getMethod("test").invoke(null);
	}
	
	public static void test(){
		System.out.println("hello,world");
	}

}
