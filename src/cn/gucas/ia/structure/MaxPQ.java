package cn.gucas.ia.structure;

public class MaxPQ<E extends Comparable<E>> extends PQ<E> {
	public MaxPQ() {
		super();
	}

	public MaxPQ(int N) {
		super(N);
	}

	public MaxPQ(E[] t) {
		super(t);
	}

	@Override
	boolean compare(int i, int j) {
		assert i > 0 && i <= size;
		return pq[i].compareTo(pq[j]) < 0;
	}
}
