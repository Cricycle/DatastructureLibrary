package alex.structures;

public class LinkedList<T>
{
	private class Node
	{
		public Node next;
		public Node prev;
		public T value;

		public Node(T value)
		{
			this.value = value;
		}
	}

	private final Node EMPTY = new Node(null);
	private int size;

	public LinkedList()
	{
		size = 0;
		EMPTY.prev = EMPTY;
		EMPTY.next = EMPTY;
	}

	public int size()
	{
		return size;
	}

	public boolean isEmpty()
	{
		return size == 0;
	}

	public void insert(T value)
	{
		Node newHead = new Node(value);
		EMPTY.prev.next = newHead; // [] ---> [new] [EMPTY]
		newHead.prev = EMPTY.prev; // [] <--> [new] [EMPTY]
		EMPTY.prev = newHead; // _____[] <--> [new] <--- [EMPTY]
		newHead.next = EMPTY; // _____[] <--> [new] <--> [EMPTY]
		++size;
	}

	public boolean delete(T value)
	{
		return delete(value, true);
	}

	private boolean delete(T value, boolean backward)
	{
		Node node = null;
		if (backward)
		{
			node = privateContainsBackward(value);
		}
		else
		{
			node = privateContainsForward(value);
		}

		if (node == null)
			return false;

		node.prev.next = node.next;
		node.next.prev = node.prev;
		--size;
		return true;
	}

	public boolean contains(T value)
	{
		Node result = privateContainsBackward(value);
		return result != null;
	}

	public T peek()
	{
		return EMPTY.prev.value;
	}

	public T peekLast()
	{
		return EMPTY.next.value;
	}

	public void push(T value)
	{
		insert(value);
	}

	public T pop()
	{
		T value = peek();
		delete(value, true);
		return value;
	}

	public void enqueue(T value)
	{
		insert(value);
	}

	public T dequeue()
	{
		T value = peekLast();
		delete(value, false);
		return value;
	}

	// Traverses the list from Head to Tail
	private Node privateContainsBackward(T value)
	{
		Node curNode = EMPTY.prev;
		while (curNode != EMPTY)
		{
			if (curNode.value.equals(value))
			{
				return curNode;
			}

			curNode = curNode.prev;
		}

		return null;
	}

	// Traverses the list from Tail to Head
	private Node privateContainsForward(T value)
	{
		Node curNode = EMPTY.next;
		while (curNode != EMPTY)
		{
			if (curNode.value.equals(value))
			{
				return curNode;
			}

			curNode = curNode.next;
		}

		return null;
	}
}
