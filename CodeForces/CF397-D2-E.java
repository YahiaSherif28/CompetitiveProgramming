
import java.util.*;
import java.io.*;
import java.text.*;

public class E397 {

	static ArrayList<Integer>[] adjList;
	static int[] s, e, l;
	static int t = 0;

	public static void findIntervals(int u, int p) {
		s[u] = t;
		for (int x : adjList[u]) {
			if (x != p) {
				t++;
				l[x] = l[u] + 1;
				findIntervals(x, u);
			}
		}
		e[u] = t;
	}

	static int mod = (int) 1e9 + 7;

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		PrintWriter pw = new PrintWriter(System.out);
		int n = sc.nextInt();
		adjList = new ArrayList[n];
		for (int i = 0; i < n; i++)
			adjList[i] = new ArrayList<Integer>();
		for (int i = 1; i <= n - 1; i++) {
			int p = sc.nextInt() - 1;
			adjList[i].add(p);
			adjList[p].add(i);
		}
		s = new int[n];
		e = new int[n];
		l = new int[n];
		findIntervals(0, -1);
		int N = 1;
		while (N < n)
			N <<= 1;

		SegmentTree st1 = new SegmentTree(N);
		SegmentTree st2 = new SegmentTree(N);
		int q = sc.nextInt();
		while (q-- > 0) {
			if (sc.nextInt() == 1) {
				int v = sc.nextInt() - 1;
				int x = sc.nextInt();
				int k = sc.nextInt();
				st1.update_range(s[v] + 1, e[v] + 1, (int) ((x + 1l * k * l[v]) % mod));
				st2.update_range(s[v] + 1, e[v] + 1, k);
			} else {
				int v = sc.nextInt() - 1;
				long x = 1l * st1.query(s[v] + 1, s[v] + 1) - ((1l * l[v] * st2.query(s[v] + 1, s[v] + 1)) % mod);
				while (x < 0)
					x += mod;
				x%=mod;
				pw.println(x);
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
				lazy[node] %= mod;
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
			return (((q1 + q2) % mod) + lazy[node]) % mod;
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
