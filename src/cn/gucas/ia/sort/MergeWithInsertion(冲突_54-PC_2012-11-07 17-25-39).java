package cn.gucas.ia.sort;

public class MergeWithInsertion {
	public static void sort(Comparable[] a) {
		int N = a.length;
		Comparable[] aux = new Comparable[N];

		int level = (int) Math.ceil(Math.log(N) / Math.log(2));

		sort(a, aux, 0, N - 1, level);

		System.out.println(level);
		if (level % 2 == 1) {
			a = aux;
		}
	}

	private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi,
			int level) {
		if (hi - lo < 3) {
			for (int i = lo; i <= hi; ++i) {
				for (int j = i; j > lo && less(a[j], a[j - 1]); --j) {
					exch(a, j, j - 1);
				}
			}
			return;
		} else {
			--level;
			int mid = lo + (hi - lo) / 2;
			sort(a, aux, lo, mid, level);
			sort(a, aux, mid + 1, hi, level);
			// if (less(a[mid], a[mid + 1])) {
			// return;
			// }
			if (level % 2 == 0) {
				merge(aux, a, lo, mid, hi);
			} else {
				merge(a, aux, lo, mid, hi);
			}
		}
	}

	private static void merge(Comparable[] a, Comparable[] aux, int lo,
			int mid, int hi) {
		int i = lo;
		int j = mid + 1;

		for (int k = lo; k <= hi; ++k) {
			if (i > mid) {
				aux[k] = a[j++];
			} else if (j > hi) {
				aux[k] = a[i++];
			} else if (less(a[j], a[i])) {
				aux[k] = a[j++];
			} else {
				aux[k] = a[i++];
			}
		}
	}

	private static void exch(Comparable[] a, int i, int j) {
		Comparable t = a[i];
		a[i] = a[j];
		a[j] = t;
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
				"o", "r", "t", "e", "x", "a", "m", "p", "l", "e", "o", "r",
				"t", "e", "x", "a", "m", "p", "l", "e" };
		// String[] a = { "s", "o", "r", "t", "e", "x", "a", "m", "p", "l", "e"
		// };
		MergeWithInsertion.sort(a);
		assert MergeWithInsertion.isSorted(a);
		MergeWithInsertion.show(a);
	}
}
