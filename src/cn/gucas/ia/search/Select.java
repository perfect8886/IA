package cn.gucas.ia.search;

import cn.gucas.ia.sort.Shuffle;

public class Select {
	public static Comparable select(Comparable[] a, int k) {
		Shuffle.shuffle(a);
		int lo = 0, hi = a.length - 1;
		while (hi > lo) {
			int j = partition(a, lo, hi);
			if (j == k)
				return a[k];
			else if (j > k)
				hi = j - 1;
			else if (j < k)
				lo = j + 1;
		}
		return a[k];
	}

	private static int partition(Comparable[] a, int lo, int hi) {
		int i = lo, j = hi + 1;
		Comparable v = a[lo];
		while (true) {
			while (less(a[++i], v)) {
				if (i == hi) {
					break;
				}
			}
			while (less(v, a[--j])) {
			}
			if (i >= j) {
				break;
			}
			exch(a, i, j);
		}
		exch(a, lo, j);
		return j;
	}

	private static boolean less(Comparable v, Comparable w) {
		return v.compareTo(w) < 0;
	}

	private static void exch(Comparable[] a, int i, int j) {
		Comparable t = a[i];
		a[i] = a[j];
		a[j] = t;
	}

	public static void main(String[] args) {
		String[] a = { "a", "b", "c", "d", "e", "f", "g" };
		System.out.println(Select.select(a, 1));
		System.out.println(Select.select(a, 0));
		System.out.println(Select.select(a, 5));
	}
}
