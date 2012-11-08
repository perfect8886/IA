package cn.gucas.ia.structure;

import java.util.NoSuchElementException;

public class Queue<Item> extends List<Item> {
	private Node tail;

	public void enqueue(Item item) {
		Node oldTail = tail;
		tail = new Node();
		tail.item = item;
		tail.next = null;
		if (isEmpty()) {
			head = tail;
		} else {
			oldTail.next = tail;
		}
		++size;
	}

	public Item dequeue() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}

		Item item = head.item;
		head = head.next;
		--size;
		if (isEmpty()) {
			tail = null;
		}
		return item;
	}
}
