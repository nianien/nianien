package com.nianien.test;

import com.nianien.core.util.MD5Generator;

public class Md5Test {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		String dyt = new MD5Generator()
				.md5ToString("C:\\Users\\skyfalling.skyfalling-lee\\Desktop\\MyEclipse.9.0.M2破解方法注册机\\publicKey.bytes");
		System.out.println(dyt);
	}

}
