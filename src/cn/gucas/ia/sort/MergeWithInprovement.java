package cn.gucas.ia.sort;

public class MergeWithInprovement {
	public static void sort(Comparable[] a) {
		int N = a.length;
		Comparable[] aux = new Comparable[N];
		for (int i = 0; i < N; ++i) {
			aux[i] = a[i];
		}
		sort(a, aux, 0, N - 1);
	}

	private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
		if (hi - lo < 7) {
			/* improvement 1: use insertion when subarray's length is small */
			for (int i = lo; i <= hi; ++i) {
				for (int j = i; j > lo && less(a[j], a[j - 1]); --j) {
					exch(a, j, j - 1);
				}
			}
			return;
		} else {
			int mid = lo + (hi - lo) / 2;
			/*
			 * improvement 2: change the role of a and aux, so as to avoid extra
			 * array copy
			 */
			sort(aux, a, lo, mid);
			sort(aux, a, mid + 1, hi);
			/*
			 * improvement 3: check whether it is already in sort, but this
			 * improvement can't work with improvement 3
			 */
			// if (less(a[mid], a[mid + 1])) {
			// return;
			// }
			merge(aux, a, lo, mid, hi);
		}
	}

	private static void merge(Comparable[] a, Comparable[] aux, int lo,
			int mid, int hi) {
		int i = lo;
		int j = mid + 1;

		/* this part of code should be added when using improvement 2 */
		// for (int k = lo; k <= hi; ++k) {
		// aux[k] = a[k];
		// }
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
		String[] a = { "s", "o", "r", "t", "e", "x", "a", "m", "p", "l", "e" };
		MergeWithInprovement.sort(a);
		assert MergeWithInprovement.isSorted(a);
		MergeWithInprovement.show(a);
	}
}
