package cn.gucas.ia.sort;

public class InsertionWithoutExch {
	public static void sort(Comparable[] a) {
		if (a == null || a.length <= 0) {
			throw new NullPointerException();
		}
		for (int i = 1; i < a.length; ++i) {
			int j = i;
			Comparable temp = a[i];
			while (j > 0 && less(temp, a[j - 1])) {
				rightShift(a, j - 1);
				--j;
			}
			// for (j = i; j > 0 && less(temp, a[j - 1]); --j) {
			// rightShift(a, j - 1);
			// }
			a[j] = temp;
		}
	}

	private static void rightShift(Comparable[] a, int i) {
		if (a == null || a.length <= 0) {
			throw new NullPointerException();
		}
		if (i < 0 || i >= a.length - 1) {
			throw new IllegalArgumentException();
		}
		a[i + 1] = a[i];
	}

	private static boolean less(Comparable v, Comparable w) {
		if (v == null || w == null) {
			throw new NullPointerException();
		}
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
		InsertionWithoutExch.sort(a);
		assert InsertionWithoutExch.isSorted(a);
		InsertionWithoutExch.show(a);
	}
}
