package alex.structures;

import alex.checks.*;

public class DynamicStack<T>
{
	private Object[] stack;
	
	private int top;
	private float upsizeRatio;
	private float downsizeRatio;
	private boolean doUpsize;
	private boolean doDownsize;
	
	private static final int MIN_SIZE = 10;
	private static final float TARGET_DOWN = 0.75f;
	
	public DynamicStack()
	{
		this(10);
	}
	
	public DynamicStack(int initialSize)
	{
		this(10, 2.0f, 0.5f, true, true);
	}
	
	public DynamicStack(int initialSize, float upsizeRatio, float downsizeRatio, boolean doUpsize, boolean doDownsize)
	{
		Assert.ArgInRangeIncl(initialSize, 1, Integer.MAX_VALUE, "initialSize");
		Assert.ArgInRangeExcl(upsizeRatio, 1.0f, 10.0f, "upsizeRatio");
		Assert.ArgInRangeExcl(downsizeRatio, 0.1f, 1.0f, "downsizeRatio");
	
		stack = new Object[initialSize];
		top = 0;
		this.upsizeRatio = upsizeRatio;
		this.downsizeRatio = downsizeRatio;
		this.doUpsize = doUpsize;
		this.doDownsize = doDownsize;
	}
	
	public boolean isEmpty()
	{
		return top == 0;
	}
	
	public void push(T t)
	{
		if (top == stack.length)
			resizeUp(t);
		
		stack[top++] = t;
	}
	
	public T pop()
	{
		if (isEmpty())
			throw new IllegalStateException("Attempted to pop from an empty DynamicStack.");
		
		resizeDown();
		@SuppressWarnings("unchecked")
		T result = (T)stack[--top];
		
		return result;
	}
	
	private void resizeUp(T t)
	{
		if (doUpsize)
		{
			int newSize = (int)Math.ceil(stack.length * upsizeRatio);
			assert newSize > stack.length : String.format("stack size: %d, upsizeRatio: %.6f, newSize: %d", stack.length, upsizeRatio, newSize);
			
			Object[] tempStack = new Object[newSize];
			System.arraycopy(stack, 0, tempStack, 0, top);
			stack = tempStack;
		}
		
		if (top == stack.length)
			throw new IllegalStateException("Attempted to push to a full DynamicStack. Size: " + stack.length + ", doUpsize: " + doUpsize + ", upsizeRatio: " + upsizeRatio + ". Elem: " + t);
	}
	
	private void resizeDown()
	{
		if (!doDownsize)
			return;
		
		if (top >= MIN_SIZE && top <= downsizeRatio * stack.length)
		{
			int newSize = (int)Math.ceil(top / TARGET_DOWN);
			assert newSize > top && newSize < stack.length : String.format("top < newSize < stack.length = %d < %d < %d", top, newSize, stack.length);
			
			Object[] tempStack = new Object[newSize];
			System.arraycopy(stack, 0, tempStack, 0, top);
			stack = tempStack;
		}
	}
}