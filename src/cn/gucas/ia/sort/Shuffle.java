package cn.gucas.ia.sort;

import java.util.Random;

public class Shuffle {
	public static <E> void shuffle(E[] array) {
		int N = array.length;
		E temp = null;
		int random = 0;

		for (int i = 0; i < N; ++i) {
			// get a random int num from i to N-1
			random = new Random().nextInt(N - i) + i;

			// swap
			temp = array[i];
			array[i] = array[random];
			array[random] = temp;
		}
	}

	public static void main(String[] args) {
		// shuffle test
		int M = 10;
		int N = 2000000;
		Integer[] array = new Integer[M];
		int[][] record = new int[M][M];
		for (int i = 0; i < N; ++i) {
			for (int j = 0; j < M; ++j) {
				array[j] = j;
			}
			Shuffle.shuffle(array);

			for (int j = 0; j < M; ++j) {
				++record[array[j]][j];
			}
		}
		for (int i = 0; i < M; ++i) {
			for (int j = 0; j < M; ++j) {
				System.out.print(record[i][j] + " ");
			}
			System.out.println();
		}
	}
}
