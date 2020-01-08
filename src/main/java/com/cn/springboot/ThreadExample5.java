package com.cn.springboot;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




public class ThreadExample5 {

	public static Logger logger = LoggerFactory.getLogger(ThreadExample5.class);
	
	public static void main(String[] args) {
		
		ExecutorService excu = Executors.newCachedThreadPool();
		ThreadExample5 t = new ThreadExample5();
		Future<String> future = excu.submit(t.new MCallable());
//		FutureTask<Integer> future = new FutureTask<Integer>(new Callable<Integer>() {
//			@Override
//			public Integer call() throws Exception {
//				Thread.sleep(5000);
//				return 100;
//			}
//		});
		
		try {
//			new Thread(future).start();
			logger.info("future:{}",future.get());
		} catch (Exception e) {
			logger.error("exception",e);
		}
		
    }
	
	class MCallable implements Callable<String> {

		@Override
		public String call() throws Exception {
			return "done";
		}
		
	}

}
