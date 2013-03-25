package cn.gucas.ia.game;

public class PointsGame {
	private final double THRESHOLD = 1e-6;
	private final static int CARD_NUM = 4;
	private final static int RESULT = 24;
	private double[] cards = new double[CARD_NUM];
	private String[] results = new String[CARD_NUM];

	public PointsGame(int[] cards) {
		if (cards.length != 4) {
			throw new IllegalArgumentException();
		}
		for (int i = 0; i < CARD_NUM; ++i) {
			this.cards[i] = cards[i];
			this.results[i] = String.valueOf(cards[i]);
		}
	}

	private boolean process(int n) {
		if (n == 1) {
			if (Math.abs(cards[0] - RESULT) < THRESHOLD) {
				System.out.println(results[0]);
				return true;
			} else {
				return false;
			}
		}

		for (int i = 0; i < n; ++i) {
			for (int j = i + 1; j < n; ++j) {
				double a, b;
				String expa, expb;

				a = cards[i];
				b = cards[j];
				cards[j] = cards[n - 1];

				expa = results[i];
				expb = results[j];
				results[j] = results[n - 1];

				results[i] = "(" + expa + "+" + expb + ")";
				cards[i] = a + b;
				if (process(n - 1)) {
					return true;
				}

				results[i] = "(" + expa + "-" + expb + ")";
				cards[i] = a - b;
				if (process(n - 1)) {
					return true;
				}

				results[i] = "(" + expb + "-" + expa + ")";
				cards[i] = b - a;
				if (process(n - 1)) {
					return true;
				}

				results[i] = "(" + expa + "*" + expb + ")";
				cards[i] = a * b;
				if (process(n - 1)) {
					return true;
				}

				if (b != 0) {
					results[i] = "(" + expa + "/" + expb + ")";
					cards[i] = a / b;
					if (process(n - 1)) {
						return true;
					}
				}

				if (a != 0) {
					results[i] = "(" + expb + "/" + expa + ")";
					cards[i] = b / a;
					if (process(n - 1)) {
						return true;
					}
				}

				cards[i] = a;
				cards[j] = b;
				results[i] = expa;
				results[j] = expb;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		PointsGame pg = new PointsGame(new int[] { 4, 13, 12, 10 });
		long startMilli = System.currentTimeMillis();
		if (pg.process(CARD_NUM)) {
			System.out.println("Success!");
		} else {
			System.out.println("Fail!");
		}
		long cost = System.currentTimeMillis() - startMilli;
		System.out.println("cost: " + cost);
	}
}
