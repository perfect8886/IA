package cn.gucas.ia.structure;

public class MinPQ<E extends Comparable<E>> extends PQ<E> {
	public MinPQ() {
		super();
	}

	public MinPQ(int N) {
		super(N);
	}

	public MinPQ(E[] t) {
		super(t);
	}

	@Override
	boolean compare(int i, int j) {
		assert i > 0 && i <= size;
		return pq[i].compareTo(pq[j]) > 0;
	}
}
