package cn.gucas.ia.game;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class Game24Point {
	private final static int CARDS_NUM = 4;
	private final static int RESULT = 24;
	private final static double THRESHOLD = 1e-6;
	private int points[];

	public Game24Point(int[] points) {
		if (points == null || points.length != CARDS_NUM) {
			throw new IllegalArgumentException();
		}
		this.points = points;
	}

	public void setPoints(int[] points) {
		if (points == null || points.length != CARDS_NUM) {
			throw new IllegalArgumentException();
		}
		this.points = points;
	}

	private enum Operation {
		PLUS, MINUS, REVERS_MINUS, TIMES, DIVIDE, REVERSE_DIVIDE
	}

	private class Card implements Comparable<Card> {
		double point;
		String equation;

		Card() {
		}

		Card(double point, String equation) {
			this.point = point;
			this.equation = equation;
		}

		@Override
		public int compareTo(Card o) {
			if (point > o.point) {
				return 1;
			} else if (point == o.point) {
				return 0;
			} else {
				return -1;
			}
		}
	}

	private class State {
		Card[] cards;

		State(Card[] cards) {
			Arrays.sort(cards);
			this.cards = cards;
		}

		@Override
		public boolean equals(Object o) {
			Card[] oCards = ((State) o).cards;
			if (oCards == null) {
				if (cards == null) {
					return true;
				} else {
					return false;
				}
			}
			if (oCards.length != cards.length) {
				return false;
			}
			for (int i = 0; i < oCards.length; ++i) {
				if (oCards[i].point != cards[i].point) {
					return false;
				}
			}
			return true;
		}

		@Override
		public int hashCode() {
			int result = 17;
			for (int i = 0; i < cards.length; ++i) {
				long f = Double.doubleToLongBits(cards[i].point);
				f = f ^ (f >> 32);
				result = 31 * result + (int) f;
			}
			return result;
		}
	}

	private State genState(int[] points) {
		int N = points.length;

		Card[] cards = new Card[N];
		for (int i = 0; i < N; ++i) {
			cards[i] = new Card(points[i], String.valueOf(points[i]));
		}

		return new State(cards);
	}

	private Card[] genCards(int i, int j, Card[] srcCards, Operation operation) {
		int N = srcCards.length;
		Card[] dstCards = new Card[N - 1];
		for (int x = 0, y = 0; x < N; ++x) {
			if (x != i && x != j) {
				dstCards[y++] = srcCards[x];
			}
		}

		switch (operation) {
		case PLUS: {
			Card card = new Card();
			card.point = srcCards[i].point + srcCards[j].point;
			card.equation = "(" + srcCards[i].equation + "+"
					+ srcCards[j].equation + ")";
			dstCards[N - 2] = card;
			break;
		}

		case MINUS: {
			Card card = new Card();
			card.point = srcCards[i].point - srcCards[j].point;
			card.equation = "(" + srcCards[i].equation + "-"
					+ srcCards[j].equation + ")";
			dstCards[N - 2] = card;
			break;
		}
		case REVERS_MINUS: {
			Card card = new Card();
			card.point = srcCards[j].point - srcCards[i].point;
			card.equation = "(" + srcCards[j].equation + "-"
					+ srcCards[i].equation + ")";
			dstCards[N - 2] = card;
			break;
		}
		case TIMES: {
			Card card = new Card();
			card.point = srcCards[i].point * srcCards[j].point;
			card.equation = "(" + srcCards[i].equation + "*"
					+ srcCards[j].equation + ")";
			dstCards[N - 2] = card;
			break;
		}
		case DIVIDE: {
			if (srcCards[j].point == 0.0) {
				return null;
			}
			Card card = new Card();
			card.point = srcCards[i].point / srcCards[j].point;
			card.equation = "(" + srcCards[i].equation + "/"
					+ srcCards[j].equation + ")";
			dstCards[N - 2] = card;
			break;
		}
		case REVERSE_DIVIDE: {
			if (srcCards[i].point == 0.0) {
				return null;
			}
			Card card = new Card();
			card.point = srcCards[j].point / srcCards[i].point;
			card.equation = "(" + srcCards[j].equation + "/"
					+ srcCards[i].equation + ")";
			dstCards[N - 2] = card;
			break;
		}
		}
		return dstCards;
	}

	public void bfs() {
		// init
		Queue<State> statesQueue = new LinkedList<State>();
		Set<State> visitedStates = new HashSet<State>();
		State startState = genState(points);
		visitedStates.add(startState);
		statesQueue.add(startState);

		// search
		while (!statesQueue.isEmpty()) {
			State state = statesQueue.poll();
			Card[] cards = state.cards;
			int N = cards.length;
			if (N == 1) {
				if (Math.abs(cards[0].point - RESULT) < THRESHOLD) {
					System.out.println(cards[0].equation);
					return;
				} else {
					continue;
				}
			}

			for (int i = 0; i < N; ++i) {
				for (int j = i + 1; j < N; ++j) {
					for (Operation operation : Operation.values()) {
						Card[] dstCards = genCards(i, j, cards, operation);
						if (dstCards != null) {
							State dstState = new State(dstCards);
							if (!visitedStates.contains(dstState)) {
								visitedStates.add(dstState);
								statesQueue.add(dstState);
							}
						}
					}
				}
			}
		}
	}

	public void dfs() {
		Set<State> visitedStates = new HashSet<State>();
		State startState = genState(points);
		visitedStates.add(startState);
		dfsVisit(startState, visitedStates);
	}

	private void dfsVisit(State state, Set<State> visitedStates) {
		Card[] cards = state.cards;
		int N = cards.length;
		if (N == 1) {
			if (Math.abs(cards[0].point - RESULT) < THRESHOLD) {
				System.out.println(cards[0].equation);
				return;
			} else {
				return;
			}
		}

		for (int i = 0; i < N; ++i) {
			for (int j = i + 1; j < N; ++j) {
				for (Operation operation : Operation.values()) {
					Card[] dstCards = genCards(i, j, cards, operation);
					if (dstCards != null) {
						State dstState = new State(dstCards);
						if (!visitedStates.contains(dstState)) {
							visitedStates.add(dstState);
							dfsVisit(dstState, visitedStates);
						}
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		Game24Point game = new Game24Point(new int[] { 4, 13, 12, 10 });
		long startMilli = System.currentTimeMillis();
		game.bfs();
		long cost = System.currentTimeMillis() - startMilli;
		System.out.println("bfs cost: " + cost);

		startMilli = System.currentTimeMillis();
		game.dfs();
		cost = System.currentTimeMillis() - startMilli;
		System.out.println("dfs cost: " + cost);
	}
}
