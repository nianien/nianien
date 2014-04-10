package com.nianien.core.thread;

/**
 * 用作ParameterizedThread对象的构造参数,实现其线程行为
 * 
 * @author skyfalling
 * 
 */
public interface ParameterizedRunnable {

	/**
	 * 根据提供的参数运行线程<br>
	 * 该方法为抽象方法,须由继承类实现
	 * 
	 * @param parameters
	 */
	void run(Object... parameters);

}
