package cn.gucas.ia.matrix;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import cn.gucas.ia.structure.Stack;

/**
 * @Description: the answer to http://weibo.com/1915548291/z4eTPtAnv
 * @author pengf
 * @date 2012-11-8
 */

public class MatrixPuzzle {
	// define operation
	private enum OPERATION {
		PLUS("+", 7), MINIUS("-", 5), TIMES("*", 3), DIVIDE("/", 2);

		private final int value;
		private final String op;

		OPERATION(String op, int value) {
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

	// define direction
	private enum DIRECTION {
		LEFT, RIGHT;
	}

	// declare node's structure
	private class Node {
		OPERATION operation;
		DIRECTION direction;
		List<Node> nextStates;

		Node(OPERATION operation, DIRECTION direction) {
			this.operation = operation;
			this.direction = direction;
			this.nextStates = new ArrayList<Node>();
		}

		void addNextState(Node s) {
			nextStates.add(s);
		}
	}

	// define node in graph
	Node plusLeft = new Node(OPERATION.PLUS, DIRECTION.LEFT);
	Node plusRight = new Node(OPERATION.PLUS, DIRECTION.RIGHT);
	Node minusLeft = new Node(OPERATION.MINIUS, DIRECTION.LEFT);
	Node minusRight = new Node(OPERATION.MINIUS, DIRECTION.RIGHT);
	Node timesLeft = new Node(OPERATION.TIMES, DIRECTION.LEFT);
	Node timesRight = new Node(OPERATION.TIMES, DIRECTION.RIGHT);
	Node divideLeft = new Node(OPERATION.DIVIDE, DIRECTION.LEFT);
	Node divideRight = new Node(OPERATION.DIVIDE, DIRECTION.RIGHT);

	// define edge in graph
	void initGraph() {
		// plus left
		plusLeft.addNextState(divideRight);

		// plus right
		plusRight.addNextState(timesRight);
		plusRight.addNextState(minusRight);
		plusRight.addNextState(divideLeft);

		// minus left
		minusLeft.addNextState(timesRight);
		minusLeft.addNextState(divideLeft);
		minusLeft.addNextState(plusLeft);

		// minus right
		minusRight.addNextState(timesLeft);

		// times left
		timesLeft.addNextState(plusLeft);
		timesLeft.addNextState(divideLeft);
		timesLeft.addNextState(minusRight);

		// times right
		timesRight.addNextState(minusLeft);

		// divide left
		divideLeft.addNextState(plusRight);

		// divide right
		divideRight.addNextState(minusRight);
		divideRight.addNextState(timesRight);
		divideRight.addNextState(plusLeft);
	}

	// declare statement
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
			result = 31 * result + node.operation.value;
			result = (int) (31 * result + input * 10);
			return result;
		}
	}

	// breadth first path function
	private void bfs() {
		// init
		Queue<State> queue = new LinkedList<State>();
		State startState1 = new State(2011, plusRight, null);
		State startState2 = new State(2011, divideRight, null);
		queue.add(startState1);
		queue.add(startState2);
		HashSet<State> states = new HashSet<State>();
		states.add(startState1);
		states.add(startState2);

		// bfs
		while (!queue.isEmpty()) {
			State v = queue.poll();
			double result = v.node.operation.apply(v.input);
			if (result == 2012
					&& (v.node.equals(timesRight) || v.node.equals(minusRight))) {
				// show the path
				Stack<State> stack = new Stack<State>();
				while (v != null) {
					stack.push(v);
					v = v.prev;
				}
				for (State state : stack) {
					System.out.println(state.input + " " + state.node.operation
							+ "(" + state.node.direction + ")" + " =");
				}
				return;
			}

			for (Node w : v.node.nextStates) {
				State s = new State(result, w, v);
				if (!states.contains(s)) {
					states.add(s);
					queue.add(s);
				}
			}
		}
	}

	public static void main(String[] args) {
		MatrixPuzzle bfs = new MatrixPuzzle();
		bfs.initGraph();
		bfs.bfs();
	}
}
