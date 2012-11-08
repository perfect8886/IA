package cn.gucas.ia.search;

import java.util.Arrays;

public class BinarySearch {
	public static <E extends Comparable<E>> int rank(E key, E[] array) {
		if (array == null || array.length <= 0) {
			throw new IllegalArgumentException();
		}
		int low = 0;
		int high = array.length - 1;
		while (low <= high) {
			int mid = low + (high - low) / 2;
			if (array[mid].compareTo(key) < 0) {
				low = mid + 1;
			} else if (array[mid].compareTo(key) > 0) {
				high = mid - 1;
			} else {
				return mid;
			}
		}
		return -1;
	}

	public static void main(String[] args) {
		Integer[] array = { 2, 3, 9, 1, -1, 0 };
		Arrays.sort(array);
		Integer key = new Integer(1);
		System.out.println(BinarySearch.rank(key, array));
	}
}
