package com.nianien.test;

import java.io.File;
import java.util.Comparator;

import com.nianien.core.util.MassSorter;

public class TestMassDataSort {
	public static void main(String[] args) throws Exception {

		MassSorter mds = new MassSorter(10000,
				new Comparator<String>() {

					@Override
					public int compare(String str1, String str2) {
						if (str1.trim().isEmpty()) {
							return 1;
						}
						if (str2.trim().isEmpty()) {
							return -1;
						}
						return Integer.parseInt(str1.trim())
								- Integer.parseInt(str2.trim());
					}
				});
		mds.sort(
				new File(
						"D:\\Workspaces\\Skyfalling\\NiaNienTest\\all200000.txt"),
				new File(
						"D:\\Workspaces\\Skyfalling\\NiaNienTest\\all200000-sort3.txt"));

	}
}
