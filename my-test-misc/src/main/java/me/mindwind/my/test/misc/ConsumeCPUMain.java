package me.mindwind.my.test.misc;

/**
 * @author mindwind
 * @version 1.0, Apr 10, 2014
 */
public class ConsumeCPUMain {

	public static void main(String[] args) throws InterruptedException {
		long wtime = 30 * 1000;
		if (args != null && args.length > 0) {
			wtime = Long.parseLong(args[0]) * 1000;
		}
		
		int cpus = Runtime.getRuntime().availableProcessors() * 10;
		for (int i = 0; i < cpus; i++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					while(true);
				}
			}).start();
		}
		Thread.currentThread().join(wtime);
		System.out.print("busy time out");
		System.exit(1);
	}

}
