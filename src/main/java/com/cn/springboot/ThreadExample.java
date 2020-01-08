package com.cn.springboot;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




public class ThreadExample {

	public static Logger logger = LoggerFactory.getLogger(ThreadExample.class);
	public static int count = 1000;
	public static int permits = 50;
	public static volatile int cou = 0;
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
		cou++;
	}

}
