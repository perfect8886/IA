package cn.gucas.ia.matrix;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * @Description: 
 * 	the answer to http://weibo.com/1915548291/z4eTPtAnv 
 * 		1. create 8 nodes; 
 * 		2. use adjacent list to represent the graph's edges 
 * 		3. breadth first search
 * @author pengf
 * @date 2012-11-8
 */

public class MatrixPuzzle {
	/* 1. create nodes */
	// 1.1 define operation
	private enum Operation {
		PLUS("+", 7), MINIUS("-", 5), TIMES("*", 3), DIVIDE("/", 2);

		private final int value;
		private final String op;

		Operation(String op, int value) {
			this.op = op;
			this.value = value;
		}

		double apply(double x) {
			switch (this) {
			case PLUS:
				return x + value;
			case MINIUS:
				return x - value;
			case TIMES:
				return x * value;
			case DIVIDE:
				return x / value;
			}
			throw new AssertionError("Unknown operation: " + this);
		}

		@Override
		public String toString() {
			return op + " " + value;
		}
	}

	// 1.2 define direction
	private enum Direction {
		LEFT("<-"), RIGHT("->");

		private final String symbol;

		Direction(String symbol) {
			this.symbol = symbol;
		}

		@Override
		public String toString() {
			return symbol;
		}
	}

	// 1.3 declare node's structure
	private class Node {
		Operation operation;
		Direction direction;
		List<Node> adjacentNodes;

		Node(Operation operation, Direction direction) {
			this.operation = operation;
			this.direction = direction;
			this.adjacentNodes = new ArrayList<Node>();
		}

		void addAdjacentNode(Node s) {
			adjacentNodes.add(s);
		}
	}

	// 1.4 define node in graph
	Node plus7Left = new Node(Operation.PLUS, Direction.LEFT);
	Node plus7Right = new Node(Operation.PLUS, Direction.RIGHT);
	Node minus5Left = new Node(Operation.MINIUS, Direction.LEFT);
	Node minus5Right = new Node(Operation.MINIUS, Direction.RIGHT);
	Node times3Left = new Node(Operation.TIMES, Direction.LEFT);
	Node times3Right = new Node(Operation.TIMES, Direction.RIGHT);
	Node divide2Left = new Node(Operation.DIVIDE, Direction.LEFT);
	Node divide2Right = new Node(Operation.DIVIDE, Direction.RIGHT);

	/* 2. define edges in graph */
	void initGraph() {
		// plus left
		plus7Left.addAdjacentNode(divide2Right);

		// plus right
		plus7Right.addAdjacentNode(times3Right);
		plus7Right.addAdjacentNode(minus5Right);
		plus7Right.addAdjacentNode(divide2Left);

		// minus left
		minus5Left.addAdjacentNode(times3Right);
		minus5Left.addAdjacentNode(divide2Left);
		minus5Left.addAdjacentNode(plus7Left);

		// minus right
		minus5Right.addAdjacentNode(times3Left);

		// times left
		times3Left.addAdjacentNode(plus7Left);
		times3Left.addAdjacentNode(divide2Left);
		times3Left.addAdjacentNode(minus5Right);

		// times right
		times3Right.addAdjacentNode(minus5Left);

		// divide left
		divide2Left.addAdjacentNode(plus7Right);

		// divide right
		divide2Right.addAdjacentNode(minus5Right);
		divide2Right.addAdjacentNode(times3Right);
		divide2Right.addAdjacentNode(plus7Left);
	}

	/* 3 search process */
	// 3.1 declare state's structure, used to record the visited statement
	private class State {
		double input;
		Node node;
		State prev;

		State(double input, Node node, State prev) {
			this.input = input;
			this.node = node;
			this.prev = prev;
		}

		@Override
		public boolean equals(Object o) {
			return (this.input == ((State) o).input)
					&& (this.node.equals(((State) o).node));
		}

		@Override
		public int hashCode() {
			int result = 17;
			long f = Double.doubleToLongBits(input);
			f = f ^ (f >> 32);
			result = 31 * result + (int) f;
			result = 31 * result + node.operation.value;
			return result;
		}
	}

	// 3.2 breadth first search
	private void bfs() {
		// init
		State startState = new State(2011, plus7Right, null);
		HashSet<State> visitedStates = new HashSet<State>();
		Queue<State> queue = new LinkedList<State>();
		queue.add(startState);
		visitedStates.add(startState);

		// run
		while (!queue.isEmpty()) {
			State v = queue.poll();
			double result = v.node.operation.apply(v.input);
			if (result == 2012 && (v.node.equals(minus5Right))) {
				// show the path
				showPath(v);
				return;
			}
			// cut branches
			if (result - (int) result > 0.0) {
				continue;
			}
			for (Node w : v.node.adjacentNodes) {
				State s = new State(result, w, v);
				if (!visitedStates.contains(s)) {
					visitedStates.add(s);
					queue.add(s);
				}
			}
		}
	}

	// 3.3 show path
	private void showPath(State s) {
		Stack<State> path = new Stack<State>();
		while (s != null) {
			path.add(s);
			s = s.prev;
		}
		while (!path.isEmpty()) {
			s = path.pop();
			System.out.println(s.input + " " + s.node.operation + "("
					+ s.node.direction + ")" + " =");
		}
	}

	public static void main(String[] args) {
		MatrixPuzzle puzzle = new MatrixPuzzle();
		puzzle.initGraph();
		puzzle.bfs();
	}
}
