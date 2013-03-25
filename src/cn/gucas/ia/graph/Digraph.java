package cn.gucas.ia.graph;

import java.util.ArrayList;
import java.util.List;

public class Digraph {
	private final int V;
	private int E;
	private List<Integer>[] adj;

	// the cast for 'adj' is correct because the elements to be created are all
	// of the same type as cast declaration
	@SuppressWarnings("unchecked")
	public Digraph(int V) {
		this.V = V;
		this.E = 0;
		adj = (List<Integer>[]) new List[V];
		for (int v = 0; v < V; ++v) {
			adj[v] = new ArrayList<Integer>();
		}
	}

	public int V() {
		return V;
	}

	public int E() {
		return E;
	}

	public void addEdge(int v, int w) {
		adj[v].add(w);
		++E;
	}

	public List<Integer> adj(int v) {
		return adj[v];
	}

	public Digraph reverse() {
		Digraph R = new Digraph(V);
		for (int v = 0; v < V; ++v) {
			for (int w : adj(v)) {
				R.addEdge(w, v);
			}
		}
		return R;
	}
}
