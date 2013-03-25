package cn.gucas.ia.structure;

import java.util.NoSuchElementException;

public abstract class PQ<E extends Comparable<E>> {
	private static final int DEFAULT_INITIAL_CAPACITY = 16;
	E[] pq;
	int size = 0; // stored in pq[1..N], pq[0] not in use

	@SuppressWarnings("unchecked")
	public PQ() {
		pq = (E[]) new Comparable[DEFAULT_INITIAL_CAPACITY + 1];
	}

	@SuppressWarnings("unchecked")
	public PQ(int N) {
		if (N <= 0) {
			throw new IllegalArgumentException();
		}
		pq = (E[]) new Comparable[N + 1];
	}

	@SuppressWarnings("unchecked")
	public PQ(E[] array) {
		if (array == null || array.length <= 0) {
			throw new NullPointerException();
		}
		size = array.length;
		pq = (E[]) new Comparable[size + 1];
		for (int i = 0; i < size; ++i) {
			pq[i + 1] = array[i];
		}
		buildHeap();
	}

	public boolean isFull() {
		return size == pq.length - 1;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public int size() {
		return size;
	}

	public void enqueue(E e) {
		if (e == null) {
			throw new NullPointerException();
		}
		ensureCapacity();
		pq[++size] = e;
		swim(size);
	}

	public E dequeue() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		E max = pq[1];
		swap(1, size--);
		pq[size + 1] = null; // eliminate obsolete reference
		sink(1);
		ensureUtilization();
		return max;
	}

	public E peek() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		return pq[1];
	}

	private void buildHeap() {
		for (int i = parent(size); i >= 1; --i) {
			sink(i);
		}
	}

	private void swap(int i, int j) {
		assert i > 0 && i <= size;
		assert j > 0 && j <= size;
		E temp = pq[i];
		pq[i] = pq[j];
		pq[j] = temp;
	}

	abstract boolean compare(int i, int j);

	private void swim(int k) {
		assert k > 0 && k <= size;
		while (hasParent(k) && compare(parent(k), k)) {
			swap(parent(k), k);
			k = parent(k);
		}
	}

	private void sink(int k) {
		assert k > 0 && k <= size;
		while (hasLeftChild(k)) {
			int j = leftChild(k);
			if (j < size && compare(j, rightChild(k))) {
				++j;
			}
			if (compare(j, k)) {
				break;
			}
			swap(k, j);
			k = j;
		}
	}

	private void resize(int len) {
		assert len > 0;
		@SuppressWarnings("unchecked")
		E[] temp = (E[]) new Comparable[len + 1];
		for (int i = 1; i <= size; ++i) {
			temp[i] = pq[i];
		}
		pq = temp;
	}

	private void ensureCapacity() {
		if (isFull()) {
			resize(size << 1);
		}
	}

	private void ensureUtilization() {
		if (size > (DEFAULT_INITIAL_CAPACITY >> 1)
				&& size == ((pq.length - 1) >> 2)) {
			resize((pq.length - 1) >> 1);
		}
	}

	private int parent(int k) {
		assert k > 1 && k <= size;
		return k / 2;
	}

	private int leftChild(int k) {
		assert hasLeftChild(k);
		return 2 * k;
	}

	private int rightChild(int k) {
		assert hasRightChild(k);
		return 2 * k + 1;
	}

	private boolean hasLeftChild(int k) {
		return k > 0 && k <= size / 2;
	}

	private boolean hasRightChild(int k) {
		return k > 0 && k <= (size - 1) / 2;
	}

	private boolean hasParent(int k) {
		return k > 1 && k <= size;
	}
}
