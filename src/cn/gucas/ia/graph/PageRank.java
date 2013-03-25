package cn.gucas.ia.graph;

public class PageRank {
	private static final double ALPHA = 0.85; // damping factor
	private static final double THRESHOLD = 0.000000001;

	private SymbolDigraph SG;
	private Digraph G;
	private Digraph RG;
	private int V;
	private double pr[];
	private double prePr[];

	// change the way that creates the SymbolDigraph as you wish.
	public PageRank(String filePath, String sp) {
		this.SG = new SymbolDigraph(filePath, sp);
	}

	private void init() {
		G = SG.G();
		RG = G.reverse();
		V = G.V();

		pr = new double[V];
		prePr = new double[V];
		for (int v = 0; v < V; ++v) {
			pr[v] = (double) 1 / V;
		}
	}

	public void excute() {
		int i = 0;

		// init
		init();

		// iterate
		boolean convergence = false;
		while (!convergence) {
			// intern result
			System.out.println("---------------" + i++
					+ "th iteration----------------");
			show();

			for (int v = 0; v < V; ++v) {
				prePr[v] = pr[v];
			}

			for (int v = 0; v < V; ++v) {
				pr[v] = (1 - ALPHA) / V;
				double epsional = 0.0;
				for (int w : RG.adj(v)) {
					epsional += prePr[w] / G.adj(w).size();
				}
				pr[v] += ALPHA * epsional;
			}

			for (int v = 0; v < V; ++v) {
				if (Math.abs(prePr[v] - pr[v]) >= THRESHOLD) {
					break;
				}
				if (v >= V - 1) {
					convergence = true;
				}
			}
		}

		// show result
		System.out.println("---------------result----------------");
		show();
	}

	private void show() {
		for (int v = 0; v < V; ++v) {
			System.out.println(SG.name(v) + ": " + pr[v]);
		}
	}

	public static void main(String[] args) {
		String filePath = "PR.txt";
		String separator = "	";
		PageRank pr = new PageRank(filePath, separator);
		pr.excute();
	}
}
