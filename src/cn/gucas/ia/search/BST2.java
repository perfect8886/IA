package cn.gucas.ia.search;

// non-recursion
public class BST2<Key extends Comparable<Key>, Value> {
	private Node root;

	private class Node {
		private Key key;
		private Value value;
		private Node left, right;
		private int size = 1;

		public Node(Key key, Value value) {
			this.key = key;
			this.value = value;
		}
	}

	public int size() {
		return size(root);
	}

	private int size(Node x) {
		if (x == null) {
			return 0;
		} else {
			return x.size;
		}
	}

	public Value get(Key key) {
		Node x = root;
		while (x != null) {
			int cmp = key.compareTo(x.key);
			if (cmp == 0) {
				return x.value;
			} else if (cmp < 0) {
				x = x.left;
			} else if (cmp > 0) {
				x = x.right;
			}
		}
		return null;
	}

	public void put(Key key, Value value) {
		Node x = root;
		Node y = null;
		while (x != null) {
			y = x;
			int cmp = key.compareTo(x.key);
			if (cmp < 0) {
				x = x.left;
			} else if (cmp > 0) {
				x = x.right;
			} else {
				x.value = value;
				return;
			}
		}
		Node z = new Node(key, value);
		if (y == null) {
			root = z;
			return;
		} else if (key.compareTo(y.key) < 0) {
			y.left = z;
		} else {
			y.right = z;
		}

		// update size
		x = root;
		while (x != null) {
			++x.size;
			int cmp = key.compareTo(x.key);
			if (cmp < 0) {
				x = x.left;
			} else {
				x = x.right;
			}
		}
	}

	public static void main(String[] args) {
		BST2<Integer, String> bst = new BST2<Integer, String>();
		System.out.println("size = " + bst.size());
		bst.put(1, "1");
		System.out.println("size = " + bst.size());
		bst.put(2, "3");
		System.out.println(bst.get(2));
		System.out.println("size = " + bst.size());
		bst.put(3, "3");
		bst.put(2, "2");
		System.out.println(bst.get(2));
		System.out.println("size = " + bst.size());
	}
}
