package cn.gucas.ia.sort;

import cn.gucas.ia.perform.Stopwatch;
import cn.gucas.ia.sort.ArrayUtil.SortMethod;

public class SortCompare {
	public static double time(String alg, Comparable[] a) {
		Stopwatch timer = new Stopwatch();
		if (alg.equals("Insertion")) {
			Insertion.sort(a);
		}
		if (alg.equals("Selection")) {
			Selection.sort(a);
		}
		if (alg.equals("Shell")) {
			Shell.sort(a);
		}
		if (alg.equals("InsertionWithGuard")) {
			InsertionWithGuard.sort(a);
		}
		if (alg.equals("InsertionWithoutExch")) {
			InsertionWithGuard.sort(a);
		}
		if (alg.equals("Merge")) {
			Merge.sort(a);
		}
		if (alg.equals("MergeBU")) {
			MergeBU.sort(a);
		}
		if (alg.equals("MergeWithInprovement")) {
			MergeWithInprovement.sort(a);
		}
		if (alg.equals("NatureMerge")) {
			NatureMerge.sort(a);
		}
		if (alg.equals("Quick")) {
			Quick.sort(a);
		}
		if (alg.equals("Quick3way")) {
			ArrayUtil.sort(a, SortMethod.TUKEY_NINTHER);
			// Quick3way.sort(a);
		}
		return timer.elapsedTime();
	}

	public static double timeRandomInput(String alg, int N, int T) {
		double total = 0.0;
		Double[] a = new Double[N];
		for (int t = 0; t < T; ++t) {
			for (int i = 0; i < N; ++i) {
				a[i] = Math.random();
			}
			total += time(alg, a);
		}
		return total;
	}

	public static void main(String[] args) {
		String alg1 = "Selection";
		String alg2 = "Insertion";
		String alg3 = "Shell";
		String alg4 = "InsertionWithGuard";
		String alg5 = "InsertionWithoutExch";
		String alg6 = "Merge";
		String alg7 = "MergeBU";
		String alg8 = "MergeWithInprovement";
		String alg9 = "NatureMerge";
		String alg10 = "Quick";
		String alg11 = "Quick3way";

		int N = 1000;
		int T = 100;
		// double t1 = timeRandomInput(alg1, N, T);
		// double t2 = timeRandomInput(alg2, N, T);
		// double t3 = timeRandomInput(alg3, N, T);
		// double t4 = timeRandomInput(alg4, N, T);
		// double t5 = timeRandomInput(alg5, N, T);
		double t6 = timeRandomInput(alg6, N, T);
		// double t7 = timeRandomInput(alg7, N, T);
		// double t8 = timeRandomInput(alg8, N, T);
		// double t9 = timeRandomInput(alg9, N, T);
		// double t10 = timeRandomInput(alg10, N, T);
		double t11 = timeRandomInput(alg11, N, T);

		// System.out.printf("For %d random Doubles \n    %s is", N, alg2);
		// System.out.printf(" %.1f times faster than %s \n", t1 / t2, alg1);
		//
		// System.out.printf("For %d random Doubles \n    %s is", N, alg3);
		// System.out.printf(" %.1f times faster than %s \n", t2 / t3, alg2);

		// System.out.printf("For %d random Doubles \n    %s is", N, alg4);
		// System.out.printf(" %.1f times faster than %s \n", t2 / t4, alg2);

		// System.out.printf("For %d random Doubles \n    %s is", N, alg6);
		// System.out.printf(" %.1f times faster than %s \n", t3 / t6, alg3);

		// System.out.printf("For %d random Doubles \n    %s is", N, alg6);
		// System.out.printf(" %.1f times faster than %s \n", t7 / t6, alg7);

		// System.out.printf("For %d random Doubles \n    %s is", N, alg6);
		// System.out.printf(" %.1f times faster than %s \n", t10 / t6, alg10);

		System.out.printf("For %d random Doubles \n    %s is", N, alg6);
		System.out.printf(" %.1f times faster than %s \n", t11 / t6, alg11);
	}
}
