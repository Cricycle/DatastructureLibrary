package alex.structures.test;

import org.testng.annotations.Test;

import alex.structures.SimpleStack;

public class SimpleStackTest
{
	/**
	 * Tests that simple pushing and popping works as expected
	 */
	@Test
	public void SimpleStackBasicPushPop()
	{
		SimpleStack<Integer> stack = new SimpleStack<>(20);

		assert stack.isEmpty();

		stack.push(5);
		stack.push(20);
		stack.push(45);
		assert !stack.isEmpty();

		assert stack.pop() == 45;
		assert stack.pop() == 20;
		assert stack.pop() == 5;
		assert stack.isEmpty();
		assert stack.maxSize() == 20;
	}

	/**
	 * Tests that we can push to the limit of the stack, and that expected data
	 * comes out when pushing and popping repeatedly
	 */
	@Test
	public void SimpleStackFullPush()
	{
		SimpleStack<Integer> stack = new SimpleStack<>(5);

		stack.push(1);
		stack.push(2);
		stack.push(3);
		stack.push(4);
		stack.push(5);
		assert !stack.isEmpty();
		assert stack.pop() == 5;
		assert !stack.isEmpty();
		assert stack.pop() == 4;
		assert !stack.isEmpty();
		assert stack.pop() == 3;
		assert !stack.isEmpty();
		assert stack.pop() == 2;
		assert !stack.isEmpty();
		assert stack.pop() == 1;
		assert stack.isEmpty();

		stack.push(10);
		stack.push(20);
		stack.push(30);
		stack.push(40);
		stack.push(50);
		assert !stack.isEmpty();
		assert stack.pop() == 50;
		assert !stack.isEmpty();
		assert stack.pop() == 40;
		assert !stack.isEmpty();
		assert stack.pop() == 30;
		assert !stack.isEmpty();
		assert stack.pop() == 20;
		assert !stack.isEmpty();
		assert stack.pop() == 10;
		assert stack.isEmpty();
	}

	/**
	 * Tests that we throw an exception when trying to pop incorrectly
	 */
	@Test(expectedExceptions = IllegalStateException.class)
	public void SimpleStackBadPop()
	{
		SimpleStack<Integer> stack = new SimpleStack<>(5);

		assert stack.isEmpty();
		stack.pop();

		assert false;
	}

	/**
	 * Tests that we throw an exception when trying to push incorrectly
	 */
	@Test(expectedExceptions = IllegalStateException.class)
	public void SimpleStackBadPush()
	{
		SimpleStack<String> stack = new SimpleStack<>(2);

		stack.push("one");
		stack.push("two");
		stack.push("toooo many");
		assert false;
	}

	/**
	 * Tests that we throw an exception with bad initial values (negatives)
	 */
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void SimpleStackBadInit1()
	{
		@SuppressWarnings("unused")
		SimpleStack<String> stack = new SimpleStack<>(-123123);
		assert false;
	}

	/**
	 * Tests that we throw an exception with bad initial value of 0
	 */
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void SimpleStackBadInit2()
	{
		@SuppressWarnings("unused")
		SimpleStack<String> stack = new SimpleStack<>(0);
		assert false;
	}
}
