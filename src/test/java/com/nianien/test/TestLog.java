package com.nianien.test;

import com.nianien.core.log.Loggers;

import java.util.logging.Logger;

public class TestLog {

	private static Logger LOG1 = Loggers.getLogger("a.b");
	private static Logger LOG2 = Loggers.getLogger("a.b.c");

	/**
	 * @param args
	 * @throws java.io.IOException
	 */
	public static void main(String[] args) throws Exception {
//		Loggers.setLevel(Level.WARNING);
		LOG1.info("dddd");
		LOG1.warning("dddd");
		LOG1.severe("dddd");
//		Loggers.setLevel(Level.INFO);

		System.err.println("================");
		LOG2.info("dddd");
		LOG2.warning("dddd");
		LOG2.severe("dddd");

	}

}
