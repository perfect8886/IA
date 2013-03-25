package cn.gucas.ia.structure;

public abstract class IndexPQ<E extends Comparable<E>> {
	private int size = 0;
	private int[] pq;
	private int[] qp;
	private E[] elements;

	@SuppressWarnings("unchecked")
	public IndexPQ(int N) {
		elements = (E[]) new Comparable[N + 1];
		pq = new int[N + 1];
		qp = new int[N + 1];
		for (int i = 0; i <= N; ++i) {
			qp[i] = -1;
		}
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public boolean contains(int k) {
		if (k < 0 || k >= qp.length) {
			throw new IllegalArgumentException();
		}
		return qp[k] != -1;
	}

	public void insert(int k, E e) {
		size++;
		qp[k] = size;
		pq[size] = k;
		elements[k] = e;
		swim(size);
	}

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

	private void swap(int i, int j) {
		assert i > 0 && i <= size;
		assert j > 0 && j <= size;
		int temp = pq[i];
		pq[i] = pq[j];
		pq[j] = temp;
	}

	abstract boolean compare(int i, int j);

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
