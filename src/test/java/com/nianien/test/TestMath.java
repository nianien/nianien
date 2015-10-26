package com.nianien.test;

import org.junit.Test;

import com.nianien.core.math.Calculator;


public class TestMath {

	@Test
	public void test() {
		String a="(2^1^4)+(2^1)^4";
		System.out.println(Calculator.calculate(a));
		System.out.println(Calculator.calculate("(2+3/0 *2)-2"));
	}

}
