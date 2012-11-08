package cn.gucas.ia.math;

import java.math.BigInteger;

public class StdMath {
	public static double sqrt(double c) {
		if (c < 0) {
			throw new IllegalArgumentException();
		}

		double t = c;
		double err = 1e-15;
		while (Math.abs(t - c / t) > 2 * err * t) {
			t = (t + c / t) / 2;
		}
		return t;
	}

	public static BigInteger gcdByMod(BigInteger p, BigInteger q) {
		if (q.equals(BigInteger.ZERO)) {
			return p;
		}
		BigInteger r = p.mod(q);
		return gcdByMod(q, r);
	}

	public static BigInteger gcdBySubtraction(BigInteger p, BigInteger q) {
		if (q.equals(BigInteger.ZERO)) {
			return p;
		} else if (p.compareTo(q) < 0) {
			return gcdBySubtraction(q, p);
		} else {
			return gcdBySubtraction(p.add(q.negate()), q);
		}
	}

	public static BigInteger gcdByShift(BigInteger p, BigInteger q) {
		if (q.equals(BigInteger.ZERO)) {
			return p;
		} else if (p.compareTo(q) < 0) {
			return gcdByShift(q, p);
		} else {
			if (isEven(p)) {
				if (isEven(q)) {
					return gcdByShift(p.shiftRight(1), q.shiftRight(1))
							.shiftRight(1);
				} else {
					return gcdByShift(p.shiftRight(1), q);
				}
			} else {
				if (isEven(q)) {
					return gcdByShift(p, q.shiftRight(1));
				} else {
					return gcdByShift(q, p.add(q.negate()));
				}
			}
		}
	}

	private static boolean isEven(BigInteger n) {
		return n.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO);
	}

	public static void main(String[] args) {
		System.out.println(StdMath.sqrt(1));
		System.out.println(StdMath.sqrt(2));
		System.out.println(StdMath.sqrt(3));
		System.out.println(StdMath.sqrt(4));
		System.out.println(StdMath.sqrt(100));
		System.out.println(StdMath.sqrt(Double.MAX_VALUE));

		System.out.println(StdMath.gcdBySubtraction(new BigInteger("1100100210001"),
				new BigInteger("120200021")));
	}
}
