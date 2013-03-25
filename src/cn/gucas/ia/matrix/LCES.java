package cn.gucas.ia.matrix;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/*Given an unsorted array of integers, find the length of the longest 
 consecutive elements sequence. Eg, given [100, 2, 1, 3], The longest 
 consecutive elements sequence is [1, 2, 3]. Return its length: 3. 
 Your algorithm should run in O(n).*/
public class LCES {
	private int[] array;

	public LCES(int[] array) {
		if (array == null || array.length == 0) {
			throw new IllegalArgumentException();
		}
		this.array = array;
	}

	class Record {
		Map<Integer, Integer> forwardRecord = new HashMap<Integer, Integer>();
		Map<Integer, Integer> backwardRecord = new HashMap<Integer, Integer>();
	}

	public int search() {
		Record record = subSearch(0, array);

		int max = 0;
		int start = -1;
		Set<Integer> keys = record.forwardRecord.keySet();
		for (Integer key : keys) {
			int len = record.forwardRecord.get(key);
			if (len > max) {
				max = len;
				start = key;
			}
		}
		for (int i = start, l = 0; l < max; ++i, ++l) {
			System.out.print(i);
			if (l < max - 1) {
				System.out.print(", ");
			} else {
				System.out.println();
			}
		}
		System.out.println("len: " + max);
		return max;
	}

	private Record subSearch(int startIndex, int[] array) {
		Record record = null;
		if (startIndex >= array.length - 1) {
			record = new Record();
			record.forwardRecord.put(array[startIndex], 1);
			record.backwardRecord.put(array[startIndex], 1);
			return record;
		}
		record = subSearch(startIndex + 1, array);

		boolean fc = record.forwardRecord.containsKey(array[startIndex] + 1);
		boolean bc = record.backwardRecord.containsKey(array[startIndex] - 1);
		if (fc && bc) {
			int start = array[startIndex]
					- record.backwardRecord.get(array[startIndex] - 1);
			int len = record.backwardRecord.get(array[startIndex] - 1) + 1
					+ record.forwardRecord.get(array[startIndex] + 1);
			record.forwardRecord.put(start, len);
			record.backwardRecord.put(start + len - 1, len);
		} else if (fc && !bc) {
			int start = array[startIndex];
			int len = record.forwardRecord.get(array[startIndex] + 1) + 1;
			record.forwardRecord.put(start, len);
			record.backwardRecord.put(start + len - 1, len);
		} else if (!fc && bc) {
			int end = array[startIndex];
			int len = record.backwardRecord.get(array[startIndex] - 1) + 1;
			record.backwardRecord.put(end, len);
			record.forwardRecord.put(end - len + 1, len);
		} else {
			record.forwardRecord.put(array[startIndex], 1);
			record.backwardRecord.put(array[startIndex], 1);
		}

		return record;
	}

	public static void main(String[] args) {
		int[] array = { 101, 102, 99, 97, 96, 98, 94, 95, 100, 50, 56, 57, 1,
				2, 4, 5, 6, 7, 8, 9 };
		LCES lces = new LCES(array);
		lces.search();
	}
}
