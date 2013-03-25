package cn.gucas.ia.game;

import java.util.Scanner;

/**
 * @Description: the answer to http://cloudbbs.org/forum.php?mod=viewthread&tid=13193&extra=&page=1 
 * @author perfect8886@hotmail.com
 * @date 2013-3-25
 */

public class DeathGame {
	private static final int MAX_N = 31;
	private boolean[] isAlive = new boolean[MAX_N];

	private int N = 31;
	private int M = 9;
	private boolean showState = false;

	public void setShowState(boolean showState) {
		this.showState = showState;
	}

	public void setN(int N) {
		assert N > 16 && N < 32;
		this.N = N;
	}

	public void setM(int M) {
		assert M > 2 && M < 10;
		this.M = M;
	}

	private void init() {
		for (int i = 0; i < N; ++i) {
			isAlive[i] = true;
		}
	}

	private int getNextAlive(int currentPosition) {
		for (int i = 1; i < N - 1; ++i) {
			int index = (i + currentPosition) % N;
			if (isAlive[index]) {
				return index;
			}
		}
		return -1;
	}

	private void show(int remain, int currentPosition) {
		System.out.print("NO." + currentPosition + " died, " + remain
				+ " remain.");
		if (showState) {
			System.out.println(" Current state:");
			for (int i = 0; i < N; ++i) {
				System.out.print(i + "	");
			}
			System.out.println();
			for (int i = 0; i < N; ++i) {
				System.out.print((isAlive[i] ? "Y" : "N") + "	");
			}
			System.out.println();
		}
		System.out.println();

	}

	public void process() {
		this.init();

		int remain = N;
		int currentPosition = 0;
		int currentNum = 1;
		while (remain > 1) {
			if (currentNum == M) {
				isAlive[currentPosition] = false;
				--remain;
				show(remain, currentPosition);

				currentNum = 1;
				currentPosition = getNextAlive(currentPosition);
				continue;
			}
			++currentNum;
			currentPosition = getNextAlive(currentPosition);
		}
		for (int i = 0; i < N; ++i) {
			if (isAlive[i]) {
				System.out.println("The final survivor is NO." + i);
				break;
			}
		}
	}

	public static void main(String[] args) {
		DeathGame deathGame = new DeathGame();
		Scanner in = new Scanner(System.in);

		while (true) {
			System.out.println("是否显示每轮状态？（Y/N）:");
			String showSate = in.nextLine();
			while (true) {
				if (showSate.equalsIgnoreCase("Y")) {
					deathGame.setShowState(true);
					break;
				} else if (showSate.equalsIgnoreCase("N")) {
					deathGame.setShowState(false);
					break;
				} else {
					System.out.println("是否显示每轮状态？（Y/N）");
					showSate = in.nextLine();
				}
			}

			System.out.println("请输入游戏人数N（16<N<32）:");
			String nStr = in.nextLine();
			int N = 0;
			while (true) {
				try {
					N = Integer.parseInt(nStr);
				} catch (Exception e) {
					System.out.println("请输入游戏人数N（16<N<32）:");
					nStr = in.nextLine();
					continue;
				}
				if (N > 16 && N < 32) {
					deathGame.setN(N);
					break;
				} else {
					System.out.println("请输入游戏人数N（16<N<32）:");
					nStr = in.nextLine();
				}
			}

			System.out.println("请输入死亡数字M（2<M<10）:");
			String mStr = in.nextLine();
			int M = 0;
			while (true) {
				try {
					M = Integer.parseInt(mStr);
				} catch (Exception e) {
					System.out.println("请输入死亡数字M（2<M<10）:");
					mStr = in.nextLine();
					continue;
				}
				if (M > 2 && M < 10) {
					deathGame.setM(M);
					break;
				} else {
					System.out.println("请输入死亡数字M（2<M<10）:");
					mStr = in.nextLine();
				}
			}

			deathGame.process();

			System.out.println("再玩一次？（Y/N）:");
			String again = in.nextLine();
			while (true) {
				if (again.equalsIgnoreCase("Y")) {
					break;
				} else if (again.equalsIgnoreCase("N")) {
					return;
				} else {
					System.out.println("再玩一次？（Y/N）:");
					again = in.nextLine();
				}
			}
		}
	}
}
