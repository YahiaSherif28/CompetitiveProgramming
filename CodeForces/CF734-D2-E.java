import java.util.*;
import java.io.*;
import java.text.*;

public class E734 {

	static int n;
	static int INF = (int) 1e9;
	static ArrayList<Integer> adjList[];
	static int[] clr;

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		PrintWriter pw = new PrintWriter(System.out);
		n = sc.nextInt();
		clr = new int[n];
		for (int i = 0; i < clr.length; i++) {
			clr[i] = sc.nextInt();
		}
		adjList = new ArrayList[n];
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Integer>();
		}
		UnionFind uf = new UnionFind(n);
		for (int i = 0; i < n - 1; i++) {
			int u = sc.nextInt() - 1;
			int v = sc.nextInt() - 1;
			adjList[u].add(v);
			adjList[v].add(u);
			if (clr[u] == clr[v])
				uf.unionSet(u, v);
		}
		HashMap<Integer, Integer> hm = new HashMap<Integer, Integer>();
		for (int i = 0; i < n; i++) {
			if (!hm.containsKey(uf.findSet(i)))
				hm.put(uf.findSet(i), hm.size());
		}
		ArrayList<Integer>[] graph = new ArrayList[hm.size()];
		for (int i = 0; i < graph.length; i++) {
			graph[i] = new ArrayList<Integer>();
		}
		for (int i = 0; i < n; i++) {
			for (int x : adjList[i]) {
				if (uf.findSet(i) != uf.findSet(x)) {
					graph[hm.get(uf.findSet(i))].add(hm.get(uf.findSet(x)));
				}
			}
		}
		int[] cnt = new int[hm.size()];
		Queue<Integer> q = new LinkedList<Integer>();
		for (int i = 0; i < cnt.length; i++) {
			cnt[i] = graph[i].size();
			if (cnt[i] == 1)
				q.add(i);
		}
		int rem = hm.size();
		int c = 0;
		while (rem > 1) {
			Queue<Integer> temp = new LinkedList<Integer>();
			while (!q.isEmpty()) {
				int cur = q.poll();
				rem--;
				for (int x : graph[cur]) {
					cnt[x]--;
					if (cnt[x] == 1)
						temp.add(x);
				}
			}
			c++;
			q = temp;
		}
		pw.println(c);
		

//		pw.println(Arrays.deepToString(graph));
		pw.close();
	}

	static class UnionFind {
		int[] p, rank, setSize;
		int numSets;

		public UnionFind(int N) {
			p = new int[numSets = N];
			rank = new int[N];
			setSize = new int[N];
			for (int i = 0; i < N; i++) {
				p[i] = i;
				setSize[i] = 1;
			}
		}

		public int findSet(int i) {
			return p[i] == i ? i : (p[i] = findSet(p[i]));
		}

		public boolean isSameSet(int i, int j) {
			return findSet(i) == findSet(j);
		}

		public void unionSet(int i, int j) {
			if (isSameSet(i, j))
				return;
			numSets--;
			int x = findSet(i), y = findSet(j);
			if (rank[x] > rank[y]) {
				p[y] = x;
				setSize[x] += setSize[y];
			} else {
				p[x] = y;
				setSize[y] += setSize[x];
				if (rank[x] == rank[y])
					rank[y]++;
			}
		}

		public int numDisjointSets() {
			return numSets;
		}

		public int sizeOfSet(int i) {
			return setSize[findSet(i)];
		}
	}

	static class Scanner {
		StringTokenizer st;
		BufferedReader br;

		public Scanner(InputStream s) {
			br = new BufferedReader(new InputStreamReader(s));
		}

		public Scanner(FileReader r) {
			br = new BufferedReader(r);
		}

		public String next() throws IOException {
			while (st == null || !st.hasMoreTokens())
				st = new StringTokenizer(br.readLine());
			return st.nextToken();
		}

		public int nextInt() throws IOException {
			return Integer.parseInt(next());
		}

		public long nextLong() throws IOException {
			return Long.parseLong(next());
		}

		public String nextLine() throws IOException {
			return br.readLine();
		}

		public double nextDouble() throws IOException {
			String x = next();
			StringBuilder sb = new StringBuilder("0");
			double res = 0, f = 1;
			boolean dec = false, neg = false;
			int start = 0;
			if (x.charAt(0) == '-') {
				neg = true;
				start++;
			}
			for (int i = start; i < x.length(); i++)
				if (x.charAt(i) == '.') {
					res = Long.parseLong(sb.toString());
					sb = new StringBuilder("0");
					dec = true;
				} else {
					sb.append(x.charAt(i));
					if (dec)
						f *= 10;
				}
			res += Long.parseLong(sb.toString()) / f;
			return res * (neg ? -1 : 1);
		}

		public boolean ready() throws IOException {
			return br.ready();
		}

	}
}
