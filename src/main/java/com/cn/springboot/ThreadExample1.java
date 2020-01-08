package com.cn.springboot;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadExample1 {
	
	public static Logger logger = LoggerFactory.getLogger(ThreadExample.class);

	public static void main(String[] args) {
		BlockingQueue<Double> queue = new ArrayBlockingQueue<Double>(10);
		ExecutorService executorService = Executors.newCachedThreadPool();
		ThreadExample1 t = new ThreadExample1();
		Producer producer = t.new Producer(queue);
		Customer customer = t.new Customer(queue);
		executorService.execute(producer);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		executorService.execute(customer);
		executorService.shutdown();
    }
	
class Producer implements Runnable{
		private volatile boolean isRun = true;
		BlockingQueue<Double> queue;
		public Producer(BlockingQueue<Double> queue){
			this.queue = queue;
		}
		@Override
		public void run() {
			while(isRun) {
				try {
					Thread.sleep(1000);
					Double d = Double.valueOf(Math.random()*100);
					logger.info("放入数据:{}",d);
					if(!queue.offer(d,2,TimeUnit.SECONDS)) {
						logger.info("放入数据失败");
						stop();
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			logger.info("结束放入数据");
		}
		public void stop() {
			isRun = false;
		}
	}
	
class Customer implements Runnable{
	BlockingQueue<Double> queue;
	public Customer(BlockingQueue<Double> queue){
		this.queue = queue;
	}
	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(500);
				logger.info("获取数据 ：{}",queue.take());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
}
