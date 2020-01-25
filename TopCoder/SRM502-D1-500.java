import java.util.*;
//import java.io.*;
//import java.text.*;

public class TheProgrammingContestDivOne {

	static Task[] arr;

	static class Task implements Comparable<Task> {
		long maxP, PPerM, reqT;

		public Task(int a, int b, int c) {
			maxP = a;
			PPerM = b;
			reqT = c;
		}

		public int compareTo(Task t) {
			return Double.compare(1.0 * reqT / PPerM, 1.0 * t.reqT / t.PPerM);
		}
	}

	static int dur, n;

	static long[][] dp;

	public static long dp(int idx, int t) {
		if (t > dur)
			return -(int) 1e9;
		if (idx == n)
			return 0;
		if (dp[idx][t] != -1)
			return dp[idx][t];
		return dp[idx][t] = Math.max(
				arr[idx].maxP - ((t + arr[idx].reqT) * arr[idx].PPerM) + dp(idx + 1, (int) (t + arr[idx].reqT)),
				dp(idx + 1, t));
	}

	public static int find(int T, int[] maxPoints, int[] pointsPerMinute, int[] requiredTime) {
		arr = new Task[maxPoints.length];
		for (int i = 0; i < requiredTime.length; i++) {
			arr[i] = new Task(maxPoints[i], pointsPerMinute[i], requiredTime[i]);
		}
		dur = T;
		n = maxPoints.length;
		Arrays.sort(arr);
		dp = new long[n][T + 1];
		for (long[] x : dp)
			Arrays.fill(x, -1);
		return (int) dp(0, 0);

	}

	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);
//		PrintWriter pw = new PrintWriter(System.out);
//		74
//		{502}
//		{2}
//		{47}
//		40000
//		{100000, 100000}
//		{1, 100000}
//		{50000, 30000}
//		75
//		{250, 500, 1000}
//		{2, 4, 8}
//		{25, 25, 25}
//		Returns: 1200
//		30
//		{100, 100, 100000}
//		{1, 1, 100}
//		{15, 15, 30}
		int T = 30;
		int[] maxPoints = { 100, 100, 100000 };
		int[] pointsPerMinute = { 1, 1, 100 };
		int[] requiredTime = { 15, 15, 30 };
//		pw.println(find(T, maxPoints, pointsPerMinute, requiredTime));
//		pw.close();
	}

//	static class Scanner {
//		StringTokenizer st;
//		BufferedReader br;
//
//		public Scanner(InputStream s) {
//			br = new BufferedReader(new InputStreamReader(s));
//		}
//
//		public Scanner(FileReader r) {
//			br = new BufferedReader(r);
//		}
//
//		public String next() throws IOException {
//			while (st == null || !st.hasMoreTokens())
//				st = new StringTokenizer(br.readLine());
//			return st.nextToken();
//		}
//
//		public int nextInt() throws IOException {
//			return Integer.parseInt(next());
//		}
//
//		public long nextLong() throws IOException {
//			return Long.parseLong(next());
//		}
//
//		public String nextLine() throws IOException {
//			return br.readLine();
//		}
//
//		public double nextDouble() throws IOException {
//			String x = next();
//			StringBuilder sb = new StringBuilder("0");
//			double res = 0, f = 1;
//			boolean dec = false, neg = false;
//			int start = 0;
//			if (x.charAt(0) == '-') {
//				neg = true;
//				start++;
//			}
//			for (int i = start; i < x.length(); i++)
//				if (x.charAt(i) == '.') {
//					res = Long.parseLong(sb.toString());
//					sb = new StringBuilder("0");
//					dec = true;
//				} else {
//					sb.append(x.charAt(i));
//					if (dec)
//						f *= 10;
//				}
//			res += Long.parseLong(sb.toString()) / f;
//			return res * (neg ? -1 : 1);
//		}
//
//		public boolean ready() throws IOException {
//			return br.ready();
//		}
//
//	}
}
