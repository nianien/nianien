package com.nianien.core.thread;

/**
 * 支持运行参数的Thread扩展类<br>
 * 可以有两种方式实现线程动作：<br>
 * 1,Override run(Object... parameters)方法; 2,提供ParameterizedThread对象作为构造参数
 * 
 * @author skyfalling
 * 
 */
public class ParamterizedThread extends Thread {

	/**
	 * ParameterizedRunnable对象
	 */
	private ParameterizedRunnable target;

	/**
	 * 运行参数
	 */
	private Object[] parameters;

	/**
	 * 默认构造方法
	 * 
	 */
	public ParamterizedThread() {
	}

	/**
	 * 构造方法,传递ParameterizedRunnable对象
	 * 
	 * @param runnable
	 */
	public ParamterizedThread(ParameterizedRunnable runnable) {
		this.target = runnable;
	}

	/**
	 * 启动线程,并传递执行参数
	 * 
	 * @param parameters
	 */
	public void start(Object... parameters) {
		this.parameters = parameters;
		super.start();
	}

	/**
	 * 禁止子类覆盖该方法
	 */
	@Override
	final public void run() {
		this.run(this.parameters);
	}

	/**
	 * 受包括的方法,继承类重载该方法
	 * 
	 * @param parameters
	 */
	protected void run(Object... parameters) {
		if (this.target != null) {
			this.target.run(parameters);
		}
	}

}
