package com.nianien.test;

import org.junit.Test;

import com.nianien.core.loader.FilePathClassLoader;

/**
 * @author skyfalling
 * 
 */
public class TestLoader {

	@Test
	public void testLoader() throws Exception {
		ClassLoader loader = new FilePathClassLoader(".");
		Class<?> cl = loader.loadClass("com.nianien.test.TestLoader");
		cl.getMethod("test0").invoke(null);
	}

	public static void test0() {
		System.out.println("hello,world");
	}

}
