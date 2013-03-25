package cn.gucas.ia.search;

import cn.gucas.ia.structure.MaxPQ;
import cn.gucas.ia.structure.MinPQ;

/**
 * @Description: dynamic median search, the answer to Algorithms 4th 2.4.30
 * @author perfect8886@hotmail.com
 * @date 2012-11-16
 */
public class DynamicMedian<E extends Comparable<E>> {
	private MaxPQ<E> firstHalf = new MaxPQ<E>();
	private MinPQ<E> lastHalf = new MinPQ<E>();
	private int size = 0;

	public void add(E e) {
		if (e == null) {
			throw new NullPointerException();
		}
		if (firstHalf.isEmpty()) {
			firstHalf.enqueue(e);
			return;
		}
		if (lastHalf.isEmpty()) {
			lastHalf.enqueue(e);
			return;
		}
		E firstMax = firstHalf.peek();
		E lastMin = lastHalf.peek();
		if (isEven()) { // insert
			if (e.compareTo(firstMax) <= 0) {
				firstHalf.enqueue(e);
			} else {
				lastMin = lastHalf.dequeue();
				firstHalf.enqueue(lastMin);
				lastHalf.enqueue(e);
			}
		} else {
			if (e.compareTo(firstMax) > 0) {
				lastHalf.enqueue(e);
			} else {
				firstMax = firstHalf.dequeue();
				lastHalf.enqueue(firstMax);
				firstHalf.enqueue(e);
			}
		}
		++size;
	}

	public E peek() {
		return firstHalf.peek();
	}

	public E del() {
		E median = firstHalf.dequeue();
		if (isEven()) {
			E lastMin = lastHalf.dequeue();
			firstHalf.enqueue(lastMin);
		}
		--size;
		return median;
	}

	private boolean isEven() {
		return size % 2 == 0;
	}

	public static void main(String[] args) {
		DynamicMedian<Integer> dm = new DynamicMedian<Integer>();
		for (int i = 0; i < 100; ++i) {
			dm.add(i);
		}
		System.out.println("del: ");
		System.out.println(dm.del());
		System.out.println("peek: ");
		System.out.println(dm.peek());
		System.out.println("del: ");
		System.out.println(dm.del());
		System.out.println("peek: ");
		System.out.println(dm.peek());
	}
}
