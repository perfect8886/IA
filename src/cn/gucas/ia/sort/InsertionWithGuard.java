package cn.gucas.ia.sort;

public class InsertionWithGuard {
	public static void sort(Comparable[] a) {
		if (a == null || a.length <= 0) {
			throw new NullPointerException();
		}
		int N = a.length;
		int minIndex = 0;
		for (int i = 1; i < N; ++i) {
			if (less(a[i], a[minIndex])) {
				minIndex = i;
			}
		}
		exch(a, minIndex, 0);

		for (int i = 1; i < N; ++i) {
			for (int j = i; less(a[j], a[j - 1]); --j) {
				exch(a, j, j - 1);
			}
		}
	}

	private static boolean less(Comparable v, Comparable w) {
		if (v == null || w == null) {
			throw new NullPointerException();
		}
		return v.compareTo(w) < 0;
	}

	private static void exch(Comparable[] a, int i, int j) {
		if (a == null || a.length <= 0) {
			throw new NullPointerException();
		}
		if (i < 0 || i >= a.length || j < 0 || j >= a.length) {
			throw new IllegalArgumentException();
		}
		Comparable t = a[i];
		a[i] = a[j];
		a[j] = t;
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
		String[] a = { "s", "o", "r", "t", "e", "x", "a", "m", "p", "l", "e" };
		InsertionWithGuard.sort(a);
		assert InsertionWithGuard.isSorted(a);
		InsertionWithGuard.show(a);
	}
}
