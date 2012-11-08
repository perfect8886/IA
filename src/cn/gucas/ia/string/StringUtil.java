package cn.gucas.ia.string;

/**
 * @ClassName: StringUtil
 * @Description: some static methods used to deal with string objects
 * @author pengf
 * @date 2012-10-31
 */
public class StringUtil {
	/**
	 * @Title: similarity
	 * @Description: calculate the similarity of string A and string B
	 * @param String
	 *            strA
	 * @param String
	 *            strB
	 * @return a double value represent the similarity
	 * @throws
	 */
	public static double similarity(String strA, String strB) {
		return (double) 1 / (distance(strA, strB) + 1);
	}

	/**
	 * @Title: distance
	 * @Description: calculate the distance between string A and string B
	 * @param String
	 *            strA
	 * @param String
	 *            strB
	 * @return a int value represent the distance
	 * @throws NullPointerException
	 */
	public static int distance(String strA, String strB) {
		// check null input
		if (null == strA || null == strB) {
			throw new NullPointerException();
		}

		int lenA = strA.length();
		int lenB = strB.length();

		// check string length
		if (lenA == 0) {
			return lenB;
		}
		if (lenB == 0) {
			return lenA;
		}

		// initialize the distance array
		int distance[][] = new int[lenA][lenB];

		for (int i = 1; i < lenA; ++i) {
			distance[i][0] = i;
		}
		for (int j = 1; j < lenB; ++j) {
			distance[0][j] = j;
		}
		distance[0][0] = strA.charAt(0) == strB.charAt(0) ? 0 : 1;

		// dynamic programming
		for (int i = 1; i < lenA; ++i) {
			for (int j = 1; j < lenB; ++j) {
				if (strA.charAt(i) == strB.charAt(j)) {
					distance[i][j] = distance[i - 1][j - 1];
				} else {
					distance[i][j] = minValue(distance[i][j - 1],
							distance[i - 1][j], distance[i - 1][j - 1]) + 1;
				}
			}
		}

		return distance[lenA - 1][lenB - 1];
	}

	/**
	 * @Title: minValue
	 * @Description: the minus value of three int params: a, b, c
	 * @param int a
	 * @param int b
	 * @param int c
	 * @return the minus int value
	 * @throws
	 */
	private final static int minValue(int a, int b, int c) {
		int min = a < b ? a : b;
		min = min < c ? min : c;
		return min;
	}

	public static boolean checkCircularRotation(String a, String b) {
		return a.concat(a).contains(b);
	}

	public static void main(String[] args) {
		System.out.println(checkCircularRotation("", ""));
		System.out.println(checkCircularRotation("ab", "ba"));
		System.out.println(checkCircularRotation("cba", "abc"));
		System.out.println(checkCircularRotation("ccba", "accb"));

		String a = "";
		String b = "aabbcc";
		String c = "ccbbaa";
		String d = "ccbbaa";

		// distance
		System.out.println(StringUtil.distance(a, b));
		System.out.println(StringUtil.distance(b, c));
		System.out.println(StringUtil.distance(c, d));
		System.out.println(StringUtil.distance(null, a));

		// similariy
		System.out.println(StringUtil.similarity(a, b));
		System.out.println(StringUtil.similarity(b, c));
		System.out.println(StringUtil.similarity(c, d));
		System.out.println(StringUtil.similarity(null, a));
	}
}
