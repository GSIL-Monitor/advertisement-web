package com.yuanshanbao.common.util.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPool {

	public static final int THREAD_POOL_CORE_SIZE = 5;// 线程池最少线程数
	public static final int THREAD_POOL_MAX_SIZE = 20;// 最大线程数
	public static final int THREAD_MAX_THREAD_WAIT = 1000;// 最大线程等待数 modify
															// wangjn
															// 这个值设置过大会导致超过corePoolSize的线程无法执行
	public static final int THREAD_POOL_WAIT_SECONDS = 5 * 60;// 最长等待时间

	private ExecutorService exec;

	private static ScheduledExecutorService schedule = Executors.newScheduledThreadPool(10);

	private static volatile ThreadPool instance;

	private ThreadPool() {

		exec = new ThreadPoolExecutor(THREAD_POOL_CORE_SIZE, THREAD_POOL_MAX_SIZE, THREAD_POOL_WAIT_SECONDS,
				TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(THREAD_MAX_THREAD_WAIT),
				new ThreadPoolExecutor.AbortPolicy());
	}

	public static ThreadPool getInstance() {

		if (instance == null) {
			synchronized (ThreadPool.class) {
				if (instance == null) {
					instance = new ThreadPool();
				}
			}
		}

		return instance;
	}

	public void exec(Runnable command) {

		exec.execute(command);
	}

	public <T> Future<T> submit(Callable<T> command) {

		return exec.submit(command);
	}

	public void schedule(Runnable command, long delay, TimeUnit unit) {
		schedule.schedule(command, delay, unit);
	}

	public void shutdown() {
		exec.shutdown();
		schedule.shutdown();
	}

	public boolean isTerminated() {

		return exec.isTerminated() && schedule.isTerminated();
	}

	class TestRunner implements Callable<String> {

		public String call() {

			for (int i = 0; i < 10; i++) {
				System.out.println(i);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
				}
			}
			return "OK";
		}
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {

		// 主线程等待子线程全部结束后运行
		ThreadPool tp = ThreadPool.getInstance();
		TestRunner tr = tp.new TestRunner();
		TestRunner tr1 = tp.new TestRunner();
		Future<String> fr1 = tp.submit(tr);
		Future<String> fr2 = tp.submit(tr1);
		tp.shutdown();
		while (!tp.isTerminated()) {
		}
		System.out.println(fr1.get());
		System.out.println(fr2.get());
		System.out.println("fin");

		ThreadPool tpp = ThreadPool.getInstance();
		TestRunner trr = tp.new TestRunner();
		TestRunner trr1 = tp.new TestRunner();
		Future<String> frr1 = tp.submit(trr);
		Future<String> frr2 = tp.submit(trr1);
		tpp.shutdown();
		while (!tpp.isTerminated()) {
		}
		System.out.println(frr1.get());
		System.out.println(frr2.get());
		System.out.println("finn");
	}
}
