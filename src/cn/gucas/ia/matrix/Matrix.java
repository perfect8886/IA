package cn.gucas.ia.matrix;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * matrix-puzzle Mr Chen's matrix puzzle posted on http://weibo.com/lirenchen A
 * simple BFS-based solution in Java. To avoid OutOfMemoryError, run it with JVM
 * argument -Xmx512m.
 * 
 * @author erwinyang http://weibo.com/erwinyang
 */
public class Matrix {

	private class State {
		public final int value;
		public final NODE node;
		public final State prev;

		public State(int value, NODE node, State prev) {
			this.value = value;
			this.node = node;
			this.prev = prev;
		}

		public int hash() {
			return value * 10 + node.value;
		}
	}

	private enum NODE {
		START(0), END(9), INC7_LEFT(1), INC7_RIGHT(2), DIV2_LEFT(3), DIV2_RIGHT(
				4), DEC5_LEFT(5), DEC5_RIGHT(6), MUL3_LEFT(7), MUL3_RIGHT(8);

		public final int value;

		NODE(int value) {
			this.value = value;
		}
	}

	private Queue<State> q = new LinkedList<State>();
	private Set<Integer> visited = new HashSet<Integer>();

	public void run() {
		q.add(new State(2011, NODE.START, null));
		while (!q.isEmpty()) {
			State state = q.poll();
			visited.add(state.hash());

			if (state.node == NODE.END && state.value == 2012) {
				List<State> steps = new ArrayList<State>();
				State p = state;
				while (p != null) {
					steps.add(p);
					p = p.prev;
				}

				System.out.println("-- STEPS --");
				for (int i = steps.size() - 1; i >= 0; i--) {
					State step = steps.get(i);
					System.out.println(String.format("step %s: %s, value=%s",
							steps.size() - i - 1, step.node.toString(),
							step.value));
				}
				return;
			}

			switch (state.node) {
			case START:
				addOrNot(new State(state.value, NODE.INC7_RIGHT, state));
				addOrNot(new State(state.value, NODE.DIV2_RIGHT, state));
				break;
			case INC7_LEFT:
				addOrNot(new State(state.value + 7, NODE.DIV2_RIGHT, state));
				break;
			case INC7_RIGHT:
				addOrNot(new State(state.value + 7, NODE.DIV2_LEFT, state));
				addOrNot(new State(state.value + 7, NODE.MUL3_RIGHT, state));
				addOrNot(new State(state.value + 7, NODE.DEC5_RIGHT, state));
				break;
			case DIV2_LEFT:
				addOrNot(new State(state.value / 2, NODE.INC7_RIGHT, state));
				break;
			case DIV2_RIGHT:
				addOrNot(new State(state.value / 2, NODE.INC7_LEFT, state));
				addOrNot(new State(state.value / 2, NODE.MUL3_RIGHT, state));
				addOrNot(new State(state.value / 2, NODE.DEC5_RIGHT, state));
				break;
			case DEC5_LEFT:
				addOrNot(new State(state.value - 5, NODE.MUL3_RIGHT, state));
				addOrNot(new State(state.value - 5, NODE.INC7_LEFT, state));
				addOrNot(new State(state.value - 5, NODE.DIV2_LEFT, state));
				break;
			case DEC5_RIGHT:
				addOrNot(new State(state.value - 5, NODE.MUL3_LEFT, state));
				addOrNot(new State(state.value - 5, NODE.END, state));
				break;
			case MUL3_LEFT:
				addOrNot(new State(state.value * 3, NODE.DEC5_RIGHT, state));
				addOrNot(new State(state.value * 3, NODE.INC7_LEFT, state));
				addOrNot(new State(state.value * 3, NODE.DIV2_LEFT, state));
				break;
			case MUL3_RIGHT:
				addOrNot(new State(state.value * 3, NODE.DEC5_LEFT, state));
				addOrNot(new State(state.value * 3, NODE.END, state));
				break;
			default:
				break;
			}
		}

	}

	private void addOrNot(State state) {
		if (!visited.contains(state.hash())) {
			q.add(state);
		}
	}

	public static void main(String[] args) {
		try {
			new Matrix().run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}