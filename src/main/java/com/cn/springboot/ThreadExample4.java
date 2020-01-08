package com.cn.springboot;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




public class ThreadExample4 {

	public static Logger logger = LoggerFactory.getLogger(ThreadExample4.class);
	
	public static void main(String[] args) {
		
		FutureTask<Integer> future = new FutureTask<Integer>(new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				Thread.sleep(5000);
				return 100;
			}
		});
		
		try {
			new Thread(future).start();
			Integer i= future.get();
			logger.info("future:{}",i);
		} catch (Exception e) {
			logger.error("exception",e);
		}
		
    }

}
