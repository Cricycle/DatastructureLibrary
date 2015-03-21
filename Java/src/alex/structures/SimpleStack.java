package alex.structures;

import alex.checks.Assert;

public class SimpleStack<T>
{
	private final Object[] stack;
	private int top;

	public SimpleStack(int size)
	{
		Assert.ArgInRangeExcl(size, 0, Integer.MAX_VALUE, "size");
		stack = new Object[size];
		top = 0;
	}

	public boolean isEmpty()
	{
		return top == 0;
	}

	public int maxSize()
	{
		return stack.length;
	}

	public void push(T t)
	{
		if (top == stack.length)
			throw new IllegalStateException(
					"Attempted to push too many elements into SimpleStack. Elem: "
							+ t + " stack size: " + stack.length);

		stack[top++] = t;
	}

	public T pop()
	{
		if (isEmpty())
			throw new IllegalStateException(
					"Attempted to pop from an empty SimpleStack.");

		@SuppressWarnings("unchecked")
		T result = (T) stack[--top];
		return result;
	}
}