package com.nianien.test;

import java.text.NumberFormat;

public class DecimalFormat {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		NumberFormat format = NumberFormat.getPercentInstance();
		format.setMaximumFractionDigits(2);
		String s=format.format(.333454);
		System.out.println(s);
		System.out.println(String.format("%.2f%%", 33.3333));
	}

}
