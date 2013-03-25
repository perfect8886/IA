package cn.gucas.ia.graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SymbolDigraph {
	private Map<String, Integer> st; // symbol table, symbol->index
	private String[] keys; // index->symbol
	private Digraph G;

	public SymbolDigraph(String filePath, String sp) {
		st = new HashMap<String, Integer>();
		
		File file = new File(filePath);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String line = "";
			while ((line = reader.readLine()) != null) {
				String[] symbolArray = line.split(sp);
				for (int i = 0; i < symbolArray.length; ++i) {
					if (!st.containsKey(symbolArray[i])) {
						st.put(symbolArray[i], st.size());
					}
				}
			}
			reader.close();

			keys = new String[st.size()];
			for (String name : st.keySet()) {
				keys[st.get(name)] = name;
			}

			G = new Digraph(st.size());
			reader = new BufferedReader(new FileReader(file));
			while ((line = reader.readLine()) != null) {
				String[] symbolArray = line.split(sp);
				int v = st.get(symbolArray[0]);
				for (int i = 1; i < symbolArray.length; ++i) {
					G.addEdge(v, st.get(symbolArray[i]));
				}
			}
			reader.close();
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

	public boolean contains(String s) {
		return st.containsKey(s);
	}

	public int index(String s) {
		return st.get(s);
	}

	public String name(int v) {
		return keys[v];
	}

	public Digraph G() {
		return G;
	}
}
