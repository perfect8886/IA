package cn.gucas.ia.graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PageRank2 {
	private static final double ALPHA = 0.85; // damping factor
	private static final double THRESHOLD = 0.000000001;

	private Map<String, Node> map = new HashMap<String, Node>();

	public PageRank2(String filePath, String sp) {
		init(filePath, sp);
	}

	class Node {
		String name;
		List<Node> outNodes = new ArrayList<Node>();
		List<Node> inNodes = new ArrayList<Node>();
		double prePr;
		double pr;

		Node(String name) {
			this.name = name;
		}

		void addOutNode(Node node) {
			outNodes.add(node);
		}

		void addInNode(Node node) {
			inNodes.add(node);
		}
	}

	private void init(String filePath, String sp) {
		File file = new File(filePath);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String line = "";
			while ((line = reader.readLine()) != null) {
				String[] array = line.split(sp);
				if (!map.containsKey(array[0])) {
					map.put(array[0], new Node(array[0]));
				}

				for (int i = 1; i < array.length; ++i) {
					if (!map.containsKey(array[i])) {
						map.put(array[i], new Node(array[i]));
					}

					map.get(array[0]).addOutNode(map.get(array[i]));
					map.get(array[i]).addInNode(map.get(array[0]));
				}
			}
			reader.close();

			Set<String> keys = map.keySet();
			for (String key : keys) {
				map.get(key).pr = (double) 1 / map.size();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public void excute() {
		int i = 0;

		// iterate
		boolean convergence = false;
		while (!convergence) {
			// intern result
			System.out.println("---------------" + i++
					+ "th iteration----------------");
			show();

			Set<String> keys = map.keySet();
			for (String key : keys) {
				map.get(key).prePr = map.get(key).pr;
			}

			for (String key : keys) {
				map.get(key).pr = (1 - ALPHA) / map.size();
				double epsional = 0.0;
				for (Node node : map.get(key).inNodes) {
					epsional += node.prePr / node.outNodes.size();
				}
				map.get(key).pr += ALPHA * epsional;
			}

			int count = 0;
			for (String key : keys) {
				if (Math.abs(map.get(key).pr - map.get(key).prePr) >= THRESHOLD) {
					break;
				}
				++count;
				if (count >= map.size()) {
					convergence = true;
				}
			}
		}

		// show result
		System.out.println("---------------result----------------");
		show();
	}

	private void show() {
		Set<String> keys = map.keySet();
		for (String key : keys) {
			System.out.println(key + ": " + map.get(key).pr);
		}
	}

	public static void main(String[] args) {
		String filePath = "PR.txt";
		String separator = "	";
		PageRank2 pr = new PageRank2(filePath, separator);
		pr.excute();

	}
}
