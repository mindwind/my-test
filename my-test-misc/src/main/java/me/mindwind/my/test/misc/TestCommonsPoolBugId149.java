package me.mindwind.my.test.misc;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.junit.Test;

/**
 * @see https://issues.apache.org/jira/browse/POOL-149 
 * @author Shuyang Zhou
 */
public class TestCommonsPoolBugId149 {

	public static int TEST_THREAD_NUM = 20;
	public static int ITERATIONS_PER_THREAD = 1000;
	public static int MAX_ACTIVE = 3;
	public static int TEST_TIMES = 2000;

	/**
	 * A dummy PoolableObjectFactory, creates Strings with unique names
	 */
	private static class DummyPoolableObjectFactory implements
		PoolableObjectFactory {

		public Object makeObject() throws Exception {
			return "Object number-" + counter.getAndIncrement();
		}

		public void destroyObject(Object obj) throws Exception {
			throw new UnsupportedOperationException("Not supported yet.");
		}

		public boolean validateObject(Object obj) {
			throw new UnsupportedOperationException("Not supported yet.");
		}

		public void activateObject(Object obj) throws Exception {
			throw new UnsupportedOperationException("Not supported yet.");
		}

		public void passivateObject(Object obj) throws Exception {
			throw new UnsupportedOperationException("Not supported yet.");
		}

		private final AtomicInteger counter = new AtomicInteger();
	}

	/**
	 * A test job to generate a heavy load against test ObjectPool,
	 * all checkout objects will be invalidate.
	 */
	private static class TestJob implements Runnable {

		private final ObjectPool testPool;
		private final int iterations;

		public TestJob(ObjectPool testPool, int iterations) {
			this.testPool = testPool;
			this.iterations = iterations;
		}

		public void run() {
			for (int i = 0; i < iterations; i++) {
				Object obj = null;
				try {
					obj = testPool.borrowObject();
				}
				catch (Exception ex) {
				}
				finally {
					try {
						testPool.invalidateObject(obj);
					}
					catch (Exception ex) {
					}
				}
			}
		}

	}

	@Test
	public void testGenericObjectPool() throws Exception {

		ObjectPool testPool = new GenericObjectPool(new DummyPoolableObjectFactory(), MAX_ACTIVE);
		for (int i = 0; i < TEST_TIMES; i++) {
			runOnce(testPool);
			System.out.println("Still Alive:" + new Date());
		}
	}

	private void runOnce(ObjectPool testPool) throws Exception {
		ExecutorService executorService = Executors.newFixedThreadPool(TEST_THREAD_NUM);
		List<TestJob> jobs = new ArrayList<TestJob>(TEST_THREAD_NUM);
		for (int i = 0; i < TEST_THREAD_NUM; i++) {
			jobs.add(new TestJob(testPool, ITERATIONS_PER_THREAD));
		}
		for (TestJob job : jobs) {
			executorService.submit(job);
		}
		executorService.shutdown();
		executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
	}

}