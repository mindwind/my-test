package me.mindwind.my.test.misc;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author mindwind
 * @version 1.0, Jan 9, 2014
 */
public class ThreadPoolThrowsMain {
	
	public static void main(String[] args) {
		Executor executor = Executors.newFixedThreadPool(1);
		final AtomicInteger c = new AtomicInteger();
		
		for (int i = 0; i < 10; i++) {
			executor.execute(new Runnable() {
				@Override
				public void run() {
					int i = c.incrementAndGet();
					System.out.println(i);
					if (i == 5) {
						throw new OutOfMemoryError();
					}
				}
			});
		}
		
		ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutor.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				int i = c.incrementAndGet();
				System.out.println(i);
				if (i % 2 == 0) {
					throw new RuntimeException();
				}
			}
		}, 0, 2, TimeUnit.SECONDS);
	}
	
}
