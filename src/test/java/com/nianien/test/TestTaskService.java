package com.nianien.test;

import com.nianien.core.util.TaskService;

public class TestTaskService {
	static int flag = 1;

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		TaskService ts = new TaskService();
		ts.start();
		for (int i = 0; i < 5; i++) {
			System.out.println("add task...");
			ts.addTask(new Runnable() {

				@Override
				public void run() {
					System.out.println("==>:" + flag++);

					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}
		System.out.println("waiting for...");
		Thread.sleep(3000);
		for (int i = 0; i < 5; i++) {
			System.out.println("add task...");
			ts.addTask(new Runnable() {

				@Override
				public void run() {
					System.out.println("==>:" + flag++);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
			});
			Thread.sleep(100);
		}

		ts.shutdown();
		System.out.println("shout down...");

	}

}
