package com.nianien.core.exception;

/**
 * 方法体没有实现的异常
 * @author skyfalling
 *
 */
public class NotImplementException extends Exception {

	private static final long serialVersionUID = 1L;
	/**
	 * 构造方法
	 */
	public NotImplementException(){
		super();
	}
	/**
	 * 构造方法,返回错误信息
	 * @param message 
	 */
	public NotImplementException(String message){
		super(message);
	}
}
