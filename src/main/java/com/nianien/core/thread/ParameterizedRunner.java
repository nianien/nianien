package com.nianien.core.thread;

import com.nianien.core.exception.ExceptionHandler;
import com.nianien.core.exception.NotImplementException;

/**
 * ParameterizedRunnable接口以及Runnable接口的抽象实现类
 * 
 * @author skyfalling
 * 
 */
public class ParameterizedRunner implements ParameterizedRunnable, Runnable {

	private Object[] parameters;

	/**
	 * 构造方法,实例化的对象可作为Runnable接口类型使用
	 * 
	 */
	public ParameterizedRunner() {
	}

	/**
	 * 构造方法,实例化的对象可作为ParameterizedRunnable接口类型使用
	 * 
	 * @param parameters
	 */
	public ParameterizedRunner(Object... parameters) {
		this.parameters = parameters;
	}

	/**
	 * 定义带参数的线程行为<br>
	 * 如果要在线程中执行该方法,必须在继承类中重写该方法,否则抛出异常<br>
	 * 该方法执行时所需要参数的参数需要在构造函数中提供
	 * 
	 * @param parameters
	 */
	@Override
	public void run(Object... parameters) {
		ExceptionHandler.throwException(new NotImplementException(
				"没有实现ParameterizedRunnable接口定义的方法：run(Object... parameters)!"));
	}

	@Override
	public void run() {
		this.run(parameters);
	}

}
