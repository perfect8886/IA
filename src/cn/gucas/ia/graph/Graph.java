package cn.gucas.ia.graph;

import cn.gucas.ia.structure.Bag;

public class Graph {
	private final int V;
	private int E;
	private Bag<Integer>[] adj;

	public Graph(int V) {
		this.V = V;
		E = 0;
		adj = (Bag<Integer>[]) new Bag[V];
		for (int v = 0; v < V; ++v) {
			adj[v] = new Bag<Integer>();
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
		adj[w].add(v);
		++E;
	}

	public Iterable<Integer> adj(int v) {
		return adj[V];
	}
}
