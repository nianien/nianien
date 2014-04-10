package com.nianien.test;

import com.nianien.core.exception.ExceptionHandler;
import com.nianien.core.io.Closer;
import com.nianien.core.io.Files;
import com.nianien.core.util.CollectionUtils;
import com.nianien.core.util.TimeCounter;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestIo {
	private final static int bufferSize = 1024 * 8;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File file = new File("all200000.txt");// all200000.txt
		assert file.exists();
		TimeCounter tc = new TimeCounter();
		byte[] bytes = getBytes(file);
		tc.start();
		for (int i = 0; i < 100; i++) {
			bytes = getBytes(file);
		}
		tc.stop();
		System.out.println(tc.timePassed());
		byte[] bytes2 = Files.getBytes(file);
		tc.start();
		for (int i = 0; i < 100; i++) {
			bytes2 = Files.getBytes(file);
		}
		tc.stop();
		System.out.println(tc.timePassed());
		assert Arrays.equals(bytes, bytes2);
		System.out.println(bytes.length);

	}

	public static byte[] getBytes(File file) {
		try {
			return getBytes(new FileInputStream(file));
		} catch (Exception e) {
            throw  ExceptionHandler.throwException(e);
		}
	}

	/**
	 * 获取InputStream对象指定长度的字节内容
	 * 
	 * @param instream
	 * @return 文件字节内容
	 */
	public static byte[] getBytes(InputStream instream) {
		try {
			List<Byte> list = new ArrayList<Byte>();
			int length = -1;
			byte[] buffer = new byte[bufferSize];
			while ((length = instream.read(buffer)) != -1) {
				for (int i = 0; i < length; i++) {
					list.add(buffer[i]);
				}
			}
			return CollectionUtils.byteArray(list);
		} catch (Exception e) {
            throw ExceptionHandler.throwException(e);
		} finally {
			Closer.close(instream);
		}
	}
}
