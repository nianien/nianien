package com.nianien.core.io;

import java.io.Closeable;

/**
 * 执行对象声明的close方法
 * 
 * @author skyfalling
 * 
 */
public class Closer {

	/**
	 * 关闭closeable对象<br>
	 * 
	 * @param closeable
	 */
	public static void close(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 如果对象声明了close方法,则执行该方法<br>
	 * 该方法不会发生异常
	 * 
	 * @param object
	 */
	public static void close(Object object) {
		if (object != null) {
			try {
				object.getClass().getMethod("close").invoke(object);
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 依次关闭指定的对象
	 * 
	 * @param object
	 */
	public static void close(Object... object) {
		for (Object obj : object) {
			close(obj);
		}
	}
}
