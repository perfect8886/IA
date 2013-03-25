package cn.gucas.ia.sort;

import java.util.HashMap;

public class KendallTau {
	public static <E> long distance(E[] a, E[] b) {
		if (a.length != b.length) {
			throw new IllegalArgumentException(" Array dimensions disagree");
		}
		int N = a.length;

		HashMap<E, Integer> indexMap4a = new HashMap<E, Integer>();
		for (int i = 0; i < N; ++i) {
			indexMap4a.put(a[i], i);
		}

		int[] indexSeq4b = new int[N];
		for (int i = 0; i < N; ++i) {
			indexSeq4b[i] = indexMap4a.get(b[i]);
		}

		int[] aux = new int[N]; // used for merge sort
		return countInversion(indexSeq4b, aux, 0, N - 1);
	}

	private static long countInversion(int[] b, int[] aux, int lo, int hi) {
		if (lo >= hi) {
			return 0;
		}
		int mid = lo + (hi - lo) / 2;
		return countInversion(b, aux, lo, mid)
				+ countInversion(b, aux, mid + 1, hi)
				+ merge(b, aux, lo, mid, hi);
	}

	private static long merge(int[] b, int[] aux, int lo, int mid, int hi) {
		int i = lo;
		int j = mid + 1;
		int count = 0;

		for (int k = lo; k <= hi; ++k) {
			aux[k] = b[k];
		}
		for (int k = lo; k <= hi; ++k) {
			if (i > mid) {
				b[k] = aux[j++];
			} else if (j > hi) {
				b[k] = aux[i++];
			} else if (aux[j] < aux[i]) {
				b[k] = aux[j++];
				count += mid - i + 1;
			} else {
				b[k] = aux[i++];
			}
		}
		return count;
	}

	public static void main(String[] args) {
		String[] a = { "a", "b", "c", "d", "e" };
		String[] b = { "a", "b", "c", "d", "e" };
		Shuffle.shuffle(a);
		Shuffle.shuffle(b);
		for (String s : a) {
			System.out.print(s + " ");
		}
		System.out.println();

		for (String s : b) {
			System.out.print(s + " ");
		}
		System.out.println();

		System.out.println("Kendall's tau: " + KendallTau.distance(a, b));
	}
}
