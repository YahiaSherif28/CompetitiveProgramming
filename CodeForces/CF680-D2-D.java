import java.util.*;
 
import java.io.*;
import java.text.*;
 
public class D680 {
	public static long pow3(long x) {
		return x * x * x;
	}
 
	static TreeSet<Long> ts;
	static HashMap<Long, Pair> memo = new HashMap<Long, Pair>();
 
	public static Pair solve(long m) {
 
		if (m < 8) {
			return new Pair(m, m);
		}
		if (memo.containsKey(m)) {
			Pair p = memo.get(m);
			Pair n = new Pair(p.x, p.y);
			return n;
		}
		long g = ts.floor(m);
 
		int mult = 0;
		Pair ans = null;
		while (m - g * mult >= 0) {
			Pair cand = solve(Math.min(m - g * mult, g - 1));
			cand.x += mult;
			cand.y += g * mult;
			if (ans == null)
				ans = cand;
			else {
				if (cand.x > ans.x) {
					ans = cand;
				} else if (cand.x == ans.x) {
					if (cand.y > ans.y)
						ans = cand;
				}
			}
			mult++;
		}
		memo.put(m, ans);
		return new Pair(ans.x, ans.y);
 
	}
 
	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		PrintWriter pw = new PrintWriter(System.out);
		long m = sc.nextLong();
		ts = new TreeSet<Long>();
		for (int i = 1; i <= (int) 1e5; i++) {
			ts.add(pow3(i));
		}
		Pair ans = solve(m);
		pw.println(ans.x + " " + ans.y);
		pw.close();
	}
 
	static class Pair {
		long x, y;
 
		public Pair(long a, long b) {
			x = a;
			y = b;
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
