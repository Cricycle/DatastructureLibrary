package alex.structures;

import alex.checks.Assert;

public class SimpleQueue<T>
{
	private final Object[] queue;

	/**
	 * Inclusive - this is the front of the queue
	 */
	private int front;

	/**
	 * Exclusive - this is the next location where a new element would go
	 */
	private int back;

	public SimpleQueue(int size)
	{
		Assert.ArgInRangeExcl(size, 0, Integer.MAX_VALUE, "size");
		queue = new Object[size + 1];
		front = 0;
		back = 0;
	}

	public boolean isEmpty()
	{
		return front == back;
	}

	public int maxSize()
	{
		return queue.length - 1;
	}

	public void enqueue(T t)
	{
		if (isFull())
			throw new IllegalStateException("Tried to enqueue into full SimpleQueue. t: " + t);

		queue[back++] = t;
		if (back == queue.length)
			back = 0;
	}

	public T dequeue()
	{
		if (isEmpty())
			throw new IllegalStateException("Tried to dequeue from an empty queue.");

		@SuppressWarnings("unchecked")
		T ret = (T) queue[front++];
		if (front == queue.length)
			front = 0;

		return ret;
	}

	private boolean isFull()
	{
		return (front == back + 1) || (front == 0 && back == (queue.length - 1));
	}
}
