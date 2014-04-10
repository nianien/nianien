package com.nianien.test;

import java.util.Timer;
import java.util.TimerTask;

import com.nianien.core.thread.RunnableTask;

/**
 * @author skyfalling
 * 
 */
public class TestTask {
	private static boolean flag = true;

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		test();
	}

	public static void _test() throws Exception {
		RunnableTask task = new RunnableTask() {

			@Override
			public void run(Object... args) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
				flag = false;

			}
		};
		task.start(300);
		System.out.println("任务启动,初始状态:flag==>" + flag);
		Thread.sleep(1000);
		task.cancel();
		int i = 1;
		while (flag) {
			Thread.sleep(1000);
			System.out.println("" + i++ + "秒之后状态:flag==>" + flag);
		}
		System.out.println("任务完成,最终状态:flag==>" + flag);

	}

	public static void test() throws Exception {
		class TimeOutTask extends TimerTask {

			private boolean isTimeout = false;

			@Override
			public void run() {
				this.isTimeout = true;
			}
		}
		
		TimeOutTask task = new TimeOutTask();

		new Timer().schedule(task, 5 * 1000);

		int n=1;
		while (!task.isTimeout) {
			Thread.sleep(1000);
			System.out.println("Waiting..."+n);
			if(n++>10)
			break;
		}
		task.cancel();
		if(task.isTimeout){
			System.out.println("timeOut");
		}
		else{
			System.out.println("OK");
		}
	}
}
