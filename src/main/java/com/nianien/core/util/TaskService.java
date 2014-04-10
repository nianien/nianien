package com.nianien.core.util;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.nianien.core.exception.ExceptionHandler;

/**
 * 任务队列服务,可以按照一定策略执行队列中的任务,该类是线程安全的
 * 
 * @author skyfalling
 * @param <T>
 */
public class TaskService {

	/**
	 * 执行钩子操作的策略
	 * 
	 * @author skyfalling
	 */
	public static interface TaskHookPolicy {

		/**
		 * 是否满足执行钩子操作的条件<br>
		 * 如果满足,执行钩子操作并返回true,否则不做任何操作并返回false<br>
		 * 
		 * @param timePassed
		 *            上次执行钩子操作后,到目前已经过去的时间长度,单位毫秒
		 * @param taskExecuted
		 *            上次执行钩子操作后,到目前已经执行的任务数
		 * @return
		 */
		boolean meets(long timePassed, long taskExecuted);

	}

	/**
	 * 任务队列
	 */
	private Queue<Runnable> taskQueue = new ConcurrentLinkedQueue<Runnable>();
	/**
	 * 服务是否开启
	 */
	private boolean ON;
	/**
	 * 服务已经关闭并且任务全部执行结束
	 */
	private boolean FINISHED;

	/**
	 * 添加任务
	 * 
	 * @param runnable
	 */
	public synchronized void addTask(Runnable task) {
		if (ON) {
			taskQueue.offer(task);
			notifyAll();
		}
	}

	/**
	 * 启动任务, 并执行队列中所有的任务
	 */
	public void start() {
		doStart(new Runnable() {
			@Override
			public void run() {
				while (ON || !taskQueue.isEmpty()) {
					while (!taskQueue.isEmpty()) {
						taskQueue.poll().run();
					}
					doWait(0);
				}
			}
		});
	}

	/**
	 * 启动任务, 定时检测任务执行状态并执行钩子操作
	 * 
	 * @param policy
	 */
	public void start(final TaskHookPolicy policy) {
		doStart(new Runnable() {
			@Override
			public void run() {
				// 上次执行钩子操作的时间
				long lastHookTime = System.currentTimeMillis();
				// 从上次执行钩子操作到现在已执行的任务数
				long taskExecuted = 0;
				while (ON || !taskQueue.isEmpty()) {
					try {
						int size = taskQueue.size();
						for (int i = 0; i < size; i++) {
							taskQueue.poll().run();
						}
						taskExecuted += size;
						if (policy.meets(System.currentTimeMillis()
								- lastHookTime, taskExecuted)) {
							// 重置钩子条件
							taskExecuted = 0;
							lastHookTime = System.currentTimeMillis();
						}
						doWait(1000);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	/**
	 * 关闭服务<br>
	 * 调用该方法后,将不再接收新的任务,但剩余的任务会被继续执行
	 */
	public synchronized void shutdown() {
		if (ON) {
			ON = false;
			try {
				this.notifyAll();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 服务是否结束<br>
	 * 当且仅当服务关闭且任务队列全部执行完成时返回true
	 * 
	 * @return
	 */
	public boolean isFinished() {
		return FINISHED;
	}

	/**
	 * 启动服务
	 * 
	 * @param service
	 */
	private synchronized void doStart(final Runnable service) {
		ExceptionHandler.throwIf(ON,
                "task service has already started!");
		ON = true;
		new Thread(new Runnable() {
			@Override
			public void run() {
				service.run();
				FINISHED = true;
			}
		}).start();
	}

	/**
	 * 等待新加入的任务
	 * 
	 * @param timeout
	 */
	private synchronized void doWait(long timeout) {
		if (ON) {
			try {
				this.wait();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
