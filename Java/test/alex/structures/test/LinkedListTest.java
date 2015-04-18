package alex.structures.test;

import org.testng.annotations.Test;

import alex.structures.LinkedList;

public class LinkedListTest
{
	@Test
	public void LinkedListInsertRemoveTest()
	{
		LinkedList<String> list = new LinkedList<>();

		assert list.size() == 0;
		assert list.isEmpty();

		list.insert("str1");
		assert list.size() == 1;
		assert !list.isEmpty();

		list.delete("str1");
		assert list.size() == 0;
		assert list.isEmpty();
	}

	@Test
	public void LinkedListBadDeleteTest()
	{
		LinkedList<String> list = new LinkedList<>();

		// nothing bad should happen
		list.delete("some value");
	}
}
