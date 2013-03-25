package cn.gucas.ia.sort;

public class ArrayUtil {
	public enum SortMethod {
		TUKEY_NINTHER;
	}

	public static <T extends Comparable<T>> void sort(T[] a, SortMethod sm) {
		switch (sm) {
		case TUKEY_NINTHER:
			TNSort(a);
		default:
			TNSort(a);
		}
	}

	private static <T extends Comparable<T>> void TNSort(T[] a) {
		TNSort(a, 0, a.length - 1);
	}

	private static <T extends Comparable<T>> void TNSort(T[] a, int lo, int hi) {
		// Insertion sort on smallest arrays
		if (hi < lo + 6) {
			for (int i = lo; i <= hi; ++i) {
				for (int j = i; j > lo && a[j].compareTo(a[j - 1]) < 0; --j) {
					swap(a, j, j - 1);
				}
			}
			return;
		}

		// choose a partition element
		int m = lo + (hi - lo) / 2;
		int len = hi - lo + 1;
		if (len > 40) {
			// Big arrays, pseudomedian of 9
			int s = len / 8;
			int l = median3(a, lo, lo + s, lo + 2 * s);
			m = median3(a, m - s, m, m + s);
			int h = median3(a, hi - 2 * s, hi - s, hi);
			m = median3(a, l, m, h);
		}
		T v = a[m];

		// Establish Invariant: v* (<v)* (>v)* v*
		int p = lo, i = p, q = hi, j = q;
		while (true) {
			while (i <= q && a[i].compareTo(v) <= 0) {
				if (a[i].equals(v)) {
					swap(a, p++, i);
				}
				++i;
			}
			while (j >= p && a[j].compareTo(v) >= 0) {
				if (a[j].equals(v)) {
					swap(a, q--, j);
				}
				--j;
			}
			if (i > j) {
				break;
			}
			swap(a, i++, j--);
		}

		// Swap partition elements back to middle
		int subLen = 0;
		subLen = Math.min(p - lo, i - p);
		swapSubArray(a, lo, i - subLen, subLen);
		subLen = Math.min(q - j, hi - q);
		swapSubArray(a, i, hi - subLen + 1, subLen);

		// Recursively sort non-partition-elements
		if ((subLen = i - p) > 1) {
			TNSort(a, lo, lo + subLen - 1);
		}
		if ((subLen = q - j) > 1) {
			TNSort(a, hi - subLen + 1, hi);
		}
	}

	private static <T> void swap(T[] a, int i, int j) {
		T t = a[i];
		a[i] = a[j];
		a[j] = t;
	}

	private static <T> void swapSubArray(T[] a, int x, int y, int len) {
		for (int i = 0; i < len; ++i, ++x, ++y) {
			swap(a, x, y);
		}
	}

	private static <T extends Comparable<T>> int median3(T[] a, int x, int y,
			int z) {
		return (a[x].compareTo(a[y]) < 0 ? (a[y].compareTo(a[z]) < 0 ? y : a[x]
				.compareTo(a[z]) < 0 ? z : x) : (a[y].compareTo(a[z]) > 0 ? y
				: a[x].compareTo(a[z]) > 0 ? z : x));
	}

	public static void main(String[] args) {
		String[] a = { "s", "o", "r", "t", "e", "x", "a", "m", "p", "l", "e" };
		ArrayUtil.sort(a, SortMethod.TUKEY_NINTHER);
		for (String s : a) {
			System.out.print(s + " ");
		}
	}
}
