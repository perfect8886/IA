package cn.gucas.ia.structure;

public class Bag<Item> extends List<Item> {
	public void add(Item item) {
		Node oldHead = head;
		head = new Node();
		head.item = item;
		head.next = oldHead;
		++size;
	}
}
