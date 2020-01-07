import java.util.*;
import java.io.*;
import java.math.*;
import java.text.*;

public class D501 {

	static int[] solve(int[] a) {
		FenwickTree ft = new FenwickTree(a.length);
		int[] fac = new int[a.length];
		for (int i = 0; i < a.length; i++) {
			int h = a[i] + 1 == 1 ? 0 : ft.rsq(a[i] + 1 - 1);
			h = a[i] - h;
			fac[a.length - 1 - i] = h;
			ft.point_update(a[i] + 1, 1);
		}
		return fac;
	}

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		PrintWriter pw = new PrintWriter(System.out);
		int n = sc.nextInt();
		int[] a = new int[n];
		for (int i = 0; i < a.length; i++) {
			a[i] = sc.nextInt();
		}
		int[] b = new int[n];
		for (int i = 0; i < b.length; i++) {
			b[i] = sc.nextInt();
		}
		int[] aa = solve(a);
		int[] bb = solve(b);
//		pw.println(Arrays.toString(aa));
//		pw.println(Arrays.toString(bb));
		int[] c = new int[n];
		for (int i = 0; i < c.length; i++) {
			c[i] = aa[i] + bb[i];
		}
		for (int i = 0; i < c.length; i++) {
			if (i + 1 < c.length)
				c[i + 1] += c[i] / (i + 1);
			c[i] %= (i + 1);
		}
//		pw.println(Arrays.toString(c));
		FenwickTree ft = new FenwickTree(n);
		for (int i = 0; i < c.length; i++) {
			ft.point_update(i + 1, 1);
		}
		int[] ans = new int[n];

		for (int i = 0; i < ans.length; i++) {
			int lo = 1;
			int hi = n;
			int bs = -1;
			while (lo <= hi) {
				int mid = lo + hi >> 1;
				if (ft.rsq(mid) <= c[c.length - 1 - i]) {
					lo = mid + 1;
				} else {
					hi = mid - 1;
					bs = mid;
				}
			}
			ans[i] = bs - 1;
			ft.point_update(bs, -1);
		}
		for (int i = 0; i < ans.length; i++) {
			pw.print(ans[i] + " ");
		}
		pw.close();
	}

	static class FenwickTree { // one-based DS

		int n;
		int[] ft;

		FenwickTree(int size) {
			n = size;
			ft = new int[n + 1];
		}

		int rsq(int b) // O(log n)
		{
			int sum = 0;
			while (b > 0) {
				sum += ft[b];
				b -= b & -b;
			} // min?
			return sum;
		}

		int rsq(int a, int b) {
			return rsq(b) - rsq(a - 1);
		}

		void point_update(int k, int val) // O(log n), update = increment
		{
			while (k <= n) {
				ft[k] += val;
				k += k & -k;
			} // min?
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
