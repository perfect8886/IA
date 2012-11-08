package cn.gucas.ia.structure;

import java.util.NoSuchElementException;

public class Stack<Item> extends List<Item> {
	public void push(Item item) {
		Node oldHead = head;
		head = new Node();
		head.item = item;
		head.next = oldHead;
		++size;
	}

	public Item pop() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		Item item = head.item;
		head = head.next;
		--size;
		return item;
	}
}
