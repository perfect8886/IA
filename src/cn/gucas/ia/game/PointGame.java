package cn.gucas.ia.game;

import java.util.HashMap;
import java.util.HashSet;

public class PointGame {
	private final static int CARDS_NUM = 4;
	private final static int RESULT = 24;
	private int[] cards;
	private HashMap<Integer, HashSet<Integer>> setMap = new HashMap<Integer, HashSet<Integer>>();

	public PointGame() {
		this(new int[] { 1, 2, 3, 4 });
	}

	public PointGame(int[] cards) {
		if (cards == null || cards.length != CARDS_NUM) {
			throw new IllegalArgumentException();
		}
		this.cards = cards;
	}

	public int[] getCards() {
		return cards;
	}

	public void setCards(int[] cards) {
		this.cards = cards;
	}

	public void process() {
		int N = (int) (Math.pow(2, CARDS_NUM) - 1);
		for (int i = 0; i <= N; ++i) {
			HashSet<Integer> set = new HashSet<Integer>();
			setMap.put(i, set);
		}

		for (int i = 0; i < CARDS_NUM; ++i) {
			setMap.get((int) Math.pow(2, i)).add(cards[i]);
		}

		for (int i = 1; i <= N; ++i) {
			setMap.put(i, f(i));
		}

		if (setMap.get(N).contains(RESULT)) {
			System.out.println("success!");
		} else {
			System.out.println("fail!");
		}
	}

	private HashSet<Integer> f(int i) {
		HashSet<Integer> s = setMap.get(i);
		if (s.size() != 0) {
			return s;
		}
		for (int x = 1; x < i; ++x) {
			if ((x & i) == x) {
				s.addAll(fork(f(x), f(i - x)));
			}
		}
		return s;
	}

	private HashSet<Integer> fork(HashSet<Integer> A, HashSet<Integer> B) {
		HashSet<Integer> s = new HashSet<Integer>();
		for (Integer a : A) {
			for (Integer b : B) {
				s.add(a + b);
				s.add(a - b);
				s.add(b - a);
				s.add(a * b);
				if (b != 0) {
					s.add(a / b);
				}
				if (a != 0) {
					s.add(b / a);
				}
			}
		}
		return s;
	}

	public static void main(String[] args) {
		PointGame pg = new PointGame();
		long startMilli = System.currentTimeMillis();
		pg.process();
		long cost = System.currentTimeMillis() - startMilli;
		System.out.println("cost: " + cost);
		pg.setCards(new int[] { 1, 1, 1, 1 });
		pg.process();
	}
}
