import java.util.*;
import java.io.*;
import java.text.*;

public class CF416D2E {

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		PrintWriter pw = new PrintWriter(System.out);
		int n = sc.nextInt();
		int m = sc.nextInt();
		int[] x = new int[m];
		int[] y = new int[m];
		int[] c = new int[m];
		for (int i = 0; i < c.length; i++) {
			x[i] = sc.nextInt() - 1;
			y[i] = sc.nextInt() - 1;
			c[i] = sc.nextInt();
		}
		int[][] adjMatrix = new int[n][n];
		for (int[] g : adjMatrix)
			Arrays.fill(g, (int) 1e9);
		for (int i = 0; i < n; i++)
			adjMatrix[i][i] = 0;
		for (int i = 0; i < m; i++) {
			adjMatrix[x[i]][y[i]] = c[i];
			adjMatrix[y[i]][x[i]] = c[i];
		}
		for (int k = 0; k < n; k++)
			for (int i = 0; i < n; i++)
				for (int j = 0; j < n; j++)
					if (adjMatrix[i][j] > adjMatrix[i][k] + adjMatrix[k][j]) {
						adjMatrix[i][j] = adjMatrix[i][k] + adjMatrix[k][j];
					}
		int[][] v = new int[n][n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (adjMatrix[j][x[i]] + c[i] == adjMatrix[j][y[i]]) {
					v[j][y[i]]++;
				}
				if (adjMatrix[j][y[i]] + c[i] == adjMatrix[j][x[i]]) {
					v[j][x[i]]++;
				}
			}
		}
//		for (int i = 0; i < n; i++) {
//			for (int j = 0; j < n; j++)
//				pw.print(v[i][j] + " ");
//			pw.println();
//		}
		int[][] ans = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				ans[i][j] = v[i][j];
				for (int k = 0; k < n; k++) {
					if (k != i && k != j && adjMatrix[i][k] + adjMatrix[k][j] == adjMatrix[i][j]) {
						ans[i][j] += v[i][k];
					}
				}
			}
		}
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				pw.print(ans[i][j] + " ");
			}
		}
		pw.close();

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
