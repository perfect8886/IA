package cn.gucas.ia.sort;

import cn.gucas.ia.structure.Queue;

public class NatureMerge {
	private static Comparable[] aux;

	public static void sort(Comparable[] a) {
		int N = a.length;
		aux = new Comparable[N];

		Queue<Integer> record = new Queue<Integer>();
		record.enqueue(0);
		for (int i = 0; i < N - 1; ++i) {
			if (less(a[i + 1], a[i])) {
				record.enqueue(i);
			}
		}
		record.enqueue(N - 1);

		for (int group = record.size() - 1; group > 1;) {
			int count = group / 2;
			int lo = record.dequeue();
			int mid = record.dequeue();
			int hi = record.dequeue();
			merge(a, lo, mid, hi);
			record.enqueue(lo);
			record.enqueue(hi);

			for (int j = 1; j < count; ++j) {
				lo = hi + 1;
				mid = record.dequeue();
				hi = record.dequeue();
				merge(a, lo, mid, hi);
				record.enqueue(hi);
			}
			if (group % 2 == 0) {
				group /= 2;
			} else {
				group = group / 2 + 1;
				record.enqueue(record.dequeue());
			}
		}
	}

	private static void merge(Comparable[] a, int lo, int mid, int hi) {
		int i = lo;
		int j = mid + 1;

		for (int k = lo; k <= hi; ++k) {
			aux[k] = a[k];
		}
		for (int k = lo; k <= hi; ++k) {
			if (i > mid) {
				a[k] = aux[j++];
			} else if (j > hi) {
				a[k] = aux[i++];
			} else if (less(aux[j], aux[i])) {
				a[k] = aux[j++];
			} else {
				a[k] = aux[i++];
			}
		}
	}

	private static boolean less(Comparable v, Comparable w) {
		return v.compareTo(w) < 0;
	}

	public static void show(Comparable[] a) {
		if (a == null || a.length <= 0) {
			throw new NullPointerException();
		}
		for (int i = 0; i < a.length; ++i) {
			System.out.print(a[i] + " ");
		}
		System.out.println();
	}

	public static boolean isSorted(Comparable[] a) {
		if (a == null || a.length <= 0) {
			throw new NullPointerException();
		}
		for (int i = 1; i < a.length; ++i) {
			if (less(a[i], a[i - 1])) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		String[] a = { "s", "o", "r", "t", "e", "x", "a", "m", "p", "l", "e",
				"e", "x", "a", "m", "p", "l", "e" };
		NatureMerge.sort(a);
		assert NatureMerge.isSorted(a);
		NatureMerge.show(a);
	}
}
