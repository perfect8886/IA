package cn.gucas.ia.structure;

/**
 * Algorithms 4th Edition, 1.5.13 
 * weighted quick-union with path compression
 * @author pengf@dsp.ac.cn
 */

public class QuickUnion {
	private int[] father; // the father's index of each node
	private int[] size; // the size of each node
	private int count; // num of components
	private int N; // num of nodes

	public QuickUnion(int N) {
		if (N <= 0) {
			throw new IllegalArgumentException();
		}
		this.count = N;
		this.N = N;
		father = new int[N];
		size = new int[N];
		for (int i = 0; i < N; ++i) {
			father[i] = i;
			size[i] = 1;
		}
	}

	public int count() {
		return count;
	}

	public boolean connected(int p, int q) {
		if (p < 0 || p >= N || q < 0 || q >= N) {
			throw new IllegalArgumentException();
		}
		return find(p) == find(q);
	}

	private int find(int p) {
		if (p < 0 || p >= N) {
			throw new IllegalArgumentException();
		}
		/* recusion, but can not control the size array */
		// if (p != father[p]) {
		// --size[father[p]];
		// father[p] = find(father[p]);
		// ++size[father[p]];
		// }
		// return father[p];

		/* non-recursion */
		int pRoot = father[p];
		while (pRoot != father[pRoot]) {
			pRoot = father[pRoot];
		}
		int temp = father[p];
		while (temp != pRoot) {
			father[p] = pRoot;
			--size[temp];
			p = temp;
			temp = father[p];
		}
		return pRoot;
	}

	public void union(int p, int q) {
		if (p < 0 || p >= N || q < 0 || q >= N) {
			throw new IllegalArgumentException();
		}
		int pRoot = find(p);
		int qRoot = find(q);
		if (pRoot == qRoot) {
			return;
		} else if (size[pRoot] > size[qRoot]) {
			father[qRoot] = pRoot;
			size[pRoot] += size[qRoot];
			--size[qRoot];
		} else {
			father[pRoot] = qRoot;
			size[qRoot] += size[pRoot];
			--size[pRoot];
		}
		--count;
	}

	public void printState() {
		System.out.println();
		System.out.println("**************************************");
		System.out.print("  father array: ");
		for (int i = 0; i < N; ++i) {
			System.out.print(father[i] + " ");
		}
		System.out.println();
		System.out.print("    size array: ");
		for (int i = 0; i < N; ++i) {
			System.out.print(size[i] + " ");
		}
		System.out.println();
		System.out.println("components num: " + count);
		System.out.println("**************************************");
		System.out.println();
	}

	public static void main(String[] args) {
		QuickUnion qu = new QuickUnion(10);
		qu.union(0, 2);
		qu.union(0, 1);
		qu.union(3, 1);
		qu.printState();

		qu.union(9, 8);
		qu.union(8, 7);
		qu.union(7, 6);
		qu.printState();

		qu.union(4, 5);
		qu.printState();

		qu.union(4, 0);
		qu.printState();

		qu.union(8, 0);
		qu.printState();

		qu.union(4, 8);
		qu.printState();
	}
}
