package alex.structures.test;

import org.testng.annotations.Test;

import alex.structures.SimpleQueue;

public class SimpleQueueTest
{
	/**
	 * Checks the initial state of the queue, and that simple enqueue dequeue
	 * work correctly
	 */
	@Test
	public void SimpleQueueBasicTest()
	{
		SimpleQueue<Integer> queue = new SimpleQueue<>(5);

		assert queue.isEmpty();
		assert queue.maxSize() == 5;

		queue.enqueue(1);
		assert !queue.isEmpty();
		assert queue.dequeue() == 1;
		assert queue.isEmpty();
	}

	/**
	 * Checks that the queue can be used to maximum size that is initialized to,
	 * and that it correctly returns elements after we've enqueued/dequeued more
	 * than the queue max size
	 */
	@Test
	public void SimpleQueueFullTest1()
	{
		SimpleQueue<Integer> queue = new SimpleQueue<>(4);

		int[] arr = { 1, 2, 3, 4 };
		for (int a : arr)
		{
			queue.enqueue(a);
		}

		for (int i = 0; i < arr.length; ++i)
		{
			assert queue.dequeue() == arr[i];
		}

		for (int a : arr)
		{
			queue.enqueue(10 * a);
		}

		for (int i = 0; i < arr.length; ++i)
		{
			assert queue.dequeue() == arr[i] * 10;
		}
	}

	/**
	 * 
	 */
	@Test
	public void SimpleQueueFullTest2()
	{

	}

	/**
	 * Check that the expected exception is thrown when doing a bad dequeue
	 */
	@Test(expectedExceptions = IllegalStateException.class)
	public void SimpleQueueBadDequeue()
	{
		SimpleQueue<String> queue = new SimpleQueue<>(50);
		queue.enqueue("100");
		queue.dequeue();

		// Bad dequeue should throw exception
		queue.dequeue();

		assert false;
	}

	/**
	 * Check that the expected exception is thrown when doing a bad enqueue
	 */
	@Test(expectedExceptions = IllegalStateException.class)
	public void SimpleQueueBadEnqueue()
	{
		SimpleQueue<String> queue = new SimpleQueue<>(2);
		queue.enqueue("word1");
		queue.enqueue("word2");

		// bad enqueue should throw exception
		queue.enqueue("word3");
	}

	/**
	 * Check that the expected exception is thrown on init of negative size
	 */
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void SimpleQueueBadInit1()
	{
		@SuppressWarnings("unused")
		SimpleQueue<String> queue = new SimpleQueue<>(-1231231);

		assert false;
	}

	/**
	 * Check that the expected exception is thrown on init of size 0
	 */
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void SimpleQueueBadInit2()
	{
		@SuppressWarnings("unused")
		SimpleQueue<String> queue = new SimpleQueue<>(0);

		assert false;
	}
}
