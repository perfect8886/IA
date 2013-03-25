package cn.gucas.ia.graph;

import java.util.PriorityQueue;

import cn.gucas.ia.structure.Bag;

public class PrimMST {
	private Edge[] edgeTo;
	private double[] distTo;
	private boolean[] marked;
	private PriorityQueue<Vertex> pq;
	private double weight;

	private class Vertex implements Comparable<Vertex> {
		private int v;
		private double weight;

		private Vertex(int v, double weight) {
			this.v = v;
			this.weight = weight;
		}

		@Override
		public int compareTo(Vertex o) {
			if (this.weight < o.weight) {
				return -1;
			} else if (this.weight > o.weight) {
				return 1;
			} else {
				return 0;
			}
		}

		@Override
		public boolean equals(Object o) {
			if (!(o instanceof Vertex)) {
				return false;
			}
			Vertex that = (Vertex) o;
			return this.v == that.v && this.weight == that.weight;
		}

		@Override
		public int hashCode() {
			int result = 17;
			result = 31 * result + v;
			long lw = Double.doubleToLongBits(weight);
			result = 31 * result + (int) (lw ^ (lw >>> 32));
			return result;
		}
	}

	public PrimMST(EdgeWeightedGraph G) {
		weight = 0.0;
		edgeTo = new Edge[G.V()];
		distTo = new double[G.V()];
		marked = new boolean[G.V()];
		for (int v = 0; v < G.V(); ++v) {
			distTo[v] = Double.POSITIVE_INFINITY;
		}
		pq = new PriorityQueue<Vertex>();

		distTo[0] = 0.0;
		pq.add(new Vertex(0, 0.0));
		while (!pq.isEmpty()) {
			visit(G, pq.remove());
		}
	}

	private void visit(EdgeWeightedGraph G, Vertex vertex) {
		marked[vertex.v] = true;
		for (Edge e : G.adj(vertex.v)) {
			int w = e.other(vertex.v);
			if (marked[w]) {
				continue;
			}
			if (e.weight() < distTo[w]) {
				Vertex toBe = new Vertex(w, distTo[w]);
				if (distTo[w] < Double.POSITIVE_INFINITY) {
					weight -= distTo[w];
				}
				if (pq.contains(toBe)) {
					pq.remove(toBe);
				}
				edgeTo[w] = e;
				distTo[w] = e.weight();
				pq.add(new Vertex(w, distTo[w]));
				weight += distTo[w];
			}
		}
	}

	public Iterable<Edge> edges() {
		Bag<Edge> b = new Bag<Edge>();
		for (int v = 1; v < edgeTo.length; ++v) {
			b.add(edgeTo[v]);
		}
		return b;
	}

	public double weight() {
		return weight;
	}

	public static void main(String[] args) {
		EdgeWeightedGraph G = new EdgeWeightedGraph(8);
		G.addEdge(new Edge(4, 5, 0.35));
		G.addEdge(new Edge(4, 7, 0.37));
		G.addEdge(new Edge(5, 7, 0.28));
		G.addEdge(new Edge(0, 7, 0.16));
		G.addEdge(new Edge(1, 5, 0.32));
		G.addEdge(new Edge(0, 4, 0.38));
		G.addEdge(new Edge(2, 3, 0.17));
		G.addEdge(new Edge(1, 7, 0.19));
		G.addEdge(new Edge(0, 2, 0.26));
		G.addEdge(new Edge(1, 2, 0.36));
		G.addEdge(new Edge(1, 3, 0.29));
		G.addEdge(new Edge(2, 7, 0.34));
		G.addEdge(new Edge(6, 2, 0.40));
		G.addEdge(new Edge(3, 6, 0.52));
		G.addEdge(new Edge(6, 0, 0.58));
		G.addEdge(new Edge(6, 4, 0.93));
		G.addEdge(new Edge(4, 5, 0.35));
		G.addEdge(new Edge(4, 5, 0.35));
		G.addEdge(new Edge(4, 5, 0.35));
		G.addEdge(new Edge(4, 5, 0.35));
		G.addEdge(new Edge(4, 5, 0.35));
		G.addEdge(new Edge(4, 5, 0.35));

		PrimMST mst = new PrimMST(G);
		for (Edge e : mst.edges()) {
			System.out.println(e);
		}
		System.out.println(mst.weight());
	}
}
