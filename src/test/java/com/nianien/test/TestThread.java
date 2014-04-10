package com.nianien.test;

import com.nianien.core.thread.ParameterizedRunnable;
import com.nianien.core.thread.ParamterizedThread;

/**
 * @author skyfalling
 *
 */
public class TestThread {
	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		new ParamterizedThread(){
			@Override
			public void run(Object...args){
				System.out.print(args[0]+","+args[1]);
			}
		}.start(1,2);
		System.out.println(","+3);
		Thread.sleep(1000);
		new ParamterizedThread(new ParameterizedRunnable() {
			
			@Override
			public void run(Object... parameters) {
				System.out.print(parameters[0]+","+parameters[1]);
				
			}
		}){
			
		}.start(1,2);
		System.out.println(","+3);
		
	}
	
}
