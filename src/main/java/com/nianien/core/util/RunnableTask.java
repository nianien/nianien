package com.nianien.core.util;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 扩展的TimerTask对象,能够自动启动定时任务
 *
 * @author skyfalling
 */
public class RunnableTask extends TimerTask {

	/**
	 * 执行参数
	 */
	private Runnable runnable;
	/**
	 * 定时器
	 */
	private Timer timer = new Timer();

	/**
	 * 构造方法,指定执行参数
	 *
	 * @param runnable
	 */
	public RunnableTask(Runnable runnable) {
		this.runnable = runnable;
	}

	/**
	 * 在指定延迟时间后执行
	 *
	 * @param delay 延迟时间，单位毫秒
	 */
	public void start(long delay) {
		timer.schedule(this, delay);

	}

	/**
	 * 在指定的时间执行任务, 如果此时间已过去, 则安排立即执行
	 *
	 * @param datetime 执行时间
	 */
	public void start(Date datetime) {
		timer.schedule(this, datetime);
	}

	/**
	 * 任务从指定的延迟时间后开始进行重复的固定延迟执行,保证任务执行的时间间隔固定
	 *
	 * @param delay  延迟时间,单位毫秒
	 * @param period 时间间隔,单位毫秒
	 */
	public void start(long delay, long period) {
		timer.schedule(this, delay, period);

	}

	/**
	 * 任务从指定的时间开始进行重复的固定延迟执行,保证任务执行的时间间隔固定
	 *
	 * @param firstTime
	 * @param period
	 */
	public void start(Date firstTime, long period) {
		timer.schedule(this, firstTime, period);
	}

	/**
	 * 任务从指定的延迟时间后开始进行重复的固定速率执行,保证任务执行的时间点固定
	 *
	 * @param firstTime
	 * @param period
	 */
	public void startAtFixedRate(Date firstTime, long period) {
		timer.scheduleAtFixedRate(this, firstTime, period);
	}

	/**
	 * 任务从指定的时间开始进行重复的固定速率执行,保证任务执行的时间点固定
	 *
	 * @param delay
	 * @param period
	 */
	public void startAtFixedRate(long delay, long period) {
		timer.scheduleAtFixedRate(this, delay, period);
	}


	@Override
	public final void run() {
		this.runnable.run();
	}


}
