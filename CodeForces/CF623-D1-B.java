import java.util.*;
import java.io.*;
import java.text.*;

public class B623 {
	static int prime, a, b, arr[];
	static long dp[][];

	public static long dp(int idx, int jump) {
		if (idx == arr.length)
			return 0;
		if (dp[idx][jump] != -1)
			return dp[idx][jump];
		long ans = (long) 1e18;
		if (jump == 0 || jump == 2) {
			if (arr[idx] % prime == 0) {
				ans = Math.min(ans, dp(idx + 1, jump));
			}
			if (arr[idx] % prime == 1 || arr[idx] % prime == prime - 1) {
				ans = Math.min(ans, b + dp(idx + 1, jump));
			}
		}
		if (jump == 0 && idx != 0) {
			ans = Math.min(ans, a + dp(idx + 1, 1));
		}
		if (jump == 1) {
			ans = Math.min(ans, dp(idx, 2));
			ans = Math.min(ans, a + dp(idx + 1, jump));
		}
		return dp[idx][jump] = ans;
	}

	public static long solve(int[] a, int p) {
		prime = p;
		arr = a;
		for (long[] x : dp)
			Arrays.fill(x, -1);
		for (int i = a.length - 1; i >= 0; i--) {
			for (int j = 0; j < 3; j++)
				dp(i, j);
		}
		return dp(0, 0);
	}

	public static long solve(int[] arr) {
		long ans = (long) 1e18;
		ArrayList<Integer> primes = new ArrayList<Integer>();
		int x = arr[0];
		if (x == 1)
			return ans;
		for (int i = 2; i <= Math.sqrt(x) + 1; i++) {
			boolean f = false;
			while (x % i == 0) {
				x /= i;
				f = true;
			}
			if (f)
				primes.add(i);
		}
		if (x != 1)
			primes.add(x);

		for (int y : primes) {
			ans = Math.min(ans, solve(arr, y));
		}
		return ans;
	}

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		PrintWriter pw = new PrintWriter(System.out);
		int n = sc.nextInt();
//		int n = (int) 1e6;
//		a = b = (int) 1e9;

		a = sc.nextInt();
		b = sc.nextInt();
		int[] aa = new int[n];
		int[] ab = new int[n];
		dp = new long[n][3];
		for (int i = 0; i < ab.length; i++) {
			aa[i] = sc.nextInt();
//			aa[i] = i + 1;
			ab[n - 1 - i] = aa[i];
		}
		long ans = (long) 1e18;
		ans = Math.min(ans, Math.min(solve(aa), solve(ab)));
		aa[0]++;
		ab[0]++;
		ans = Math.min(ans, Math.min(b + solve(aa), b + solve(ab)));
		aa[0] -= 2;
		ab[0] -= 2;
		ans = Math.min(ans, Math.min(b + solve(aa), b + solve(ab)));

		pw.println(ans);
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
