package cn.gucas.ia.sort;

public class Quick {
	public static void sort(Comparable[] a) {
		// Shuffle.shuffle(a);
		sort(a, 0, a.length - 1);
	}

	private static void sort(Comparable[] a, int lo, int hi) {
		if (hi <= lo + 7) {
			/* improvement 1: use insertion when subarray's length is small */
			for (int i = lo; i <= hi; ++i) {
				for (int j = i; j > lo && less(a[j], a[j - 1]); --j) {
					exch(a, j, j - 1);
				}
			}
			return;
		}
		int j = partition(a, lo, hi);
		sort(a, lo, j - 1);
		sort(a, j + 1, hi);
	}

	private static int partition(Comparable[] a, int lo, int hi) {
		int mid = lo + (hi - lo) / 2;
		// get the median
		int median = getMedian3(a, lo, mid, hi);
		exch(a, median, lo);

		int i = lo, j = hi + 1;
		Comparable v = a[lo];
		while (true) {
			while (less(a[++i], v)) {
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

	private static int getMedian3(Comparable[] a, int x, int y, int z) {
		return less(a[x], a[y]) ? (less(a[y], a[z]) ? y : less(a[x], a[z]) ? z
				: x) : (less(a[z], a[y]) ? y : less(a[z], a[x]) ? z : x);
	}

	private static boolean less(Comparable v, Comparable w) {
		return v.compareTo(w) < 0;
	}

	private static void exch(Comparable[] a, int i, int j) {
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
		Quick.sort(a);
		assert Quick.isSorted(a);
		Quick.show(a);
	}
}
