import java.util.*;
import java.io.*;
import java.text.*;

public class E980 {

	static int[] p, s, e, dist;
	static ArrayList<Integer>[] adjList;
	static int t = 0;

	public static void getIntervals(int u) {
		Stack<Integer> st = new Stack<Integer>();
		st.add(u << 1 | 1);
		st.add(u << 1);
		while (!st.isEmpty()) {
			int cur = st.pop();
			int ss = cur & 1;
			int idx = cur >> 1;
			if (ss == 0) {
				s[idx] = t++;
				if (p[idx] != -1)
					dist[s[idx]] = dist[s[p[idx]]] + 1;
				for (int x : adjList[idx]) {
					if (s[x] == -1) {
						p[x] = idx;
						st.add(x << 1 | 1);
						st.add(x << 1);
					}
				}
			} else {
				e[idx] = t - 1;
			}
		}
	}

	static class Pair {
		int idx, s;

		public Pair(int idx, int s) {
			this.idx = idx;
			this.s = s;
		}

	}

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		PrintWriter pw = new PrintWriter(System.out);
		int n = sc.nextInt();
		int k = sc.nextInt();

		adjList = new ArrayList[n];
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Integer>();
		}

		for (int i = 0; i < n - 1; i++) {
			int u = sc.nextInt() - 1;
			int v = sc.nextInt() - 1;

			adjList[u].add(v);
			adjList[v].add(u);
		}
		p = new int[n];
		Arrays.fill(p, -1);


		s = new int[n];
		e = new int[n];
		dist = new int[n];
		Arrays.fill(s, -1);

		getIntervals(n - 1);

		int N = 1;
		while (N < n)
			N <<= 1; // padding

		SegmentTree sg = new SegmentTree(N);

		int rem = n - k - 1;

		boolean taken[] = new boolean[n];
		taken[n - 1] = true;
		for (int i = n - 2; rem > 0 && i >= 0; i--) {
			if (taken[i])
				continue;
			int q = dist[s[i]] + sg.query(s[i] + 1, s[i] + 1);
			if (q <= rem) {
				rem -= q;
				int cur = i;
				Stack<Integer> path = new Stack<Integer>();
				while (!taken[cur]) {
					taken[cur] = true;
					path.add(cur);
					cur = p[cur];
				}
				while (!path.isEmpty()) {
					cur = path.pop();
					sg.update_range(s[cur] + 1, e[cur] + 1, -1);
				}
			}

		}

		for (int i = 0; i < taken.length; i++) {
			if (!taken[i]) {
				pw.print(i + 1 + " ");
			}
		}
		pw.close();
	}

	static class SegmentTree { // 1-based DS, OOP

		int N; // the number of elements in the array as a power of 2 (i.e. after padding)
		int[] sTree, lazy;

		SegmentTree(int N) {
			this.N = N;
			sTree = new int[N << 1]; // no. of nodes = 2*N - 1, we add one to cross out index zero
			lazy = new int[N << 1];
		}

		void update_range(int i, int j, int val) // O(log n)
		{
			update_range(1, 1, N, i, j, val);
		}

		void update_range(int node, int b, int e, int i, int j, int val) {
			if (i > e || j < b)
				return;
			if (b >= i && e <= j) {
				lazy[node] += val;
			} else {
				int mid = b + e >> 1;
				
				update_range(node << 1, b, mid, i, j, val);
				update_range(node << 1 | 1, mid + 1, e, i, j, val);

			}

		}

		
		int query(int i, int j) {
			return query(1, 1, N, i, j);
		}

		int query(int node, int b, int e, int i, int j) // O(log n)
		{
//			System.out.println(b+" "+e+" "+node);
			if (i > e || j < b)
				return 0;
			if (b >= i && e <= j)
				return lazy[node];
			int mid = b + e >> 1;

			int q1 = query(node << 1, b, mid, i, j);
			int q2 = query(node << 1 | 1, mid + 1, e, i, j);
			return q1 + q2 + lazy[node];
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

	}
}
