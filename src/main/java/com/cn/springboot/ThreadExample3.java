package com.cn.springboot;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




public class ThreadExample3 {

	public static Logger logger = LoggerFactory.getLogger(ThreadExample3.class);
	public static int count = 5000;
	public static int permits = 200;
	public static volatile int cou = 0;
	public final static Lock lock = new ReentrantLock();
	public static void main(String[] args) {
		ExecutorService executorService = Executors.newCachedThreadPool();
		CountDownLatch countDownLatch = new CountDownLatch(count);
		Semaphore semaphore = new Semaphore(permits);
		for(int i=0;i<count;i++) {
			executorService.execute(()->{
				try {
					semaphore.acquire();
					update();
					semaphore.release();
				} catch (InterruptedException e) {
					logger.error("exception",e);
				}
				countDownLatch.countDown();
			});
		}
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			logger.error("exception",e);
		}
		executorService.shutdown();
		logger.info("cou:{}",cou);
    }
	
	private static void update() {
		lock.lock();
		try {
			cou++;
		}finally {
			lock.unlock();
		}
	}

}
