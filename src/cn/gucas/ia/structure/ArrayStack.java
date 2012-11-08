package cn.gucas.ia.structure;

import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayStack<E> implements Iterable<E> {
	private static final int DEFAULT_INITIAL_CAPACITY = 16;

	// The elements array will contain only E instances from push(E)
	// This is sufficient to ensure type safety, but the runtime
	// type of the array won't be E[]; it will always be Object[]!
	@SuppressWarnings("unchecked")
	private E[] elements = (E[]) new Object[DEFAULT_INITIAL_CAPACITY];;
	private int size = 0;

	public void push(E e) {
		ensureCapacity();
		elements[size++] = e;
	}

	public E pop() {
		if (isEmpty()) {
			throw new EmptyStackException();
		}
		E result = elements[--size];
		elements[size] = null; // eliminate obsolete reference
		ensureUtilization();
		return result;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public boolean isFull() {
		return size == elements.length;
	}

	public int size() {
		return size;
	}

	private final void resize(int len) {
		@SuppressWarnings("unchecked")
		E[] temp = (E[]) new Object[len];
		for (int i = 0; i < size; ++i) {
			temp[i] = elements[i];
		}
		elements = temp;
	}

	private final void ensureCapacity() {
		if (isFull()) {
			// elements = Arrays.copyOf(elements, 2 * size + 1);
			resize(size << 1);
		}
	}

	private final void ensureUtilization() {
		if (size > (DEFAULT_INITIAL_CAPACITY >> 1)
				&& size == (elements.length >> 2)) {
			resize(elements.length >> 1);
		}
	}

	@Override
	public Iterator<E> iterator() {
		return new ReverseArrayIterator();
	}

	private class ReverseArrayIterator implements Iterator<E> {
		private int i = size;

		@Override
		public boolean hasNext() {
			return i > 0;
		}

		@Override
		public E next() {
			if (i <= 0) {
				throw new NoSuchElementException();
			}
			return elements[--i];
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	public static void main(String[] args) {
		ArrayStack<String> stack = new ArrayStack<String>();
		for (String arg : args) {
			stack.push(arg);
		}
		while (!stack.isEmpty()) {
			System.out.println(stack.pop().toUpperCase());
		}
	}
}
