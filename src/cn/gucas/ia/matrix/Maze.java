package cn.gucas.ia.matrix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * @Description: the answer to http://weibo.com/1915548291/z4eTPtAnv 
 * 		1. create 8 nodes; 
 * 		2. use adjacent list to represent the graph's edges 
 * 		3. search: breadth first search(bfs) / two way breadth first search(bibfs)
 *			cutting strategy:
 *				a. cut repeat state
 *				b. cut the state with decimal output	
 *		4. performance compare
 * @author pengf
 * @date 2012-11-8
 */

public class Maze {
	/* 1. create nodes */
	// 1.1 define operation
	private enum Operation {
		PLUS("+"), MINUS("-"), TIMES("*"), DIVIDE("/");

		private final String symbol;

		Operation(String symbol) {
			this.symbol = symbol;
		}

		@Override
		public String toString() {
			return symbol;
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
		int value;
		Direction direction;
		List<Node> adjacentNodes;

		Node(Operation operation, int value, Direction direction) {
			this.operation = operation;
			this.value = value;
			this.direction = direction;
			this.adjacentNodes = new ArrayList<Node>();
		}

		void addAdjacentNode(Node s) {
			adjacentNodes.add(s);
		}

		double apply(double in) {
			switch (operation) {
			case PLUS:
				return in + value;
			case MINUS:
				return in - value;
			case TIMES:
				return in * value;
			case DIVIDE:
				return in / value;
			}
			throw new AssertionError("Unknown op: " + operation);
		}

		double reverseApply(double in) {
			switch (operation) {
			case PLUS:
				return in - value;
			case MINUS:
				return in + value;
			case TIMES:
				return in / value;
			case DIVIDE:
				return in * value;
			}
			throw new AssertionError("Unknown op: " + operation);
		}

		@Override
		public int hashCode() {
			int result = 17;
			result = 31 * result + operation.ordinal();
			result = 31 * result + value;
			result = 31 * result + direction.ordinal();
			return result;
		}
	}

	// 1.4 define node in graph
	Node plus7Left = new Node(Operation.PLUS, 7, Direction.LEFT);
	Node plus7Right = new Node(Operation.PLUS, 7, Direction.RIGHT);
	Node minus5Left = new Node(Operation.MINUS, 5, Direction.LEFT);
	Node minus5Right = new Node(Operation.MINUS, 5, Direction.RIGHT);
	Node times3Left = new Node(Operation.TIMES, 3, Direction.LEFT);
	Node times3Right = new Node(Operation.TIMES, 3, Direction.RIGHT);
	Node divide2Left = new Node(Operation.DIVIDE, 2, Direction.LEFT);
	Node divide2Right = new Node(Operation.DIVIDE, 2, Direction.RIGHT);

	/* 2 define edges in graph */
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
		double in;
		Node node;
		State prev;

		State(double in, Node node, State prev) {
			this.in = in;
			this.node = node;
			this.prev = prev;
		}

		@Override
		public boolean equals(Object o) {
			return (this.in == ((State) o).in)
					&& (this.node.equals(((State) o).node));
		}

		@Override
		public int hashCode() {
			int result = 17;
			long f = Double.doubleToLongBits(in);
			f = f ^ (f >> 32);
			result = 31 * result + (int) f;
			result = 31 * result + node.hashCode();
			return result;
		}
	}

	// 3.2 breadth first search
	private void bfs(boolean showPath) {
		// init
		State startState = new State(2011, plus7Right, null);
		HashSet<State> visitedStates = new HashSet<State>();
		Queue<State> queue = new LinkedList<State>();
		queue.add(startState);
		visitedStates.add(startState);

		// run
		while (!queue.isEmpty()) {
			State v = queue.poll();
			double out = v.node.apply(v.in);
			if (out == 2012 && (v.node.equals(minus5Right))) {
				// show the path
				if (showPath) {
					showPath(v, true);
				}
				return;
			}
			// cut branches
			if (out - (int) out > 0.0) {
				continue;
			}
			for (Node w : v.node.adjacentNodes) {
				State s = new State(out, w, v);
				if (!visitedStates.contains(s)) {
					visitedStates.add(s);
					queue.add(s);
				}
			}
		}
	}

	// 3.3 two way breadth first search
	private void bibfs(boolean showPath) {
		// forward init
		State startState = new State(2011, plus7Right, null);
		HashMap<Integer, State> forwardVisitedStates = new HashMap<Integer, State>();
		Queue<State> forwardQueue = new LinkedList<State>();
		forwardQueue.add(startState);
		forwardVisitedStates.put(startState.hashCode(), startState);
		// backward init
		State endState = new State(2012, minus5Left, null);
		HashMap<Integer, State> backwardVisitedStates = new HashMap<Integer, State>();
		Queue<State> backwardQueue = new LinkedList<State>();
		backwardQueue.add(endState);
		backwardVisitedStates.put(endState.hashCode(), endState);

		// run
		while (!(forwardQueue.isEmpty() && backwardQueue.isEmpty())) {
			if (forwardQueue.size() <= backwardQueue.size()) {
				State v = forwardQueue.poll();
				double out = v.node.apply(v.in);
				int bvKey = new State(out, v.node, null).hashCode();
				if (backwardVisitedStates.containsKey(bvKey)) {
					// show path
					if (showPath) {
						showPath(v, true);
						State bv = backwardVisitedStates.get(bvKey);
						showPath(bv, false);
					}
					return;
				}
				// cut branches
				if (out - (int) out > 0.0) {
					continue;
				}
				for (Node w : v.node.adjacentNodes) {
					State s = new State(out, w, v);
					int sKey = s.hashCode();
					if (!forwardVisitedStates.containsKey(sKey)) {
						forwardVisitedStates.put(sKey, s);
						forwardQueue.add(s);
					}
				}
			} else {
				State v = backwardQueue.poll();
				double out = v.node.reverseApply(v.in);
				int fvKey = new State(out, v.node, null).hashCode();
				if (forwardVisitedStates.containsKey(fvKey)) {
					// show path
					if (showPath) {
						State fv = forwardVisitedStates.get(fvKey);
						showPath(fv, true);
						showPath(v, false);
					}
					return;
				}
				// cut branches
				if (out - (int) out > 0.0) {
					continue;
				}
				for (Node w : v.node.adjacentNodes) {
					State s = new State(out, w, v);
					int sKey = s.hashCode();
					if (!backwardVisitedStates.containsKey(sKey)) {
						backwardVisitedStates.put(sKey, s);
						backwardQueue.add(s);
					}
				}
			}
		}
	}

	// 3.4 show path
	private void showPath(State s, boolean reverse) {
		if (reverse) {
			Stack<State> path = new Stack<State>();
			while (s != null) {
				path.add(s);
				s = s.prev;
			}
			while (!path.isEmpty()) {
				s = path.pop();
				System.out.println(s.in + " " + s.node.operation + s.node.value
						+ "(" + s.node.direction + ")" + " =");
			}
		} else {
			while (s != null) {
				System.out.println(s.node.operation + "" + s.node.value + "("
						+ s.node.direction + ")" + " = " + s.in);
				s = s.prev;
			}
		}
	}

	public static void main(String[] args) {
		Maze puzzle = new Maze();
		// init graph
		puzzle.initGraph();

		/* 4 performance compare */
		// bfs 
		long startMilli = System.currentTimeMillis();
		puzzle.bfs(false);
		long cost = System.currentTimeMillis() - startMilli;
		System.out.println("bfs cost: " + cost);

		// bibfs
		startMilli = System.currentTimeMillis();
		puzzle.bibfs(false);
		cost = System.currentTimeMillis() - startMilli;
		System.out.println("bibfs cost: " + cost);

		// show path
		// puzzle.bfs(true);
		puzzle.bibfs(true);
	}
}
